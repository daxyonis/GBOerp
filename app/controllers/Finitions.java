/**
 * 
 */
package controllers;

import java.util.List;

import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import security.*;
import models.Finition;
import services.FinitionService;
import views.html.finition.list;
import play.libs.Json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Eva
 *
 */
@Component
public class Finitions extends Controller {
	
	private static FinitionService finitionService;
	
	@Autowired
	public void setFinitionService(FinitionService fs){
		this.finitionService = fs;
	}
	
	/**
	 * GET method
	 * Find all the Finition objects that exist
	 * @return  a list of Finition objects, in JSON format
	 */
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.VENDOR)
	public static Result findAll(){
		List<Finition> finitions = finitionService.findAll();
		return ok(Json.toJson(finitions));
	}
	
	/**
	 * GET method
	 * Find all the Finition that apply to a given Flexible product category
	 * @param cat  the category of Flexible product
	 * @return
	 */
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.VENDOR)
	public static Result findForFlexibleCategory(String cat){
		List<Finition> finitions = finitionService.findAllActive();
		
		if(cat.equalsIgnoreCase("MESH") ||
		   cat.equalsIgnoreCase("BANNIÈRE"))
			return ok(Json.toJson(finitions));
		else
			return ok(Json.toJson(finitions.subList(0, 1))); 
	}
	
	/**
	 * GET method
	 * Show the list of all finitions (BUYER level)
	 * @return web page with printers list
	 */
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.BUYER)
	public static Result list(){		
		List<Finition> finis = finitionService.findAll();		
		return ok(list.render(finis));		
	}
	
	/**
	 * PUT method
	 * Update a finition
	 * @return  JSON Finitionpassed as input
	 */	
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.BUYER)
	@BodyParser.Of(BodyParser.Json.class)
	public static Result save() {
		Finition finition = null;
		String errMsg = "Erreur : la mise à jour n'a pu être complétée.";
				
		// WARNING : try-catch masks all errors that may happen...
		try{
			// Get the JSON object sent by frontend
			JsonNode json = request().body().asJson();
			finition = getFinition(json);
				
			if(finition != null){
				// Save
				boolean success = finitionService.save(finition);
				if(success)
					return ok(Json.toJson(finition));
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
	 * Adds a new finition
	 * @return  JSON Finition passed as input
	 */
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.BUYER)
	@BodyParser.Of(BodyParser.Json.class)
	public static Result add() {
		Finition finition = null;
		String errMsg = "Erreur : l'ajout n'a pu être complété.";
				
		// WARNING : try-catch masks all errors that may happen...
		try{
			// Get the JSON object sent by frontend
			JsonNode json = request().body().asJson();
			finition = getFinition(json);
				
			if(finition != null){
				// Save
				boolean success = finitionService.add(finition);
				if(success)
					return ok(Json.toJson(finition));
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
	 * DELETE method
	 * Removes the specified printer (BUYER level)
	 * @return	JSON Imprimante passed as input
	 */	
	@play.mvc.Security.Authenticated(Secured.class)
	@Authorize(minLevel=PermissionLevel.BUYER)
	@BodyParser.Of(BodyParser.Json.class)
	public static Result remove() {
		Finition finition = null;
		String errMsg = "Erreur : la suppression n'a pu être complétée.";
				
		// WARNING : try-catch masks all errors that may happen...
		try{
			// Get the JSON object sent by frontend
			JsonNode json = request().body().asJson();
			finition = getFinition(json);
				
			if(finition != null){
				// Save
				boolean success = finitionService.remove(finition);
				if(success)
					return ok(Json.toJson(finition));
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
	 * Get a finition object (Finition) from json 
	 * @param json
	 * @return
	 * @throws JsonProcessingException
	 */
	private static Finition getFinition(JsonNode json) throws JsonProcessingException{
		if(json == null)
			return null;		
		int id = json.findPath("id").isNumber() ? json.findPath("id").intValue() : Integer.MIN_VALUE; 
		String type = json.findPath("type").textValue();
		Finition fini = null;
		if(id > Integer.MIN_VALUE && !type.isEmpty()){
			// Convert to pojo
			ObjectMapper objMapper = new ObjectMapper();			
			fini = objMapper.treeToValue(json, Finition.class);
		}
		
		return fini;
	}

}
