package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;

import security.*;
import models.Laminage;
import services.LaminageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@play.mvc.Security.Authenticated(Secured.class)
@Authorize(minLevel=PermissionLevel.VENDOR)
public class Laminages extends Controller {

	private static LaminageService lamService;
	
	@Autowired
	public void setLaminageService(LaminageService lamService){
		this.lamService = lamService;
	}
	
	public static Result findForCategory(String category){
		List<Laminage> lams = lamService.findAllForCategory(category);
		return ok(Json.toJson(lams));
	}
}
