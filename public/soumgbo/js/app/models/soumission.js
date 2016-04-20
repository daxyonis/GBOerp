define(['underscore', 
        'backbone'], 
       function(_, 
                Backbone) {
    
    var Soumission = Backbone.Model.extend({
    
        initialize: function(){
//            this.items = new Items;
//            this.items.parent = this;
            
            // Don't need this if parent soum already loads children items
            //this.items.url = '/api/soumissions/' + this.id + '/items'
        },
                
        //urlRoot: '/api/soumissions'        
        urlRoot: function(){
            if (this.isNew()){
              return "/api/soumissions";
            } else {
              return "http://localhost:9000/api/soumissions/";// + this.id;
            }
        }
            
    });
    
    return Soumission;
});