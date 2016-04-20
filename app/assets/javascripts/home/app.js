// Module Exemple de l'application1 qui dépend de model1 et view1

define(['jquery',
        'bootstrap',
		'underscore',
		'backbone',
        'home/model1',
        'home/view1'
		], function($, Bootstrap, _, Backbone, Model1, View1){

    var initialize = function(){
        var model1 = new Model1({title: "Interface web pour les soumissions"});
        var view1 = new View1({el: "#container", model: model1});
        view1.render();
    };
    
    return {
        initialize: initialize
    };
    
    
});

