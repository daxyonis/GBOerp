/**
 * itemsPage.js
 * Point d'entrée requirejs pour la liste des items d'une soumission
 */
require([
	'main' // this loads the config so has to be completed before any module is called
	], function (main) {
    
    require(['itemsPage/app'], function(App){
        App.initialize(serverItems);
    });
    
});