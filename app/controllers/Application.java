package controllers;

import org.springframework.stereotype.Component;

import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;
import security.Authorize;
import security.PermissionLevel;
import security.Secured;
import views.html.index;

@Component
public class Application extends Controller {

    public static Result index() {
        return ok(index.render(""));    	
    }    
    
    @play.mvc.Security.Authenticated(Secured.class)
    @Authorize(minLevel=PermissionLevel.USER)
    public static Result test(){
    	double i = Math.random();
    	if(i < 0.5){
    		throw new RuntimeException("Test Value was < 0.5 so we threw an exception !!");
    	}
    	return ok("Test Value did not return an exception... try again.");
    }
    
    /**
     * Javascript router action
     * @return
     */
    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
            Routes.javascriptRouter("jsRoutes",
                routes.javascript.Accessoires.find(),
                routes.javascript.Accessoires.findTypesForFlexible(),
                routes.javascript.Activites.findInfographie(),
                routes.javascript.Activites.findManipulation(),
                routes.javascript.Activites.findLaminage(),
                routes.javascript.Clients.findAll(),
                routes.javascript.Finitions.findForFlexibleCategory(),
                routes.javascript.Finitions.findAll(),                
                routes.javascript.Finitions.add(),
                routes.javascript.Finitions.save(),
                routes.javascript.Finitions.remove(),
                routes.javascript.Flexibles.findAll(),
                routes.javascript.Flexibles.findAllDTO(),
                routes.javascript.Laminages.findForCategory(),
                routes.javascript.Imprimantes.findAll(),
                routes.javascript.Imprimantes.findForFlexible(),
                routes.javascript.Imprimantes.save(),
                routes.javascript.Imprimantes.add(),
                routes.javascript.Imprimantes.remove(),
                routes.javascript.Reps.findAll(),
                routes.javascript.Statuts.findAll(),
                routes.javascript.Soumissions.order(),
                controllers.app.routes.javascript.AppSoumissions.view(),
                controllers.app.routes.javascript.AppItems.voidItem(),
                controllers.app.routes.javascript.AppItems.readAllForSoum(),
                controllers.app.routes.javascript.AppItems.create(),                
                controllers.app.routes.javascript.AppItems.read(), 
                controllers.app.routes.javascript.AppItems.update(),
                controllers.app.routes.javascript.AppItems.delete(),
                controllers.app.routes.javascript.AppSoumissions.copy(),
                controllers.app.routes.javascript.AppSoumissions.delete(),
                controllers.app.routes.javascript.AppSoumissions.newSoum(),
                controllers.app.routes.javascript.AppSoumissions.newVersion()
            )
        );
    }
}
