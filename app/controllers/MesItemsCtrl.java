package controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import play.mvc.Http.*;
import play.mvc.Result;
import play.mvc.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import security.*;
import models.MesItems;
import models.Item;
import models.Soumission;
import services.MesItemsService;
import services.SoumService;
import services.ItemService;

import views.html.mesitems.*;

@Component
@play.mvc.Security.Authenticated(Secured.class)
@Authorize(minLevel=PermissionLevel.VENDOR)
public class MesItemsCtrl extends Controller{
	
	public static final String USER_KEY = Secured.CKIE_USERNAME_KEY;
	public static final String CIBLE_KEY = "Cible";
	
	public static MesItemsService mesItemsService;	
	public static SoumService soumService;
	public static ItemService itemService;	
	
	
	@Autowired
	public void setMesItemsService(MesItemsService mesItemsService){
		this.mesItemsService = mesItemsService;
	}
	
	@Autowired
	public void setSoumService(SoumService soumServiceImpl){
		this.soumService = soumServiceImpl;
	}
	
	@Autowired
	public void setItemService(ItemService itemServiceImpl){
		this.itemService = itemServiceImpl;
	}
	
	/**
	 * Show the list of MesItems for the current user
	 * @return	html content with ok http status
	 */		
	public static Result show() {
		
		String user = Context.current().session().get(USER_KEY);					
		
		List<Item> mesItems = mesItemsService.getAllItemsByUser(user);		
		return ok(liste.render(mesItems, user));
		
	}
		
	/**
	 * Choose items to add to MesItems
	 * @return	the page to add items
	 */
	public static Result choose() {
		return ok(ajout.render());
	}
	
	
	/**
	 * POST method : add item(s) selected in html form
	 * @return	redirect to show()
	 */
	public static Result add() {		
		
		RequestBody body = request().body();
		Map<String, String[]> bodyMap = body.asFormUrlEncoded();	    	    
		List<String> listOfItems = Arrays.asList(bodyMap.get("NoItem"));
		
		if(listOfItems == null || listOfItems.isEmpty()) {
			return badRequest("No field [value] found");
		} 
		else {
			
			String user = Context.current().session().get(USER_KEY);			
						
			// save MesItems						
			for(int i=0; i<listOfItems.size(); i++){
				MesItems monItem = new MesItems();
				monItem.setUsager(user);
				monItem.setNoItem(Integer.parseInt(listOfItems.get(i)));
				mesItemsService.add(monItem);				
			}								
			return redirect(routes.MesItemsCtrl.show());			
		}
	}
	
	/**
	 * DELETE method : Removes an item from MesItems
	 * @param noItem
	 * @return	redirect to show()
	 */
	public static Result remove(int noItem) {
		
		String user = Context.current().session().get(USER_KEY);
		if(user != null && noItem != 0) {
			mesItemsService.delete(user, noItem);			
		}		
		else {
			flash("error", "Aucun item à supprimer.");
		}
		
		return redirect(routes.MesItemsCtrl.show());		
	}
	
	/**
	 * GET method : build the get request for confirmation page
	 * by setting up the list of Mes Items and Soumission cible
	 * @param cible	the chosen soumission
	 * @return	the page for confirmation
	 */
	public static Result confirm(int cible){
		String user = Context.current().session().get(USER_KEY);
		Soumission soumCible = soumService.findByNo(cible);
		List<Item> mesItems = mesItemsService.getAllItemsByUser(user);		
		return ok(confirm.render(mesItems, user, soumCible));
	}
	
	/**
	 * DELETE method
	 * Removes all items in Mes Items list for the current user
	 * @return
	 */
	public static Result removeAll(){
		String user = Context.current().session().get(USER_KEY);
		if(user != null) {
			mesItemsService.deleteAll(user);
		}		
		
		return redirect(routes.MesItemsCtrl.show());
	}
	
	/**
	 * POST method : copy the items from MesItems to Soumission cible
	 * @return
	 */
	public static Result copy() {
		
		RequestBody body = request().body();
		Map<String, String[]> bodyMap = body.asFormUrlEncoded();
		Integer noSoumCible = Integer.parseInt(Arrays.asList(bodyMap.get("noSoumission")).get(0));
		
		try{
			String user = Context.current().session().get(USER_KEY);
			List<Item> mesItems = mesItemsService.getAllItemsByUser(user);
			itemService.copyItemsToSoum(mesItems, noSoumCible);
			Soumission soumCible = soumService.findByNo(noSoumCible);
			flash("success", "Les items ont bien été copiés dans la soumission cible " + soumCible.getSuite() + "-" + soumCible.getVersion());
			
		} catch(Exception e){
			GBOlogger.LogError("MesItemsCtrl.copy()", e, Context.current());
			flash("error", "Erreur interne : les items n'ont pas pu être copiés dans la soumission cible.");
		}
				
		return redirect(routes.MesItemsCtrl.show());
	}
}
