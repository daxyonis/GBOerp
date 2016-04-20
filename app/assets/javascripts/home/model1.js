// Define a requirejs module for this model
// define(arrayofDependencies, factoryFunction);

define(['underscore',
		'backbone'], 
		function(_, Backbone){
			var myModel = Backbone.Model.extend();

			return myModel;
});

