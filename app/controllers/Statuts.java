package controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.Authorize;
import security.PermissionLevel;
import models.Statut;
import security.Secured;
import services.StatutService;
/**
 * @author Eva
 * Statuts controller for getting status data
 * Security level : VENDOR or above
 */
@Component
@play.mvc.Security.Authenticated(Secured.class)
@Authorize(minLevel=PermissionLevel.VENDOR)
public class Statuts extends Controller {
	
	private static StatutService statutService;
	
	@Autowired
	public void setStatutService(StatutService ss){
		this.statutService = ss;
	}
	
	/**
	 * GET method
	 * Find all possible statuses
	 * @return  list of status objects in JSON format
	 */
	public static Result findAll(){
		List<Statut> statuts = statutService.findAll();
		return ok(Json.toJson(statuts));
	}

}
