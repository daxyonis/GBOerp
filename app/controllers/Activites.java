/**
 * 
 */
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import security.*;
import services.ActiviteMOService;
import play.libs.Json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Eva
 *
 */
@Component
@play.mvc.Security.Authenticated(Secured.class)
@Authorize(minLevel=PermissionLevel.VENDOR)
public class Activites extends Controller {
	
	private static ActiviteMOService moService;
	
	@Autowired
	public void setMOService(ActiviteMOService moService){
		this.moService = moService;
	}		
	
	public static Result findInfographie(){
		return find("infographie","Infographie");
	}
	
	public static Result findManipulation(){
		return find("assemblage", "Manipulation/Finition");
	}
	
	public static Result findLaminage(){
		return find("assemblage", "Laminage");
	}
	
	/**
	 * GET method
	 * Finds the details about a given activity
	 * @param categorie	the category of activity
	 * @param nom name of activity
	 * @return an Infographie object in JSON format
	 */
	public static Result find(String categorie, String nom){
		
		String categorieInterne = moService.getMOcategory(categorie);
		if(categorieInterne != null && !categorieInterne.isEmpty()){
				return ok(Json.toJson(moService.findByActivite(categorieInterne, nom)));
		}
		else{
				return badRequest();
		}		
	}

}
