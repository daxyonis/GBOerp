/**
 * home.js
 * Point d'entrée requirejs pour la page d'accueil
 */ 
require([
	'main'     // this loads the config so has to be completed before any module is called
	], function (main) {
    
		  require(['home/app'], function(App){
      App.initialize();  
    });
    
});