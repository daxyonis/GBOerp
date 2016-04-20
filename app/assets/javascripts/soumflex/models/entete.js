/**
 * Modele pour l'entete de la soumission (rapide flexible)
 */
define(['underscore',
		'backbone'], 
		function(_, Backbone){

    /******************************************************************/
    /* MODELE SOUM-ENTETE                                             */
    /******************************************************************/
			var entete = Backbone.Model.extend({
       
       defaults:{
        // collClient
        // collRep
        rep : "",
        client : "",
        contact : "",
        contactPhone : "",
        contactEmail : "",
        projet : "",
        dateRequise : "",
        dateEstimation : new Date(),
        noError : true
       },
    
      
   });
	
			return entete;
});
