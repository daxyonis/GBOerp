package controllers.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.node.ObjectNode;

import play.Logger;
import play.data.Form;
import play.i18n.Lang;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;
import play.cache.*;
import security.Authorize;
import security.PermissionLevel;
import security.Secured;
import services.ClientService;
import services.ProdFamilyService;
import services.RepService;
import services.app.AppSoumService;
import models.Client;
import models.ProdFamily;
import models.Rep;
import models.Soumission;
import models.app.AppSoumissionDTO;
import models.Statut;
import models.app.AppSoumEntete;
import models.app.AppSoumission;
import views.html.appsoum.enteteDetails;
import views.html.appsoum.soumView;
import views.html.appsoum.soumError;
import views.html.appsoum.itemsView;
import views.html.appsoum.list;
import utilities.MoreCollectors;

/**
 * Controller for managing the AppSoumission objects
 * NOTE: this controller caches the maps for clients and reps.
 * @author Eva
 *
 */
@Component	// this is for spring configuration
@play.mvc.Security.Authenticated(Secured.class)
@Authorize(minLevel=PermissionLevel.VENDOR)
public class AppSoumissions extends Controller {

    // forms
    private static final Form<AppSoumEntete> enteteForm = Form.form(AppSoumEntete.class);

    // services
    private static AppSoumService soumService;
    private static RepService repService;
    private static ClientService clientService;
    private static CacheApi cache;
    private static ProdFamilyService pfService;

    @Autowired
    public void setSoumService(AppSoumService soumService) {
	AppSoumissions.soumService = soumService;
    }

    @Autowired
    public void setRepService(RepService rs){
	AppSoumissions.repService = rs;
    }

    @Autowired
    public void setClientService(ClientService cs){
	AppSoumissions.clientService = cs;
    }

    @Autowired
    public void setProdFamService(ProdFamilyService pfs){
	AppSoumissions.pfService = pfs;
    }

    @Autowired
    public void setCache(CacheApi cache){
	AppSoumissions.cache = cache;
    }

    public static Result list(){
	return ok(list.render());
    }

    /**
     * GET method
     * Crée une nouvelle soumission dans la base de donnée et redirige vers la vue de cette soumission
     * @return  vue globale de la soumission si la création est un succès; retour à la liste autrement
     */
    public static Result newSoum(){
	AppSoumission soum = new AppSoumission();

	// Remplir avec des valeurs plausibles
	// Deviner le vendeur selon l'usager
	String userEmail = session().get(Secured.CKIE_USERNAME_KEY);
	List<Rep> repsForUser = repService.findByEmail(userEmail);
	if(repsForUser.size() > 0){
	    soum.entete.setNoVendeur(repsForUser.get(0).getSalesmanCode());
	    soum.entete.setNomVendeur(repsForUser.get(0).getDescription1());
	}
	// La date courante
	soum.entete.setDateEntre(new Date());

	int newSoumno = soumService.create(soum);

	if(newSoumno > 0){
	    flash("success","Nouvelle soumission créée.");
	    return redirect(routes.AppSoumissions.view(newSoumno));
	}
	else{
	    flash("error", "Impossible de créer une nouvelle soumission.");
	    return redirect(routes.AppSoumissions.list());
	}
    }

    /**
     * GET method
     * @param no	numéro de la soumission
     * @return		ok avec la vue principale de la soumission
     */
    public static Result view(int no){		

	Optional<AppSoumission> soum = soumService.findByNo(no, true);
	if(soum.isPresent()){
	    Map<Integer,String> versionMap = getVersionsForSuite(soum.get().getSuite());    
	    return ok(soumView.render(soum.get(), versionMap));
	}
	else{
	    return notFound(soumError.render(no));
	}
    }

    /**
     * GET method
     * Retourne la page du formulaire d'entete
     * @param no  numéro de la soumission
     * @return  ok avec la vue du formulaire d'entete
     */
    public static Result viewEntete(int no){	
	// Set the language for the whole session
	ctx().changeLang(new Lang(Lang.forCode("fr")));

	Optional<AppSoumission> soum = soumService.findByNo(no, false);

	if(!soum.isPresent()){
	    return notFound(soumError.render(no));
	}		

	Form<AppSoumEntete> enteteRemplie = enteteForm.fill(soum.get().getEntete()); 

	Map<String,String> repMap = cache.getOrElse("repMap", () -> getMapForReps());
	Map<String,String> clientMap = cache.getOrElse("clientMap", () -> getMapForClients());

	return ok(enteteDetails.render(soum.get(), enteteRemplie, repMap, clientMap));		
    }


