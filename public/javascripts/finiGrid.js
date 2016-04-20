/***************************************************
 * finiGrid.js
 * Define the js code for displaying and interacting
 * the finition grid
 */
/**************************************
 * CONTROLLER FOR AJAX REST REQUESTS
 **************************************/
var finitionController = {
            loadData: function(filter) {
                return $.ajax({
                    type: jsRoutes.controllers.Finitions.findAll().method,	//"GET",
                    url: jsRoutes.controllers.Finitions.findAll().url,		//"/api/finitions/",
                    data: filter,
                    dataType: "json",
                    error: function(xhr, ajaxOptions, thrownError) {
                    	$('#errDiv').html("<div class='alert alert-danger'>" + thrownError + "</div>");
  				    }  				    
                });
            },

            insertItem: function(item) {
            	item.id = -1;	// adding new item
            	return $.ajax({
	                    type: jsRoutes.controllers.Finitions.add().method,	//"POST",
	                    url: jsRoutes.controllers.Finitions.add().url,		//"/api/finitions/",
	                    data: JSON.stringify(item),
	                    contentType : 'application/json',
	                    dataType: "json",
	                    error: function(xhr, ajaxOptions, thrownError) {
				           $('#errDiv').html("<div class='alert alert-danger'>" + xhr.responseText + "</div>");			           
	  				    },
	  				    success: function(){
	  				    	$("#jsGrid").jsGrid("loadData");
	  				    }
                });            	
            },

            updateItem: function(item) {  
            	return $.ajax({
                    type: jsRoutes.controllers.Finitions.save().method,		//"PUT",
                    url: jsRoutes.controllers.Finitions.save().url,			//"/api/finitions/",
                    data: JSON.stringify(item),
                    contentType : 'application/json',
                    dataType: "json",
                    error: function(xhr, ajaxOptions, thrownError) {
                    	$('#errDiv').html("<div class='alert alert-danger'>" + xhr.responseText  + "</div>"); 				      
  				    }  				  
                });            	          
            },

            deleteItem: function(item) {
                return $.ajax({
                    type: jsRoutes.controllers.Finitions.remove().method,		//"DELETE",
                    url: jsRoutes.controllers.Finitions.remove().url,			//"/api/finitions/",
                    data: JSON.stringify(item),
                    contentType : 'application/json',
                    dataType: "json",
                    error: function(xhr, ajaxOptions, thrownError) {
                    	$('#errDiv').html("<p class='alert alert-danger'>" + xhr.responseText  + "</p>");  				        
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
 
        controller: finitionController,
 
        fields: [            
            { name: "active", title: "Actif", type: "checkbox", sorting: false },
            { name: "codeGBC", title: "Code", type: "text", align:"center" },
            { name: "type", title: "Type", type: "text" },
            { name: "taille", title: "Taille", type: "text", align:"center" },
            { name: "cotes1", title: "Cotes 1", type:"text"},
            { name: "cotes2", title: "Cotes 2", type:"text"},  
            { name: "coutFixe", title: "Coût fixe", type:"floatnumber", align:"center", 
           	 itemTemplate: function(value) {
                    return value ? (value.toFixed(2) + "$") : "-"; }
            },
            { name: "prix", title: "Coût", type:"floatnumber", align:"center", 
            	 itemTemplate: function(value) {
                     return value ? (value.toFixed(2) + "$") : "-"; }
             },            
            { name: "unite", title: "Unités", type: "text", align:"center" },            
            { type: "control" }
        ]
    });
    
    $( document ).ajaxStart(function() {
    	$('#errDiv').html("");
    });    
 
});