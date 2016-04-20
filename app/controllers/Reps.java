/**
 * 
 */
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import security.*;

import models.Rep;
import services.RepService;

import play.libs.Json;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Eva
 *
 */
@Component
@play.mvc.Security.Authenticated(Secured.class)
@Authorize(minLevel=PermissionLevel.VENDOR)
public class Reps extends Controller {
	
	private static RepService repService;
	
	@Autowired
	public void setRepService(RepService rs){
		this.repService = rs;
	}
	
	public static Result findAll(){
		List<Rep> reps = repService.findAllActive();
		return ok(Json.toJson(reps));
	}

}
