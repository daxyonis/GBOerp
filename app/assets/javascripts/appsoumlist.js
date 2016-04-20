/**
 * appsoumlist.js
 * Point d'entrée requirejs pour la page de la liste des soumissions
 */ 
require([
	'main'     // this loads the config so has to be completed before any module is called
	], function (main) {    
	  require(['appsoumlist/app'], function(App){
      App.initialize();  
    });
    
});