define(['underscore',
        'backbone',
        'soumflex/models/item'], function(_, Backbone, Item){
    
  var ItemList = Backbone.Collection.extend({

    // Reference to this collection's model.
    model: Item
  });

    return ItemList;
});