package controllers.app;

import java.util.List;
import java.util.Optional;

import models.app.AppItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.Logger;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.*;
import security.Authorize;
import security.PermissionLevel;
import security.Secured;
import services.app.AppItemService;

@Component	// this is for spring configuration
@play.mvc.Security.Authenticated(Secured.class)
@Authorize(minLevel=PermissionLevel.VENDOR)
public class AppItems extends Controller {

	private static AppItemService itemService;
	
	@Autowired
	public void setItemService(AppItemService is) {
		this.itemService = is;
	}
	
	/**
	 * GET method
	 * Create a new Item object but not in the database
	 * @return a new empty Item object, in JSON format
	 */
	public static Result voidItem(){
		return ok(Json.toJson(new AppItem()));
	}
	
	/**
	 * POST method
	 * Create a new item, intended to be used by Backbone.sync
	 * @param noSoum  the soum id
	 * @return the only field that has changed : noItem 
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public static Result create(int noSoum)
	{
		String errMsg = "Erreur : l'ajout n'a pu être complété.";
		JsonNode json = request().body().asJson();
	    AppItem item = null;
	    try{
	    	item = new ObjectMapper().treeToValue(json, AppItem.class);
	    }
	    catch(JsonProcessingException e){
	    	Logger.debug("Caught JsonProcessingException",e);
	    	return badRequest(errMsg);
	    }
	    if(item != null){
	    	try{
	    		int noItem = itemService.add(item);
	    		ObjectNode result = Json.newObject();
		        result.put("noItem", noItem);
		        return ok(result);
	    	}
	    	catch(Exception e){
	    		Logger.debug("Caught error in itemService.add", e);
	    		return internalServerError(errMsg);
	    	}
	    		    	
	    }
	    else{
	    	Logger.debug("AppItem is null.");
	    	return badRequest(errMsg);
	    }        
	    		
	}
	/**
	 * GET method
	 * Read a collection of items, intended to be used with Backbone.Collection.fetch
	 * @param noSoum  the soum id
	 * @return a JSON array of the items from a given soum
	 */
	public static Result readAllForSoum(int noSoum){
		List<AppItem> items = itemService.findAllForSoum(noSoum);		
		return ok(Json.toJson(items));
	}
	
	/**
	 * GET method
	 * Read one item, intended to be used with Backbone.Model.fetch
	 * @param noSoum  the soum id (not useful for the method)
	 * @param noItem  the item id
	 * @return  the given item object, as json
	 */
	public static Result read(int noSoum, int noItem){
		Optional<AppItem> item = itemService.findByNo(noItem);
		if(item.isPresent()){
			return ok(Json.toJson(item.get()));
		}
		else{
			return badRequest("No such item.");
		}
		
	}
	
	/**
	 * PUT method
	 * Update one item, intended to be used by Backbone.sync
	 * @param noSoum  the soum id (not useful for the method)
	 * @param noItem  the item id
	 * @return  http code (ok for success; otherwise some failure code)
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public static Result update(int noSoum, int noItem){
		String errMsg = "Erreur : la mise à jour n'a pu être complétée.";
		JsonNode json = request().body().asJson();
	    AppItem item = null;
	    try{
	    	item = new ObjectMapper().treeToValue(json, AppItem.class);
	    }
	    catch(JsonProcessingException e){
	    	Logger.debug("Caught JsonProcessingException",e);
	    	return badRequest(errMsg);
	    }
	    if(item != null){	    
	    	boolean success = itemService.save(item);
	    	if(success)
	    		return ok(Json.toJson(item));
	    	else
	    		return internalServerError(errMsg);
	    }
	    else{
	    	Logger.debug("AppItem is null.");
	    	return badRequest(errMsg);
	    }   
	}
	
	/**
	 * DELETE method
	 * Delete one item, intended to be used by Backbone.sync
	 * @param noSoum  the soum id (not useful for the method)
	 * @param noItem  the item id
	 * @return  http code (ok for success; otherwise some failure code)
	 */	
	public static Result delete(int noSoum, int noItem){
		String errMsg = "Erreur : la délétion n'a pu être complétée.";
	    if(noItem > 0){
	    	Optional<AppItem> item = itemService.findByNo(noItem);
	    	if(item.isPresent() && (noSoum == item.get().getNoSoumission())){
		    	boolean success = itemService.remove(item.get());
		    	if(success){		    		
		    		return ok(Json.toJson(item.get()));
		    	}
		    	else{
		    		return internalServerError(errMsg);
		    	}
	    	}
	    	else {
	    		return badRequest(errMsg);
	    	}
	    }
	    else{
	    	Logger.debug("AppItem is null.");
	    	return badRequest(errMsg);
	    }   
	}		
}
