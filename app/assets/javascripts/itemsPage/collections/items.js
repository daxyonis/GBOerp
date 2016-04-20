define(['underscore', 
        'backbone',
        'itemsPage/models/item'
        ], 
       function (_, Backbone, Item){
    
    var Items = Backbone.Collection.extend({        
        model: Item,
        
        comparator: 'noOrdre',
        
        initialize: function(){
            this.on('change:noOrdre', this.sort);
        }
    });
    
    return Items;    
});