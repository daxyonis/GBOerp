// appsoum.js
// E. Maciejko
// Created: 30.03.2016
// Defines client side behavior of appsoum main page

$(document).ready(function(){
	
	//**************************************
	// CALLBACK DEFINITIONS
	//**************************************
	
	// Callback for changing version
	function versionChange(){
		// The noSoumission is encoded inside the selected version value
		var noSoum = $('select#vers option:selected').val();
		var url;
		if(noSoum > 0){
			url = jsRoutes.controllers.app.AppSoumissions.view(noSoum).url;
			location.href = url;
		} else{
			$('.modal').modal('show');
		}		
	}
	
	//**********************************
	// Nouvelle version CALLBACKS
	// IN-MODAL : callback for when we click on radio button choice between copying items or not
	function copyItemClick(){
		var copyItem = $('input[name=copyItem]:checked').val();
		if(copyItem === "Y"){
			$('select#modal-vers').prop('disabled', false);
		}
		else{
			$('select#modal-vers').prop('disabled', true);
		}
	}
	
	// IN-MODAL : callback for OK button when creating new version
	function newVersionOkClick(){
		// Call the route URL with the right parameters : suite and versionACopier
		var suite = $('button#newVersionOK').val();
		var versionAcopier = "";
		var copyItem = $('input[name=copyItem]:checked').val();		
		if(copyItem === "Y"){
			versionAcopier = $('select#modal-vers option:selected').val();
		}				
		$('.modal').modal('hide');
		location.href = jsRoutes.controllers.app.AppSoumissions.newVersion(suite, versionAcopier).url;
	}
	
	function cancel(){
		// on cancel we must put the select in initial state
		$('select#vers option:contains("' + app.laVersion + '")').prop('selected', true);
	}
	//**********************************

	
	// Dispatch the callbacks to DOM events
	$('select#vers').on('change', versionChange);
	
	$('input[name=copyItem]').on('click', copyItemClick);
	
	$('button#newVersionOK').on('click', newVersionOkClick);
	
	$('button[data-dismiss="modal"]').on('click', cancel);
	$('.modal.fade').on('click', cancel);
	$(document).keyup(function(e) {
        if (e.keyCode == 27) {
            // When escape is pressed, consider it is a cancel
            cancel();
        }
    });
});