    /**
     * POST method
     * Action pour le formulaire d'entête de la soumission
     * Sauvegarde les nouvelles valeurs de l'entête dans la soumission 
     * @param   no : le numéro de la soumission
     * @return  la vue avec erreurs s'il y a lieu; sinon retour à la vue principale de la Soumission
     */
    public static Result saveEntete(int no){
	Form<AppSoumEntete> boundForm = enteteForm.bindFromRequest();				

	if(boundForm.hasErrors()){
	    flash("error", "Veuillez corriger les erreurs dans le formulaire.");			
	    Optional<AppSoumission> soum = soumService.findByNo(no, false);

	    if(soum.isPresent()){
		Map<String,String> repMap = cache.getOrElse("repMap", () -> getMapForReps());
		Map<String,String> clientMap = cache.getOrElse("clientMap", () -> getMapForClients());

		return badRequest(enteteDetails.render(soum.get(), boundForm,  repMap, clientMap));
	    }
	    else{
		return notFound(soumError.render(no));
	    }
	}

	AppSoumEntete entete = boundForm.get();

	if(!soumService.updateEntete(entete)){
	    flash("error", "Une erreur s'est produite lors de l'enregistrement.");
	}		

	return redirect(routes.AppSoumissions.view(no));
    }

    public static Result viewItems(int no){
	Optional<AppSoumission> soum = soumService.findByNo(no, true);

	if(soum.isPresent()){
	    List<ProdFamily> prodFamilies = pfService.findAll();
	    Html itemsData = new Html(Json.toJson(soum.get().items).toString());
	    Html pfData = new Html(Json.toJson(prodFamilies).toString());
	    return ok(itemsView.render(soum.get(), itemsData, pfData));
	}
	else{
	    return notFound(soumError.render(no));
	}
    }

    // Map of <RepCode, RepName>
    private static Map<String,String> getMapForReps(){
	List<Rep> reps = repService.findAllActive();
	Map<String,String> repMap = reps.stream().collect(MoreCollectors.toLinkedMap(Rep::getSalesmanCode,Rep::getDescription1));
	// cache for future use
	cache.set("repMap", repMap);
	return repMap;
    }

    // Map of <ClientNo, ClientName>
    private static Map<String,String> getMapForClients(){
	List<Client> clients = clientService.findAll();
	Map<String,String> clientMap = clients.stream().collect(MoreCollectors.toLinkedMap(Client::getNoClient, Client::getNom));
	// cache for future use
	cache.set("clientMap", clientMap);
	return clientMap;
    }
    
    /**
     * Creates a map of all versions of a suite
     * @param suite	the suite number
     * @return		a Map of <NoSoumission, Version>
     */
    private static Map<Integer,String> getVersionsForSuite(int suite){
	
	List<AppSoumission> soums = soumService.findBySuite(suite);
	Map<Integer,String> versionMap = new LinkedHashMap<Integer,String>();
	for(AppSoumission s: soums){
	    versionMap.put(s.getNoSoumission(), s.getVersion());
	}
	versionMap.put(0, "Nouvelle...");
	return versionMap;
    }

    /**
     * GET method to retrieve a subset(page) of AppSoumission data	 
     * @param search		search keyword
     * @param sort			sort field ("suite" is the default)
     * @param order			"asc" or "desc" for the output result ("desc" is default)
     * @param limit			page size
     * @param offset        page offset
     * @param rep           rep id as filter (optional) 
     * @param statut        status code as filter (optional)
     * @param numero        either the noSoumission or dossier field, as filter (optional)
     * @param dateDebut     min date string  (optional)
     * @param dateFin       max date string  (optional)
     * 
     * @return	a list of SoumissionDTO objects, in JSON format
     */
    public static Result getData(String search, String sort, String order, int limit, int offset,
	    String rep, int statut, String numero, String dateDebut, String dateFin){
	int pageSize = limit;
	int pageNb = offset/limit + 1;		
	int num = numero.isEmpty() ? 0 : Integer.parseInt(numero);

	if(validateData(pageSize, pageNb, sort, order, rep, statut, num, dateDebut, dateFin)){

	    // Return it in the format wanted
	    AppSoumissionDTO soumDto = new AppSoumissionDTO();						
	    soumDto.setRows(soumService.getPage(search, sort, order, pageSize, pageNb, rep, statut, num, dateDebut, dateFin));			
	    soumDto.setTotal(soumService.countAll(search, rep, statut, num, dateDebut, dateFin));

	    return ok(Json.toJson(soumDto));
	}
	else{
	    return badRequest();
	}

    }

