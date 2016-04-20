/***************************************************
 * printerGrid.js
 * Define the js code for displaying and interacting
 * the printer grid
 */

/**************************************
 * CONTROLLER FOR AJAX REST REQUESTS
 **************************************/
var printerController = {
            loadData: function(filter) {
                return $.ajax({
                    type: jsRoutes.controllers.Imprimantes.findAll().method,	//"GET",
                    url: jsRoutes.controllers.Imprimantes.findAll().url,		//"/api/printers/",
                    data: filter,
                    dataType: "json",
                    error: function(xhr, ajaxOptions, thrownError) {
                    	$('#errDiv').html("<p class='alert alert-danger'>" + thrownError + "</p>");
  				    }
                });
            },

            insertItem: function(item) {
                return $.ajax({
                    type: jsRoutes.controllers.Imprimantes.add().method,	//"POST",
                    url: jsRoutes.controllers.Imprimantes.add().url,		//"/api/printers/",
                    data: JSON.stringify(item),
                    contentType : 'application/json',
                    dataType: "json",
                    error: function(xhr, ajaxOptions, thrownError) {
			           $('#errDiv').html("<p class='alert alert-danger'>" + thrownError + "</p>");			           
  				    }
                });
            },

            updateItem: function(item) {            	
            	return $.ajax({
                    type: jsRoutes.controllers.Imprimantes.save().method,		//"PUT",
                    url: jsRoutes.controllers.Imprimantes.save().url,			//"/api/printers/",
                    data: JSON.stringify(item),
                    contentType : 'application/json',
                    dataType: "json",
                    error: function(xhr, ajaxOptions, thrownError) {
                    	$('#errDiv').html("<p class='alert alert-danger'>" + thrownError + "</p>"); 				      
  				    }
                });
            },

            deleteItem: function(item) {
                return $.ajax({
                    type: jsRoutes.controllers.Imprimantes.remove().method,		//"DELETE",
                    url: jsRoutes.controllers.Imprimantes.remove().url,			//"/api/printers/",
                    data: JSON.stringify(item),
                    contentType : 'application/json',
                    dataType: "json",
                    error: function(xhr, ajaxOptions, thrownError) {
                    	$('#errDiv').html("<p class='alert alert-danger'>" + thrownError + "</p>");  				        
  				    }
                });
            }
            };

/***************************
 * SETUP THE GRID
 ***************************/
$(function() {	
 
    $("#jsGrid").jsGrid({
        height: "500px",
        width: "100%",
 
        filtering: false,
        editing: true,
        sorting: true,
        paging: false,
        autoload: true,
 
        pageSize: 15,
        pageButtonCount: 5,
 
        deleteConfirm: "Voulez-vous vraiment supprimer cet élément ?",
 
        controller: printerController,
 
        fields: [
            { name: "active", title: "Actif", type: "checkbox", sorting: false },
            { name: "codeSelection", title: "Code", type: "text" },
            { name: "machine", title: "Machine", type: "text" },
            { name: "mode", title: "Mode", type: "text" },
            { name: "vitesse", title: "Vitesse", type:"number", align:"center"},
            { name: "pourcentEncre", title: "% encre", type:"number", align:"center", 
            	itemTemplate: function(value) {
                return value + "%"; }
            },
            { name: "prixEncre", title: "Prix encre", type:"floatnumber", align:"center", 
            	 itemTemplate: function(value) {
                     return value.toFixed(2) + "$"; }
             },
            { name: "perteEncre", title: "Perte encre", type:"number", align:"center",
            	itemTemplate: function(value) {
                    return value + "%"; }
             },
            { name: "margeEncre", title: "Marge encre", type:"number", align:"center",
            	itemTemplate: function(value) {
                    return value + "%"; }
             },
            { name: "codeMachine", title: "Code Machine Genius", type: "text", align:"center" },
            { name: "hauteurMax_po", title: "Hauteur max (po)", type:"number", align:"center"},
            { name: "largeurMax_po", title: "Largeur max (po)", type:"number", align:"center"},
            { type: "control" }
        ]
    });
    
    $( document ).ajaxStart(function() {
    	$('#errDiv').html("");
    });    
 
});