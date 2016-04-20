// rechercheCible.js
// E. Maciejko
// 11.11.2014
//
// Implémente les callbacks sur 
// la recherche de soumission cible par mot-clé
//
// Dépendant de : jquery
// Vues qui dépendent de ce js : mesitems/liste.scala.html
//

// When the page is fully loaded...
$(document).ready(function() {
	
	$("input#inputTextKeyword").attr("value","");
	$("input#inputTextKeyword").attr("placeholder","Entrer mot à chercher");
	
	function clear(){
		// empty the divs
		$("div#listeSoumissions").empty();
	};
	
	//*******************************
	// Loading div animation
	$(document).ajaxStart(function(){
		  $("#loadingDiv").css("display","inline-block");
		});
		$(document).ajaxComplete(function(){
		  $("#loadingDiv").css("display","none");
		});
	
	/*************************************************
	 * Callback function to get a list of Soumissions from the server
	 * given the user-entered keyword
	 **************************************************/
	 function getListSoumissions(event) {
			
			var field = $("select#fieldSelect option:selected").val();
			var numCharacters = $('input#inputTextKeyword').val().length;		
			
			if(numCharacters > 2) {								
				var urlToSearch = $("a#searchLink").attr("href");
				var keyword = $("input#inputTextKeyword").val();
							
				urlToSearch = urlToSearch + "search/?value=" + keyword + "&field=" + 
							  field + "&dateDebut=&dateFin=" + "&selectClient=" + "&selectRepresentant=";						
				//alert("urlToSearch = " + urlToSearch);
				
				// AJAX GET list of items or soumissions
				$.get(urlToSearch, 
						function(resp) { // on success
						afficherListeCible("div#listeSoumissions", resp);							
					})
					.fail(function() { // on failure
						console.log("Request failed");
					});
			}
			else {
				clear();
			}
		};	// end of function getListSoumissions
		
	/*************************************************
	 * Post-GET callback to create a select html element
	 * with the list of returned Soumissions
	 **************************************************/
	function afficherListeCible(dans, json){
			
		clear();    			    	    	
    	
    	// Next build the select
    	if(json.length > 0) {
    		$(dans).append("<h3>Choisir parmi <span class='text-danger'>" + json.length + "</span> résultats trouvés : </h3>");
	    	var htmlStr = "<select id='listeSoum' required='required'>";
	    	$.each(json, function(i, obj) {
	    		// Beginning of option
	    		if(i == 0){
	    			htmlStr += "<option value=" + obj["noSoumission"] + ">";
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
	    	
	    	$('a#copyItems').removeClass("disabled");
    	}
    	else {
    		$(dans).append("<p class='bg-info'>Aucun résultat.</p>");
    		
    		$('a#copyItems').addClass("disabled");
    	}
	};
	/***************************************
	 * 
	 */
	function setSelectedCible(event) {
		// Add the selected soumission as a cible parameter
		var url = $(this).attr("href");
		url += "?cible=" + $("select#listeSoum option:selected").val();
		$(this).attr("href",url);
		//alert($(this).attr("href"));
	}
	
	/********************************************
	 * bind the element events to callbacks
	 */
	$('select#fieldSelect').bind("change",getListSoumissions);
	$('input#inputTextKeyword').bind("keyup",getListSoumissions);	
	$('a#copyItems').bind("click", setSelectedCible)
});