    /**
     * Method to validate user input data when searching for soumissions
     * see getData() method for parameters
     * @param pageSize
     * @param pageNb
     * @param sort
     * @param order
     * @param rep
     * @param statut
     * @param numero
     * @param dateDebut
     * @param dateFin
     * @return
     */
    private static boolean validateData(int pageSize, int pageNb, String sort, String order, 
	    String rep, int statut, int numero, String dateDebut, String dateFin){

	if(pageSize <= 0)
	    return false;

	if(pageNb <= 0)
	    return false;

	if(Soumission.getSortableFieldNames().indexOf(sort) < 0)
	    return false;

	if(!order.equalsIgnoreCase("asc") && !order.equalsIgnoreCase("desc"))
	    return false;

	if(statut < Statut.MIN_CODE || statut > Statut.MAX_CODE)
	    return false;

	if(numero < 0)
	    return false;

	SimpleDateFormat SDateformat = new SimpleDateFormat("yyyy-MM-dd");
	if(!dateDebut.isEmpty()){
	    try{				
		SDateformat.parse(dateDebut);
	    }
	    catch(ParseException e){
		return false;				
	    }
	}
	if(!dateFin.isEmpty()){
	    try{
		SDateformat.parse(dateFin);
	    }
	    catch(ParseException e){
		return false;				
	    }
	}

	return true;
    }

    /**
     * GET method
     * Copies a given AppSoumission and returns the new copy 
     * @param noSoum	the AppSoumission id(noSoumission)
     * @return	the new AppSoumission copy object, as JSON
     */
    public static Result copy(int noSoum, boolean withItems){

	// Find soum to copy
	Optional<AppSoumission> soum = soumService.findByNo(noSoum, withItems);
	if(soum.isPresent()){
	    int newSoumNo = soumService.createCopy(soum.get(), withItems);
	    Optional<AppSoumission> newSoum = soumService.findByNo(newSoumNo, false);
	    if(newSoum.isPresent()){
		return ok(Json.toJson(newSoum.get()));
	    }
	    else{
		Logger.debug(String.format("Soumission copie # %d non trouvée.", newSoumNo));
		return internalServerError("Soumission copie non trouvée.");
	    }
	}
	else{
	    Logger.debug(String.format("Soumission source # %d non trouvée.", noSoum));
	    return badRequest("Soumission source non trouvée.");
	}
    }

    /**
     * DELETE method
     * Removes an existing AppSoumission object 
     * @param noSoum	the AppSoumission id(noSoumission)
     * @return	noSoum as json on success, or failure message
     */
    public static Result delete(int noSoum){
	boolean success = soumService.delete(noSoum);
	if(success){
	    ObjectNode result = Json.newObject();
	    result.put("noSoumission", noSoum);
	    return ok(result);
	}
	else{
	    return internalServerError("La suppression n'a pas eu lieu.");
	}
    }


    /**
     * GET method
     * Creates a new version of the given AppSoumission
     * @param suite	suite number of AppSoumission for which to create a new version
     * @param itemsFrom	version identifier (A-Z)
     * @return	the newly created AppSoumission, as JSON
     */
    public static Result newVersion(int suite, String itemsVersion){
	int newNo = soumService.createCopyVersion(suite, itemsVersion);
	if(newNo > 0){
	    flash("success","Nouvelle version créée.");
	    return(redirect(routes.AppSoumissions.view(newNo)));
	}
	else{
	    return internalServerError("La copie n'a pu être créée.");
	}
    }
}
