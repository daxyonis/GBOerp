package controllers;

import java.util.ArrayList;
import java.util.List;

import play.mvc.Controller;
import play.mvc.Result;
import play.cache.CacheApi;
import play.data.Form;
import models.Flexible;
import models.FlexibleDTO;
import models.CellStyle;
import models.Imprimante;
import security.*;
import services.FlexibleService;
import services.CellStyleService;
import services.ImprimanteService;
import views.html.flex.listPage;
import views.html.flex.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import play.libs.Json;
import play.mvc.BodyParser;

import com.fasterxml.jackson.databind.JsonNode;

@Component	// Spring scans the package :this component is to be registered as bean
public class Flexibles extends Controller {
	
	private static final Form<Flexible> flexForm = Form.form(Flexible.class);
	private static FlexibleService flexService;
	private static CellStyleService cellStyleService;
	private static ImprimanteService imprimService;
	private static CacheApi cache;
	
	@Autowired
	public void setFlexibleService(FlexibleService fs){
		this.flexService = fs;
	}
	
	@Autowired
	public void setCellStyleService(CellStyleService css){
		this.cellStyleService = css;
	}
	
	@Autowired
	public void setImprimanteService(ImprimanteService is){
		this.imprimService = is;
	}
	
	@Autowired
	public void setCache(CacheApi cache){
		this.cache = cache;
	}
	
