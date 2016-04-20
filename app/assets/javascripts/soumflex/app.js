/**
 * Module soumflex : application SPA de la soumission rapide flexible
 *  
 */
define(['jquery',
		'jsroutes',
		'backbone',
		'soumflex/models/item',
        'soumflex/collections/items',
        'soumflex/collections/clients',
        'soumflex/collections/reps',
        'soumflex/collections/flexibles',
        'soumflex/models/entete',
        'soumflex/models/soumflex',
        'soumflex/views/soumflexview',
        'soumflex/views/enteteview',
        'soumflex/views/itemsview'], function(
		$, jsRoutes, Backbone,
		Item, Items, Clients, Reps, Flexibles,
		Entete, SoumFlex, SoumFlexView, EnteteView, ItemsView){	
	
    var startApp = function(initialData){        
                
        var clients = new Clients(initialData.clients);
        var reps = new Reps(initialData.reps);
        
        var bus = _.extend({}, Backbone.Events); 
        
        // Creer l'entete
        var entete = new Entete({collClient: clients, collRep : reps});
        
        // Creer la vue entete
        var enteteView = new EnteteView({el: "#entete", model: entete, bus: bus});
        enteteView.render();
        
        // Creer la collection des items
        var items = new Items();                 
        
        // Creer la vue des items
        var lesProduits = new Flexibles(initialData.flexibles);
        var itemsView = new ItemsView({el: "#details", collection:items, 
                                       collProd: lesProduits, 
                                       refInfographie : initialData.referenceInfographie,
                                       refMaindOeuvre : initialData.referenceMaindOeuvre,
                                       refLaminage : initialData.referenceLaminage
                                       });
        
        items.push(new Item({id : 0, _id : 1, nom : 'Premier item'}));
        
        // Creer la soumission
        var soumflex = new SoumFlex({entete : entete, items : items});
        
        // Creer la vue soumission et initialiser
        var view = new SoumFlexView({el: "#resumeSoum", model: soumflex, bus: bus});
        
        // MAJ la soumview lorsqu'un item change
        items.on('change ' +
                 'change:impression '+
                 'change:infographie '+
                 'change:maindoeuvre '+
                 'change:finition '+
                 'change:laminage', view.render, view);     
        
        items.on('remove', view.render, view);
    };
	
	var initialize = function(){
		// Bootstrap all needed data
		var initialData = {};
		
		// Ajax load of all Representants       
		var	route = jsRoutes.controllers.Reps.findAll();
		var def1 = $.getJSON(route.url,
			function(jsonData) {
			  initialData.reps = jsonData;
					 })
					 .fail(function( jqxhr, textStatus, error ) {
						 var err = textStatus + ", " + error;
						 console.log( "Request Failed: " + err );
					 });
			
		// Ajax load of all Clients
		route = jsRoutes.controllers.Clients.findAll();
		var def2 = $.getJSON(route.url,
			 function(jsonData) {
				initialData.clients = jsonData;
						})
						.fail(function( jqxhr, textStatus, error ) {
								var err = textStatus + ", " + error;
								console.log( "Request Failed: " + err );
						});
			
		// Ajax load of Materiels
		route = jsRoutes.controllers.Flexibles.findAllDTO();
		var def3 = $.getJSON(route.url,
						function(jsonData) {
							initialData.flexibles = jsonData;
						})
						.fail(function( jqxhr, textStatus, error ) {
							var err = textStatus + ", " + error;
							console.log( "Request Failed: " + err );
						});			
			
		// Ajax load of taux Infographie
		route = jsRoutes.controllers.Activites.findInfographie();       
		var def4 = $.getJSON(route.url,
					function(jsonData){
					initialData.referenceInfographie = jsonData;        	
			})
			.fail(function( jqxhr, textStatus, error ) {
				var err = textStatus + ", " + error;
				console.log( "Request Failed: " + err );
			  });
			
		// Ajax load of taux Main d'Oeuvre
	   route = jsRoutes.controllers.Activites.findManipulation();        
		var def5 = $.getJSON(route.url,
			function(jsonData){            
				initialData.referenceMaindOeuvre = jsonData;        	
		})
		.fail(function( jqxhr, textStatus, error ) {
				var err = textStatus + ", " + error;
				console.log( "Request Failed: " + err );
		});
			
		// Ajax load of taux Laminage
		route = jsRoutes.controllers.Activites.findLaminage();
		var def6 = $.getJSON(route.url,
		  function(jsonData){
			initialData.referenceLaminage = jsonData;
		})
		.fail(function( jqxhr, textStatus, error ) {
				var err = textStatus + ", " + error;
				console.log( "Request Failed: " + err );
			  });
					
		// Sync all asynchronous ajax calls before updating view        
		$.when(def1, def2, def3, def4, def5, def6).done(function(){        	
			startApp(initialData); 
		});
	};
    
    return {
        initialize: initialize
    };		
    
    
});

