package controllers;

import java.util.List;

import play.mvc.Controller;
import play.mvc.Result;

import security.*;
import models.Imprimante;
import services.ImprimanteService;
import views.html.imprim.*;

import play.libs.Json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.mvc.BodyParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Eva
 * Imprimantes controller for getting Imprimante data
 * Security level : VENDOR or above
 */
@Component
public class Imprimantes extends Controller {
	
	private static ImprimanteService impService;		
	
	@Autowired
	public void setImpService(ImprimanteService impService){
		this.impService = impService;
	}
	
	/**
	 * GET method
	 * Gets the list of printers supported by a given Flexible product
	 * @param id	the product id
	 * @return	JSON list of Imprimante objects
	 */
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.VENDOR)
	public static Result findForFlexible(int id){
		List<Imprimante> imps = impService.findForProduct("Flexible", id);
		return ok(Json.toJson(imps));
	}
	
	/**
	 * GET method
	 * Gets the list of all printers 
	 * @return  JSON list of all Imprimante
	 */
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.VENDOR)
	public static Result findAll(){
		List<Imprimante> printers = impService.findAll();
		return ok(Json.toJson(printers));
	}
	
	/**
	 * PUT method
	 * Update a printer
	 * @return  JSON Imprimante passed as input
	 */	
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.BUYER)
	@BodyParser.Of(BodyParser.Json.class)
	public static Result save() {	
		
		Imprimante printer = null;
		String errMsg = "Erreur : la mise à jour n'a pu être complétée.";
				
		// WARNING : try-catch masks all errors that may happen...
		try{
			// Get the JSON object sent by frontend
			JsonNode json = request().body().asJson();
			printer = getPrinter(json);
				
			if(printer != null){
				// Save
				boolean success = impService.save(printer);
				if(success)
					return ok(Json.toJson(printer));
				else
					return internalServerError(errMsg);
			}
			else{
				return badRequest(errMsg);
			}
		}
		catch(Exception e){
			return badRequest(errMsg);
		}		
	}
	
	/**
	 * POST method
	 * Adds a new printer
	 * @return  JSON Imprimante passed as input
	 */
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.BUYER)
	@BodyParser.Of(BodyParser.Json.class)
	public static Result add() {		
		
		Imprimante printer = null;
		String errMsg = "Erreur : l'ajout n'a pu être complété.";		
		
		// WARNING : try-catch masks all errors that may happen...
		try{
			// Get the JSON object sent by frontend
			JsonNode json = request().body().asJson();
			printer = getPrinter(json);		
		
			if(printer != null){
				// Save
				boolean success = impService.add(printer);
				if(success)
					return ok(Json.toJson(printer));
				else
					return internalServerError(errMsg);
			}
			else {
				return badRequest(errMsg);
			}	
		}
		catch(Exception e){
			// maybe we did not fill all of the fields	
			return badRequest(errMsg);
		}
	}
	
	/**
	 * DELETE method
	 * Removes the specified printer (BUYER level)
	 * @return	JSON Imprimante passed as input
	 */	
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.BUYER)
	@BodyParser.Of(BodyParser.Json.class)
	public static Result remove() {
		
		Imprimante printer = null;
		String errMsg = "Erreur : la suppression n'a pu être complétée.";				
		
		// WARNING : try-catch masks all errors that may happen...
		try{
			// Get the JSON object sent by frontend
			JsonNode json = request().body().asJson();
			printer = getPrinter(json);				
		
			if(printer != null){
				// Save
				boolean success = impService.remove(printer);
				if(success)
					return ok(Json.toJson(printer));
				else
					return internalServerError(errMsg);
			}
			else{
				return badRequest(errMsg);
			}		
		}
		catch(Exception e){
			return badRequest(errMsg);
		}
				
	}
	
	/**
	 * GET method
	 * Show the list of all printers (BUYER level)
	 * @return web page with printers list
	 */
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.BUYER)
	public static Result list(){		
		List<Imprimante> printers = impService.findAll();		
		return ok(list2.render(printers));		
	}

	/**
	 * Get a printer object (Imprimante) from json 
	 * @param json
	 * @return
	 * @throws JsonProcessingException
	 */
	private static Imprimante getPrinter(JsonNode json) throws JsonProcessingException{
		if(json == null)
			return null;		
		String code = json.findPath("codeSelection").textValue();
		Imprimante printer = null;
		if(null != code && !code.isEmpty()){
			// Convert to pojo
			ObjectMapper objMapper = new ObjectMapper();			
			printer = objMapper.treeToValue(json, Imprimante.class);
		}
		
		return printer;
	}
}
