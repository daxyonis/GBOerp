package controllers;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import security.*;

import models.SoumFlex;
import services.SoumService;
import views.html.soumflex.page;
/**
 * @author Eva
 *
 */
@Component
@play.mvc.Security.Authenticated(Secured.class)
@Authorize(minLevel=PermissionLevel.VENDOR)
public class SoumRapideFlexible extends Controller {
	
	private static SoumService soumService;
	private static final Form<SoumFlex> soumflexForm = Form.form(SoumFlex.class);
	
	@Autowired
	public void setSoumService(SoumService ss){
		this.soumService = ss;
	}
	
	/**
	 * GET method
	 * Display the form for Soumission Rapide Flexible
	 * @return
	 */
	public static Result show(){
		return ok(page.render(soumflexForm));
	}
	
	/**
	 * POST method
	 * Save values from the form Soumission Rapide Flexible
	 * @return
	 */
	public static Result save(){		
		Form<SoumFlex> boundForm = soumflexForm.bindFromRequest();
		
		if(boundForm.hasErrors()){			
			return badRequest(page.render(boundForm));
		}
		
		SoumFlex flex = boundForm.get();
		
		int newNoSoum = soumService.create(flex);		
		
		if(flex.getTotalVente() > SoumFlex.MAX_SALE_AMOUNT){
			flash("error","Le montant des ventes dépasse le maximum permis de " + SoumFlex.MAX_SALE_AMOUNT +
						  "$: seuls les matériaux ont été copiés dans la soumission. Consulter l'estimateur pour " +
						  "compléter la soumission.");
			return redirect(routes.Application.index());
		}
		return redirect(routes.Soumissions.render(newNoSoum));
	}
}