	/**
	 * GET method
	 * Liste de tous les flexibles
	 * @return	page listant les flexibles
	 */
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.BUYER)
	public static Result list() {
		List<Flexible> flexs = flexService.findAll();
		return ok(listPage.render(flexs));
	}
	
	/**
	 * GET method
	 * Entrer un nouveau Flexible
	 * @return	formulaire pour ajouter un flexible
	 */
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.BUYER)
	public static Result newFlexible() {
		return ok(details.render(flexService.findAll(), flexForm.fill(new Flexible()), flexService.getAllCategories()));
	}
	
	/**
	 * DELETE method
	 * Supprime un enregistrement des Flexibles
	 * @param id	id du Flexible à supprimer
	 * @return		la page qui liste les Flexibles, avec un message (succès ou échec de l'opération)
	 */
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.BUYER)
	public static Result remove(int id) {
		flexService.remove(id);		
		return redirect(routes.Flexibles.list());
	}
	
	/**
	 * GET method
	 * Édition d'un Flexible
	 * @param id   	le id du Flexible a éditer
	 * @return		la page d'édition ou NotFound si le id ne pointe vers rien
	 */
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.BUYER)
	public static Result edit(int id){		
		
		final Flexible flex = flexService.getById(id);
		
		if(flex != null){
			Form<Flexible> filledForm = flexForm.fill(flex);
			return ok(details.render(flexService.findAll(), filledForm, flexService.getAllCategories()));
		}
		else {
			return notFound(String.format("Le Flexible numéro %d n'existe pas.", id));
		}
		
	}
	
	/**
	 * POST : méthode d'action du formulaire de détails
	 * Sauvegarde d'un Flexible  
	 * @return		la page qui liste les Flexibles, avec un message (succès ou échec de l'opération)
	 */
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.BUYER)
	public static Result save(){
		Form<Flexible> boundForm = flexForm.bindFromRequest();
		
		if(boundForm.hasErrors()){
			flash("error", "Veuillez corriger le formulaire.");
			return badRequest(details.render(flexService.findAll(), boundForm, flexService.getAllCategories()));
		}
		
		Flexible flex = boundForm.get();
		
		// Get the printers list
		List<Imprimante> imprimantes = new ArrayList<Imprimante>();
		for(Imprimante printer : flex.getImprimantes()){
			if(printer.getCodeSelection() != null){
				imprimantes.add(imprimService.findByCode(printer.getCodeSelection()));
			}		
		}
		flex.setImprimantes(imprimantes);
		
		if(flex.getId() == 0){
			// new product
			if(flexService.add(flex)){
				flash("success", String.format("Le produit %s a bien été ajouté à la liste.", flex.getProduit()));
			}
			else{
				flash("error", "Une erreur s'est produite et le produit n'a pu être ajouté.");
			}
		}
		else {
			if(flexService.update(flex)){
				flash("success", String.format("Le produit %s a été mis à jour.", flex.getProduit()));
			}
			else {
				flash("error", "Une erreur s'est produite et le produit n'a pu être mis à jour.");
			}
		}
				
		return redirect(routes.Flexibles.list());
		
	}
	
	/**
	 * GET method
	 * Envoi des styles des cellules de tableau des Flexibles
	 * @return
	 */
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.BUYER)
	public static Result getStyles(){		
		 
		 List<CellStyle> styles = cellStyleService.findAll();
		 return ok(Json.toJson(styles));
	}
		
	
	/**
	 * POST method
	 * Sauvegarde d'un style modifié
	 * @return	message texte selon le succès ou l'échec d'un ajout/modification
	 * 			d'un style
	 */
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.BUYER)
	@BodyParser.Of(BodyParser.Json.class)
	public static Result saveStyle(){	
	  // Get the JSON object sent by frontend
	  JsonNode json = request().body().asJson();	  	  
	  	  
	  // Get the row id and column index parameters
	  // these are necessary to uniquely define a cell
	  short rowId = (short)(Integer.parseInt(json.findPath("rowId").textValue()));
	  byte colIndex = (byte)(json.findPath("colIndex").intValue());	  	  
	  
	  if(rowId <= 0) {
	    return ok("Incorrect parameter [rowId]");
	  } else if(colIndex <= 0 || colIndex > 20){
		return ok("Incorrect parameter [colIndex]");
	  } else {
		  String cssClass = json.findPath("cssClass").textValue();
		  String cssColor = json.findPath("cssColor").textValue();
		  String cssBgColor = json.findPath("cssBgColor").textValue();
		  
		  String sendBackMessage = "Nothing to udpate.";
		  
		  if(cssClass != null || cssColor != null || cssBgColor != null){
			  CellStyle style = new CellStyle();
			  
			  style.setRowId(rowId);
			  style.setColIndex(colIndex);
			  
			  if(cssClass != null)
				  style.setCssClass(cssClass);
			  if(cssColor != null)
				  style.setCssColor(cssColor);
			  if(cssBgColor != null)
				  style.setCssBgColor(cssBgColor);
			  			 
			 if(cellStyleService.getByRowCol(rowId, colIndex) != null){
				 // object exists
				 if(cellStyleService.update(style))
					 sendBackMessage = "Style was updated : " + style.toString();
				 else
					 sendBackMessage = "Error in updatind style : " + style.toString();
			 }
			 else {
				 if(cellStyleService.add(style))
					 sendBackMessage = "Style was added : " + style.toString();				 
				 else
					 sendBackMessage = "Error in adding style : " + style.toString();				 
			 }
		 }
		  
		  return ok(sendBackMessage);
		  
	  }
	}
	
	/**
	 * DELETE method
	 * Retrait d'un style dans une cellule donnée de la table
	 * @param row	paramètre indicateur de la rangée (id de rangée)
	 * @param col	paramètre indicateur de la colonne (index de colonne)
	 * @return		un message texte de succès ou échec
	 */
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.BUYER)
	public static Result removeStyle(String row, String col){				
		
		if(!validate(row, col)){
			return badRequest("Incorrect row : " + row + " or col : " + col);
		}
		
		short rowId = Short.parseShort(row);
		byte colIndex = Byte.parseByte(col);
		
		if(cellStyleService.remove(rowId, colIndex)){
			return ok("Style removed in row=" + row + ", col=" + col);
		}else{
			return ok("Style not removed in row=" + row + ", col=" + col);
		}
		
	}
	
	/**
	 * Validates an input (row, col) parameters
	 * @param row
	 * @param col
	 * @return	true if parameters are valid; false otherwise
	 */
	public static boolean validate(String row, String col){
		// row must be > 0
		short rowId = Short.parseShort(row);
		if(rowId <= 0 ){
			return false;
		}
		
		// col must be > 0 and < 20
		byte colIndex = Byte.parseByte(col);
		if((colIndex <= 0) || (colIndex > 20)){
			return false;
		}
		
		return true;
	}

	/**
	 * GET method
	 * Find all the Flexibles that exist
	 * @return list of Flexibles in json format
	 */
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.VENDOR)
	public static Result findAll(){
		List<Flexible> flexs = cache.getOrElse("flexList", () -> flexService.findAll());
		return ok(Json.toJson(flexs));
	}
	
	/**
	 * GET method
	 * Find all the Flexibles DTO that exist
	 * @return list of FlexibleDTOs in json format
	 */
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.VENDOR)
	public static Result findAllDTO(){
		List<FlexibleDTO> flexs = cache.getOrElse("flexDTOList", () -> flexService.findAllDTO());
		return ok(Json.toJson(flexs));
	}
}
