/**
 * 
 */
package controllers;

import java.util.List;

import play.mvc.Controller;
import play.mvc.Result;

import security.*;
import models.Accessoire;
import models.Type;
import services.AccessoireService;

import play.libs.Json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Eva
 * Controller for Accessoire api
 */
@Component
@play.mvc.Security.Authenticated(Secured.class)
@Authorize(minLevel=PermissionLevel.VENDOR)
public class Accessoires extends Controller {
	
	private static AccessoireService accessoireService;
	
	@Autowired
	public void setAccessoireService(AccessoireService accService){
		this.accessoireService = accService;
	}
	
	/**
	 * GET method
	 * Find the Accessoire objects with given type
	 * @param pType  the type of Accessoire
	 * @return  a list of Accessoire objects, in JSON format
	 */
	public static Result find(String pType){
		List<Accessoire> accessoires = accessoireService.findByType(pType);
		return ok(Json.toJson(accessoires));
	}
	
	/**
	 * GET method
	 * Find all Accessoire types for a given Flexible product
	 * @param id   the id of the Flexible product
	 * @return  a list of Accessoire types, in JSON format
	 */
	public static Result findTypesForFlexible(int id){
		// TODO : pass the id parameter to filter the supported accessoire types...
		List<Type> types = accessoireService.findTypes();
		return ok(Json.toJson(types));
	}
	
}
