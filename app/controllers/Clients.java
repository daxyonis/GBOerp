/**
 * 
 */
package controllers;

import java.util.List;

import play.mvc.Controller;
import play.mvc.Result;
import play.cache.*;

import security.*;
import models.Client;
import services.ClientService;

import play.libs.Json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Eva
 * Clients controller for getting client data
 * Security level : VENDOR or above
 */
@Component
@play.mvc.Security.Authenticated(Secured.class)
@Authorize(minLevel=PermissionLevel.VENDOR)
public class Clients extends Controller {

	private static ClientService clientService;
	private static CacheApi cache;
	
	@Autowired
	public void setClientService(ClientService cs){
		this.clientService = cs;
	}
	
	@Autowired
	public void setCache(CacheApi cache){
		this.cache = cache;
	}
	
	/**
	 * GET method
	 * Find all the Client objects that exist
	 * @return  a list of Client objects, in JSON format
	 */
	public static Result findAll(){
		List<Client> clients = cache.getOrElse("clientList", () -> getClientList());
		return ok(Json.toJson(clients));
	}
	
	private static List<Client> getClientList(){
		List<Client> clients = clientService.findAll();
		cache.set("clientList", clients);
		return clients;
	}
	
}
