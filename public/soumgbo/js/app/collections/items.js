define(['underscore', 
        'backbone',
       'models/item'], 
       function(_, 
                Backbone,
                Item) {
    
    var Items = Backbone.Collection.extend({        
        model: Item    
    });
    
    return Items;
});