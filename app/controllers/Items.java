package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;
import security.Authorize;
import security.PermissionLevel;
import security.Secured;
import services.dao.ItemDAO;
import services.dao.ItemDAOImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import models.Item;
import views.html.items.list;
import views.html.items.details;

@Component	// this is for spring configuration
@play.mvc.Security.Authenticated(Secured.class)
@Authorize(minLevel=PermissionLevel.USER)
public class Items extends Controller{		
		
    private static ItemDAO itemDAO;
	private static final Form<Item> itemForm = Form.form(Item.class);
	
	@Autowired	// this is for dependency injection
	public void setItemDAO(ItemDAOImpl itemDAO) {
		this.itemDAO = itemDAO;
	}
	
	/**
	 * Liste de tous les items
	 * @return	page listant les items
	 */
	public static Result list() {
		List<Item> items = itemDAO.findAllItems();
		return ok(list.render(items));		
	}
	
	/**
	 * Liste des items dans une soumission donnée
	 * @param noSoum	le noSoumission
	 * @return			java.util.List<Item>
	 */
	public static Result find(int noSoum) {
		List<Item> items = itemDAO.findAllItemsFromSoum(noSoum);
		return ok(Json.toJson(items));
	}
		
	/**
	 * Retourne le formulaire pour créer un nouvel item
	 * (infos de base)
	 * @return	
	 */
	public static Result newItem() {
		return ok(details.render(itemForm));
    }		
	
	/**
	 * Sauvegarder un nouvel item
	 * 
	 * @return
	 */
	public static Result save() {
		
		Form<Item> boundForm = itemForm.bindFromRequest();
		if(itemForm.hasErrors()) {
			flash("error", "Please correct the form below.");
			return badRequest(details.render(boundForm));
		}
		
		Item item = boundForm.get();
				
		if(item.getNoItem() == 0) {
			// newly created item
			// TBM : get a real NoSoumission !
			item.setNoSoumission(38401);
			itemDAO.addItem(item);
			flash("success", String.format("Ajout réussi de l'item %d : %s ", item.getNoItem(), item.getNomItem()));
		}
		else {
			itemDAO.update(item);
			flash("success", String.format("Modification réussie de l'item %d : %s", item.getNoItem(), item.getNomItem()));
		}		
		
		return redirect(routes.Items.list());
	}
	
	/**
	 * Chercher une liste d'Items
	 * where given field contains given value
	 * field : can be "all" to search for all admissible fields
	 * value : the keyword to search for (>= 5 characters)
	 * @param value
	 * @param field
	 * @return
	 */
	public static Result search(String value, String field, 
								String dateDebut, String dateFin, String selectClient, String selectRepresentant) {
		
		if(!validate(field, value)){
			return badRequest(Json.parse("field : " + field + ", value : " + value));
		}
		else{
			ItemDAO.KeyFindItemEnum fieldEnum;
			switch(field){			
			case "NoItem" : fieldEnum = ItemDAO.KeyFindItemEnum.NO_ITEM;
			break;
			case "NomItem": fieldEnum = ItemDAO.KeyFindItemEnum.NOM_ITEM;
			break;
			case "Description": fieldEnum = ItemDAO.KeyFindItemEnum.DESCRIPTION;
			break;
			case "Famille": fieldEnum = ItemDAO.KeyFindItemEnum.FAMILLE;
			break;
			case "all":
			default: fieldEnum = ItemDAO.KeyFindItemEnum.ALL;
			}
			List<Item> items = itemDAO.findByKeyword(fieldEnum, value);
			return ok(Json.toJson(items));
		}
	}
	
	public static boolean validate(String field, String value){
		// TODO
		return true;
	}		
	
}
