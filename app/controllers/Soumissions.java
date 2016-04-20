package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import models.Commande;
import models.Soumission;
import models.SoumissionDTO;
import models.Statut;
import security.Authorize;
import security.PermissionLevel;
import security.Secured;
import services.dao.SoumissionDAO;
import services.CommandeService;
import services.SoumService;
import views.html.soumission.soumClient;
import views.html.soumission.list;
import views.html.soumission.suivi;

@Component	// this is for spring configuration
@play.mvc.Security.Authenticated(Secured.class)
@Authorize(minLevel=PermissionLevel.USER)
public class Soumissions extends Controller{
	
	private static SoumService soumService;
	private static CommandeService commService;
	
	@Autowired
	public void setSoumService(SoumService soumService) {
		this.soumService = soumService;
	}
	
	@Autowired
	public void setCommService(CommandeService commService){
		this.commService = commService;
	}
			
	/**
	 * GET method 
	 * @return  a view with the paged list Soumissions
	 */
	public static Result list(){
		return ok(list.render());
	}
	
	/** 
	 * GET method
	 * Search for a list of Soumissions where given field contains given value
	 * 
	 * @param value		the keyword to search for (>= 3 characters)
	 * @param field		can be "all" to search for all admissible fields
	 * @return
	 */
	public static Result search(String value, String field,
								String dateDebut, String dateFin, String selectClient, String selectRepresentant) {								
		SoumissionDAO.KeyFindSoumEnum fieldEnum;
		switch(field){
		case "NoSoumission": fieldEnum = SoumissionDAO.KeyFindSoumEnum.NO_SOUM;
		break;
		case "Suite" : fieldEnum = SoumissionDAO.KeyFindSoumEnum.SUITE;
		break;
		case "Projet": fieldEnum = SoumissionDAO.KeyFindSoumEnum.PROJET;
		break;
		case "Client": fieldEnum = SoumissionDAO.KeyFindSoumEnum.CLIENT;
		break;		
		case "all":
		default: fieldEnum = SoumissionDAO.KeyFindSoumEnum.ALL;
		}
		
		List<Soumission> soums = soumService.findByKeyword(fieldEnum, value, dateDebut, dateFin, selectClient, selectRepresentant);
		return ok(Json.toJson(soums));				  
		
	}
	
	/**
	 * GET method
	 * Render a soumission
	 * @param noSoumission	soumission id
	 * @param out			parameter for rendering : "client" --> soumission for client
	 * @return				the rendered web page for the given soumission
	 */
	public static Result render(int noSoumission, String out){
		switch(out){
		case "client":	// output for client
			Soumission soum = soumService.findByNo(noSoumission);
			return ok(soumClient.render(soum));
		default:
			flash("error","BAD QUERY STRING ERROR ON TRYING TO ACCESS TO " + request().uri());
			return redirect(routes.Application.index());
		}
		
	}
	
	/**
	 * GET method
	 * Finds a specific soumission
	 * @param noSoum 	the soumission id
	 * @return		 	the given soumission object, in JSON format
	 */
	public static Result find(int noSoum){
		Soumission soum = soumService.findByNo(noSoum);
		return ok(Json.toJson(soum));
	}
	

	/**
	 * GET method to retrieve a subset(page) of Soumission data	 
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
			SoumissionDTO soumDto = new SoumissionDTO();						
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
	 * Renders the page for order lookup
	 * @param dossier	the dossier
	 * @return   the order lookup rendered web page
	 */
	public static Result order(int dossier){
		Commande comm = commService.findByCustomerOrder(dossier);
		return ok(suivi.render(comm));
	}
}
