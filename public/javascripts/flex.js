// flex.js
// E. Maciejko
// 12.01.2015
//
// Implémente la validation du formulaire Flexible
//
// Dépendant de : jquery, plugins.jquery.com/spectrum/
// Vues qui dépendent de ce js : flex/details.scala.html, flex/list.scala.html
//

/***************************************
* AJAX DELETE a Flexible record
* Don't know why but the delete call does 
* not work when we put it within the 
* $(document).ready()...
***************************************/ 
function del(urlToDelete) {
	
	if(confirm("Voulez-vous vraiment supprimer cet élément ?")){
		$.ajax({
			url:      urlToDelete,
			type:     'DELETE',
			success:  function(results) {
			// refresh the page after letting the time to delete
			setTimeout(function() {
				location.reload();
				}, 500);
		}
		});
	}
}
	
// When the page is fully loaded...
$(document).ready(function() {
				
	/***************************************
	* AJAX GET style values from server
	***************************************/
	function loadStyles(){		
		$.get("/flex/styles",
			  function(json){					
					// Apply the styles to the page
					$.each(json, function(i, obj) {						
						//console.log(obj);	// debug
						var className = "td_" + obj["rowId"] + "_" + obj["colIndex"];
						// console.log(className);	// debug
						if(obj["cssClass"] != null){
							$("td."+className).addClass(obj["cssClass"]);
						}
						if(obj["cssColor"] != null){
							$("td."+className).css("color", obj["cssColor"]);
						}
						if(obj["cssBgColor"] != null){
							$("td."+className).css("background-color", obj["cssBgColor"]);
						}
					});
				})
				.fail(function() { // on failure
					console.log("Request failed");
				});		
	}
	
	/******************************************
	* AJAX POST style values to the server	 
	******************************************/
	function saveStyles(jsonStyle){
		// console.log(jsonStyle);	// debug
		$.ajax({ type	: 'POST',
				url		: '/flex/styles/',
				data	: JSON.stringify(jsonStyle),
				contentType : 'application/json',
				success: function(data){
				     //On ajax success do this
				     // console.log(data);	// debug
				      },
				   error: function(xhr, ajaxOptions, thrownError) {
				      //On error do this
				        //$.mobile.loading('hide')
				        if (xhr.status == 200) {
				            console.log(ajaxOptions);
				        }
				        else {
				        	console.log('Error status = ' + xhr.status);
				        	console.log(thrownError);
				        }
				    }
		});
	}
	/*********************************************
	* AJAX DELETE style from a cell
	*********************************************/
	function removeStyle(jsonStyle){
		var urlToDelete = '/flex/styles/?row=' + jsonStyle["rowId"] + '&col=' + jsonStyle["colIndex"];
		//console.log(urlToDelete);	// debug
		
		$.ajax({ type	: 'DELETE',
				url		: urlToDelete,				
				success: function(results){
					// refresh the page
					setTimeout(function(){
						location.reload();		
					}, 333);
			      },
			   error: function(xhr, ajaxOptions, thrownError) {
			      //On error do this
			        //$.mobile.loading('hide')
			        if (xhr.status == 200) {
			            console.log(ajaxOptions);
			        }
			        else {
			        	console.log('Error status = ' + xhr.status);
			        	console.log(thrownError);
			        }
			    }
		});
	}
	
	/**********************************************
	 * JS global variables
	 **********************************************/	
	// With Inspector tool, estimate the height of : 
	// navbar + h2 + top header of table + all vertical margins = 198px ~ 200px
	var newHeight = screen.availHeight - 160;	
	
	// color picker
	var paletteDefault = [
	      	            ["#000000", "#434343", "#666666", "#999999", "#b7b7b7", "#cccccc", "#d9d9d9", "#efefef", "#f3f3f3", "#ffffff"],
	    			    ["#980000", "#ff0000", "#ff9900", "#ffff00", "#00ff00", "#00ffff", "#4a86e8", "#0000ff", "#9900ff", "#ff00ff"],
	    			    ["#e6b8af", "#f4cccc", "#fce5cd", "#fff2cc", "#d9ead3", "#d9ead3", "#c9daf8", "#cfe2f3", "#d9d2e9", "#ead1dc"],
	    			    ["#dd7e6b", "#ea9999", "#f9cb9c", "#ffe599", "#b6d7a8", "#a2c4c9", "#a4c2f4", "#9fc5e8", "#b4a7d6", "#d5a6bd"],
	    			    ["#cc4125", "#e06666", "#f6b26b", "#ffd966", "#93c47d", "#76a5af", "#6d9eeb", "#6fa8dc", "#8e7cc3", "#c27ba0"],
	    			    ["#a61c00", "#cc0000", "#e69138", "#f1c232", "#6aa84f", "#45818e", "#3c78d8", "#3d85c6", "#674ea7", "#a64d79"],
	    			    ["#85200c", "#990000", "#b45f06", "#bf9000", "#38761d", "#134f5c", "#1155cc", "#0b5394", "#351c75", "#741b47"],
	    			    ["#5b0f00", "#660000", "#783f04", "#7f6000", "#274e13", "#0c343d", "#1c4587", "#073763", "#20124d", "#4c1130"]
	    	        ];
	var currentColor = "#000000";	// black
	var currentBgColor = "#ffff00";	// yellow
	var colorpickerInput = $("#colorPck");
	var bgcolorpickerInput = $("#colorPck2");
	
	var clickedCellId = 0;
	var clickedCellCol = 0;
	
	/***************************************
	 * init code
	 ***************************************/
	// Dynamically set table height
	$('#flexTable').attr('data-height', newHeight);
	
	// Load styles right away
	loadStyles();

	// details : modal form
	$('#myModal').modal('show');
	$('#myModal button.close').on("click", function(){
		window.location.href = "/flex/";
	});
	
	/***************************************
	 * utility functions
	 ***************************************/
	function tdClassName(){
		return ("td_" + clickedCellId + "_" + clickedCellCol);
	}		
	
	function getStyle(){
		var appliedClasses = $('td.'+ tdClassName()).attr("class");
		var styleClasses = "";
		
		if(appliedClasses.indexOf("TDcenter") >= 0)
			styleClasses += "TDcenter ";
		else if(appliedClasses.indexOf("TDright") >= 0)
			styleClasses += "TDright ";
		else if(appliedClasses.indexOf("TDleft") >= 0)
			styleClasses += "TDleft ";
		
		if(appliedClasses.indexOf("TDbold") >= 0)
			styleClasses += "TDbold ";
		
		if(appliedClasses.indexOf("TDitalic") >= 0)
			styleClasses += "TDitalic ";
				
		var currentStyle = {"rowId" : clickedCellId,
				"colIndex" 	: clickedCellCol,
				"cssClass" 	: styleClasses,
				"cssColor" 	: $('td.'+ tdClassName()).css("color"),
				"cssBgColor": $('td.'+ tdClassName()).css("background-color")
				};
		
		return currentStyle;
	}
	
	function removeClass(myClass){
		if(clickedCellId > 0 && clickedCellCol > 0){
			$('td.'+ tdClassName()).removeClass(myClass);
		}
	}
	
	function applyClass(myClass){ 
		if(clickedCellId > 0 && clickedCellCol > 0){
			$('td.'+ tdClassName()).addClass(myClass);		
			saveStyles(getStyle());
		}
	}
	
	function applyCss(prop, value){		
		if(clickedCellId > 0 && clickedCellCol > 0){
			$('td.'+ tdClassName()).css(prop, value);
			saveStyles(getStyle());
		}
	}
	
	function printTable(title, tableId){
		// Add an iframe
		$('div.mainContent').append('<iframe id="ifmcontentstoprint" style="height: 1px; width: 1px; position: absolute">text</iframe>');
		var content = document.getElementById(tableId);
		var pri = document.getElementById("ifmcontentstoprint").contentWindow;
		pri.document.open();	
		pri.document.write('<'+'html'+'><'+'head'+'><'+'style'+'>');
		pri.document.write('table{border-collapse:collapse} th{font-weight:bold} ');
		pri.document.write('td,th{border: 1px solid #aaa; text-align: center; vertical-align: middle; font-size: x-small;}');
		pri.document.write('.noprint{display: none}');
		pri.document.write('h2{text-align:center}');
	    pri.document.write('<'+'/'+'style'+'><'+'/'+'head'+'><'+'body'+'>');
	    pri.document.write('<'+'h2'+'>'+title+'<'+'/'+'h2'+'>');
	    pri.document.write('<'+'table'+'>');
		pri.document.write(content.innerHTML);
		pri.document.write('<'+'/'+'table'+'>');
		pri.document.write('<'+'/'+'body'+'><'+'/'+'html'+'>');
		setTimeout(function() {
			pri.document.close();
			pri.focus();
			pri.print();
			}, 333);
		setTimeout(function() {
			// Remove the iframe
			$('#ifmcontentstoprint').remove();
			}, 1000);
	}
	/************************************************
	 * Event callbacks
	 ************************************************/
	$("#flexTable").delegate("td", "click", function() {
	  var $this = $(this);
	  $('#flexTable td').css("border","1px solid #ddd");
	  $this.css("border","2px solid cyan");
	  
	  var col = $(this).parent().children().index($(this));		  
	  var id  = $(this).parent().attr('id');		  
	  clickedCellId = id;
	  clickedCellCol = col;	  
	  //console.log('Cell (' + clickedCellId + ', ' + clickedCellCol + ')');	// debug
	});
							
	$('#btBold').bind("click", function(){
		applyClass("TDbold");									
	});
	$('#btItal').bind("click", function(){
		applyClass("TDitalic");					
	});
	
	$('#btAlignLeft').bind("click", function(){
		removeClass("TDcenter");
		removeClass("TDright");
		applyClass("TDleft");
	});
	$('#btAlignCenter').bind("click", function(){
		removeClass("TDleft");
		removeClass("TDright");
		applyClass("TDcenter");
	});
	$('#btAlignRight').bind("click", function(){
		removeClass("TDcenter");
		removeClass("TDleft");
		applyClass("TDright");
	});
	
	$('#btBgColor').bind("click", function(){
		applyCss("background-color", currentBgColor);
	});
	
	$('#btColor').bind("click", function(){
		applyCss("color", currentColor);
	});
	
	$('#btClear').bind("click", function(){
		if(clickedCellId > 0 && clickedCellCol > 0){
			removeStyle(getStyle());
		}
	});

	$('#btPrint').bind("click", function(){
		printTable("Liste des Flexibles", "flexTable");
	});

	/********************************************
	 * Color pickers code
	 * Uses the spectrum jquery plugin
	 * http://plugins.jquery.com/spectrum/
	 ********************************************/
	colorpickerInput.spectrum({
        color: currentColor,
        flat: false,
        showInput: false,
        className: "full-spectrum",
        showInitial: false,
        showPalette: true,
        showPaletteOnly : true,
        showSelectionPalette: true,
        hideAfterPaletteSelect: true,
        maxPaletteSize: 10,
        preferredFormat: "hex",
        localStorageKey: "spectrum.color",
        move: function (color) {
        	currentColor = color;
        },
        show: function () {

        },
        beforeShow: function () {

        },
        hide: function (color) {
        	currentColor = color;
        },
        change: function() {
            
        },
        palette: paletteDefault
    });	// end of spectrum()
	
	bgcolorpickerInput.spectrum({
        color: currentBgColor,
        flat: false,
        showInput: false,
        className: "full-spectrum",
        showInitial: false,
        showPalette: true,
        showPaletteOnly : true,
        showSelectionPalette: true,
        hideAfterPaletteSelect: true,
        maxPaletteSize: 10,
        preferredFormat: "hex",
        localStorageKey: "spectrum.bgcolor",
        move: function (color) {
        	currentBgColor = color;
        },
        show: function () {

        },
        beforeShow: function () {

        },
        hide: function (color) {
        	currentBgColor = color;
        },
        change: function() {
            
        },
        palette: paletteDefault
    });	// end of spectrum()
		
});	// end of $(document).ready()
