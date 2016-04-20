// rechercheItems.js
// E. Maciejko
// 06.10.2014
//
// Implémente les callbacks sur la sélection de boutons radio
// et la recherche de soumission ou item par mot-clé
//
// Dépendant de : jquery
// Fichiers qui dépendent de ce js : ajout.scala.html
//

//*******************************************************
// When the page is fully loaded...
$(document).ready(function() {
	
	//*********************************
	// Init
	function init(){
		// Ajax load of all Representants       
    	var route = jsRoutes.controllers.Reps.findAll();
        var def1 = $.getJSON(route.url,
	                function(jsonData) {
	        			updateRepresentants(jsonData);
					 })
					 .fail(function( jqxhr, textStatus, error ) {
					     var err = textStatus + ", " + error;
					     console.log( "Request Failed: " + err );
					 });
        
        // Ajax load of all Clients
        route = jsRoutes.controllers.Clients.findAll();
        var def2 = $.getJSON(route.url,
	                function(jsonData) {
        				updateClients(jsonData);
					})
					.fail(function( jqxhr, textStatus, error ) {
						    var err = textStatus + ", " + error;
						    console.log( "Request Failed: " + err );
					});
     // Sync all asynchronous ajax calls before updating view        
        $.when(def1, def2).done(function(){        	
        	registerClientAndRepCallbacks();            
        });
	}

	//*********************
	// Templating                        
	function compileAndAddHtml (templateId, context, targetId){
	  // Handlebars
	  // Grab the template script
	  var theTemplateScript = $('#'+templateId).html();    
	  // Compile the template
	  var theTemplate = Handlebars.compile(theTemplateScript);
	  // Pass our data to the template
	  var theCompiledHtml = theTemplate(context);
	  // Add the compiled html to the page
	  $('#'+targetId).html(theCompiledHtml);
	}
	function updateRepresentants(jsonData){
		compileAndAddHtml('rep-select-template',{rep : jsonData},'selectRepresentant');
		$('#selectRepresentant').css("width","200px");
	}

	function updateClients(jsonData){
		compileAndAddHtml('client-select-template',{client : jsonData},'selectClient');
		$('#selectClient').css("width","200px");
	}
	//*********************************
	// Clear
	function clear() {
		// empty the divs
		$("#listeItems").empty();
		$("#listeSoumissions").empty();
		$('a#ajoutItems').addClass("hidden");
	}

	//***************************************
	// Enable/disable a filter checkbox and object
	//**************************************
	function filterEnabled(trueOrFalse,checkboxId, divId){
		$("#"+ checkboxId + ":checkbox").prop("checked",trueOrFalse);
		if(trueOrFalse == true){
			$("#" + divId).removeClass("uncheckedLabel");		
		}
		else{
			$("#" + divId).addClass("uncheckedLabel");		
		}
	}
	//***************************************
	//Register callbacks for client and rep
	//**************************************
	function registerClientAndRepCallbacks(){
		
		// When we select a client or rep, automatically check the box
		$("#selectClient").change(function(){
			filterEnabled(true,"filtreClient", "divFiltreClient");

			getListItemsOrSoumissions();
		});
		
		$("#selectRepresentant").change(function(){
			var rep = $(this).val(); 
			if(rep == null || rep === "NA"){
				filterEnabled(false,"filtreVendeur", "divFiltreVendeur");
			}
			else{
				filterEnabled(true,"filtreVendeur", "divFiltreVendeur");
			}
			
			getListItemsOrSoumissions();
		});
		
		$("#filtreClient:checkbox").click(function(){
			$("#divFiltreClient").toggleClass("uncheckedLabel");
			
			getListItemsOrSoumissions();
		});
		$("#filtreVendeur:checkbox").click(function(){	
			// Change css
			$("#divFiltreVendeur").toggleClass("uncheckedLabel");
			
			getListItemsOrSoumissions();
		});
		
	}

	//*********************************************	
	// Enable or disable all filters
	function enableFilters(enable){
		if(enable){
			$('div#filtres').removeClass('hidden');		
		}
		else{
			$('div#filtres').addClass('hidden');	
		}
	}
	
	//*********************************************	
	// Initialization
	init();
	
	//*********************************************	
	// Setting the datepicker config and options
	$.datepicker.setDefaults( $.datepicker.regional[ "fr-CA" ] );
	$( ".mydatepicker" ).datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "yy-mm-dd"
	    });		
	$( ".mydatepicker" ).datepicker( "option", "disabled", true );
	//*********************************************	
		
	
	//*******************************
	// Loading div animation
	$(document).ajaxStart(function(){
		  $("#loadingDiv").css("display","inline-block");
		});
		$(document).ajaxComplete(function(){
		  $("#loadingDiv").css("display","none");
		});
			
	//*********************************************
	// Callback when user clicks on date filter checkbox
	//*********************************************			
	$("#filtreDate:checkbox").click(function(){							
		if( $(this).prop("checked") ){			
			$( ".mydatepicker" ).datepicker( "option", "disabled", false );					
			$(".ui-datepicker").css('margin-left', '-50px', 'margin-top', '-50px');						
		}
		else{
			$( ".mydatepicker" ).datepicker( "option", "disabled", true );			
		}
		$("#divFiltreDate").toggleClass("uncheckedLabel");
		getListItemsOrSoumissions();
	});
	
	$('#dateDebut').change(function(){		
		// Date debut change : c'est le min de Date fin		
		var currentDate = $(this).datepicker( "getDate" );
		$('#dateFin').datepicker("option", "minDate", currentDate);		
		filterEnabled(true,"filtreDate", "divFiltreDate");
		getListItemsOrSoumissions();
	});
	
	$('#dateFin').change(function(){		
		// Date debut change : c'est le min de Date fin		
		var currentDate = $(this).datepicker( "getDate" );
		$('#dateDebut').datepicker("option", "maxDate", currentDate);
		filterEnabled(true,"filtreDate", "divFiltreDate");	
		getListItemsOrSoumissions();
	});
		
	
	//***************************************
	// Callback when user clicks on radio group
	//**************************************	
	$("input[name='choixItemSoum']").click(function() {
		
		clear();
		
		// empty the select
		$("select#fieldSelect").empty();				
		
		if($(this).prop("checked") && $(this).attr("id") == "choixSoum"){			
			// on a choisi de chercher par Soumission
			var htmlStr = "<option value='Suite' selected='selected'>Numéro de soumission</option>";
			htmlStr +="<option value='Projet'>Projet</option>";
			htmlStr +="<option value='Client'>Client</option>";
			htmlStr +="<option value='all'>Tout</option>";
			$("select#fieldSelect").html(htmlStr);
			enableFilters(true);
		}
		else if($(this).prop("checked")  && $(this).attr("id") == "choixItem"){
			// on choisit de chercher par item			
			var htmlStr = "<option value='all' selected='selected'>Tout</option>";
			htmlStr +="<option value='NoItem'>No Item</option>";
			htmlStr +="<option value='NomItem'>Nom Item</option>";
			htmlStr +="<option value='Description'>Description</option>";
			$("select#fieldSelect").html(htmlStr);
			enableFilters(false);
		}
		
		// As if an event happened
		getListItemsOrSoumissions();
	});
		
	
	
	//***************************************
	// Callback to get values from server
	//***************************************
	 function getListItemsOrSoumissions(event) {
		
		var field = $("select#fieldSelect option:selected").val();
		var numCharacters = $('input#inputTextKeyword').val().length;		
		
		if(numCharacters > 2) {
			
			var searchBy =  $("input[name='choixItemSoum']:checked").attr("id");
			var urlToSearch = $("input[name='choixItemSoum']:checked").val();
			var keyword = $('input#inputTextKeyword').val();
						
			urlToSearch = urlToSearch + "search/";
			var filtre = "?value=" + keyword + "&field=" + field;						
			//alert("searchBy = " + searchBy + ", urlToSearch = " + urlToSearch);
			
			// Serialize the filter components
			if ($('#filtreDate').prop("checked")) {
					filtre += "&" +  $('#dateDebut').serialize();			
					filtre += "&" +  $('#dateFin').serialize();
			}
			else{
				filtre += "&dateDebut=&dateFin=";
			}
			
			if ($('#filtreClient').prop("checked")) {
				filtre += "&" + $('#selectClient').serialize();
			}
			else{
				filtre += "&selectClient=";
			}
			
			if ($('#filtreVendeur').prop("checked")) {
				filtre += "&" + $('#selectRepresentant').serialize(); 					
			}
			else{
				filtre += "&selectRepresentant=";
			}
				
			//alert(urlToSearch + filtre);
					
			// AJAX GET list of items or soumissions
			$.get(urlToSearch + filtre,					
				  function(resp) { // on success
						if(searchBy == "choixItem") {
							afficherListe("#listeItems", "Items trouvés", resp);
						}
						else {
							afficherSoumissions("#listeSoumissions", "Soumissions trouvées", resp);
						}						
				})
				.fail(function() { // on failure
					console.log("Request failed");
				});
		}
		else {
			clear();
		}
	};	// end of function getListItemsOrSoumissions
	 
	//***************************************
	// Callback to get all items from soumission
	// selected in dropdown list (select element)
	//***************************************
	function getItemsFromSelectedSoum(event) {
		
		var noSoumission = $("select#listeSoum option:selected").val();
		// hack to get the url from scala template
		var urlToSearch = $("input#choixItem").val();
		// add ulr parameter
		urlToSearch += noSoumission;
		
		// AJAX GET list of items
		$.get(urlToSearch, 
				function(resp) { // on success
				afficherListe("#listeItems", "Items trouvés", resp);					
			})
			.fail(function() { // on failure
				console.log("Request failed");
			});
	}
	//***************************************
	// Callback to post values to server
	//***************************************
	 function postListItems(event) {
		 event.preventDefault();
		 
		 //get the list of selected noItem
		 var arrayOfCheckboxes = $('input:checkbox:checked').serializeArray(); 		 
		 
		 if(arrayOfCheckboxes.length > 0){
			 // AJAX post
			 var url = $('a#ajoutItems').prop("href");			 
			 
			 $.ajax({
				  type: "POST",
				  url: url,
				  data: arrayOfCheckboxes,
				  success: function(data) { // on success
				 			console.log(data);
				 			window.location.href = $('a#mesItemsLink').prop("href");
			 				}				  
				});
		 }	 
		 
	 }; // end of function postListItems

	//***************************************
	//BIND html elements to callbacks
	//***************************************
    $('input#inputTextKeyword').bind("keyup",getListItemsOrSoumissions);
    $('select#fieldSelect').bind("change",getListItemsOrSoumissions);
    $('a#ajoutItems').bind("click",postListItems);    
	
    //***************************************
    // RESPONSE FUNCTIONS
	// Both those functions get a json object as an argument,
	// which itself also holds other objects.
	// 1. The first function is supposed to get an object 
	//	 	containing just a list of items.
	// 2. The second function is supposed to get an object containing
	//	 	a list of soumissions objects    
	function afficherListe(dans, subtitle, json) {
		
		// First empty the <div> completely and add a title.
		$(dans).empty();
		
		$(dans).append("<h3>" + subtitle + "</h3>");
		var str = "<p class='bg-info'>La recherche a retourné " + json.length;
		if(json.length > 1){
			str += " items.</p>";
		} else {
			str += " item.</p>";
		}
		
		$(dans).append(str);
			
		// Then add every object contained in the list.
		if(json.length > 0){
			var htmlStr = "<button id='checkAll' class='btn btn-default espace'>Cocher tous</button>" +
						  "<button id='checkNone' class='btn btn-default espace'>Cocher aucun</button>" 
			htmlStr += "<div class='table-responsive'>";
			htmlStr += "<table class='table table-striped'>";
			$.each(json, function(i, obj) {
				if(i == 0){	// first line = header
					htmlStr += "<thead><tr>";
					htmlStr += "<th></th>"
	//				for (var prop in obj){
	//					htmlStr += "<th>" + prop + "</th>";
	//				}
					htmlStr += "<th>Numéro</th><th>Nom Item</th><th>Description</th><th>Largeur</th><th>Hauteur</th>";
					htmlStr += "<th>Famille</th><th>Source Prod</th><th>NoSoum</th>";
					htmlStr += "</tr></thead>";
					htmlStr += "<tbody>";
				}
				
				htmlStr += "<tr>";	
				htmlStr += "<td><input type='checkbox' name='NoItem' value=" + obj["noItem"] + " /></td>";
	//			for (var prop in obj){
	//				htmlStr += "<td>" + obj[prop] + "</td>";
	//			}
				htmlStr += "<td>" + obj["noItem"] + "</td>";
				htmlStr += "<td>" + obj["nomItem"] + "</td>";
				htmlStr += "<td>" + obj["description"] + "</td>";
				htmlStr += "<td>" + obj["largeur"] + "</td>";
				htmlStr += "<td>" + obj["hauteur"] + "</td>";
				htmlStr += "<td>" + obj["famille"] + "</td>";
				htmlStr += "<td>" + obj["sourceProd"] + "</td>";
				htmlStr += "<td>" + obj["noSoumission"] + "</td>";
				
				htmlStr += "</tr>";
			});
			htmlStr += "</tbody></table>";
			htmlStr += "</div>";
			$(dans).append(htmlStr);
			$('a#ajoutItems').removeClass("hidden");
			
			$('input:checkbox').click(function(){
				if($('input:checkbox:checked').length > 0){
					// any one is checked
					$('a#ajoutItems').removeClass("disabled");
				}
				else {
					// none is checked
					$('a#ajoutItems').addClass("disabled");					
				}
		    });
			
			$('#checkAll').click(function(){
				$("input:checkbox[name*='NoItem']").prop("checked",true);
				// any one is checked
				$('a#ajoutItems').removeClass("disabled");
			});
			$('#checkNone').click(function(){
				$("input:checkbox[name*='NoItem']").prop("checked",false);
				// none is checked
				$('a#ajoutItems').addClass("disabled");	
			});
			
		} else {
			$('a#ajoutItems').addClass("hidden");
		}
	};	// end function
	
	//***********************************
	// RESPONSE FUNCTION
    // For Soumissions
	//***********************************	
    function afficherSoumissions(dans, subtitle, json) {
    	clear();
    	
    	$(dans).append("<h3>" + subtitle + "</h3>");    	    	
    	    	
    	var str = "<p class='bg-info'>La recherche a retourné " + json.length;
		if(json.length > 1){
			str += " soumissions.</p>";
		} else {
			str += " soumission.</p>";
		}
		$(dans).append(str);
		
    	// Next build the select
    	if(json.length > 0) {
	    	var htmlStr = "<select id='listeSoum' required='required' size=10>";
	    	$.each(json, function(i, obj) {
	    		// Beginning of option
	    		if(i == 0){
	    			htmlStr += "<option selected='selected' value=" + obj["noSoumission"] + ">";
	    		}
	    		else{
	    			htmlStr += "<option value=" + obj["noSoumission"] + ">";
	    		}
	    		// Descriptive string
	    		htmlStr += obj["suite"] + "-" + obj["version"] + ", Client : " + obj["clientSoum"] + ", Projet : " + obj["projet"];
	    		htmlStr += "</option>";
	    	});
	    	// close the select and append
	    	htmlStr += "</select>";
	    	
	    	$(dans).append(htmlStr);
	    	
	    	// bind the select to callback
	    	$("select#listeSoum").bind("change",getItemsFromSelectedSoum);
	    	// call the callback for default selected soumission
	    	getItemsFromSelectedSoum();
    	}
    	
    }	// end of function
	
});
