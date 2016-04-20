/**
 * soumflex.js
 * Point d'entrée requirejs pour la soumission rapide flexible
 */
require([
	'main'     // this loads the config so has to be completed before any module is called
	], function (main) { 
    
		  require(['soumflex/app'], function(App){
       App.initialize();         
    });		
    
});