// Define a requirejs module for this model
// define(arrayofDependencies, factoryFunction);

define([
    'jquery',
    'underscore',
    'backbone',
    'home/model1'
	], function($, _, Backbone, Model1){
    
		var myView = Backbone.View.extend({
			render: function(){
				this.$el.html(this.model.get("title"));
				return this;
			}
		});
    
    return myView;
});

