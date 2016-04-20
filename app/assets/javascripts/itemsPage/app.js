define(['jquery',
        'bootstrap', 
       'itemsPage/collections/items',    
        'itemsPage/views/itemsView',
        'jsroutes'
       ], 
       function($, 
                Bootstrap,
                Items, 
                ItemsView,
                jsRoutes){
    
    var initialize = function(serverItems){
        
        // Create items collection from bootstraped data
        var lesItems = new Items(serverItems);  
        var noSoum = parseInt(location.pathname.split("/")[2]);
        lesItems.url = jsRoutes.controllers.app.AppItems.readAllForSoum(noSoum).url;
        
        // Listen to changes on the collection
        lesItems.on('change add', function(e){
            if(this.any(function(m){ return !m.isValid(); })){
                $('#saveAlert').addClass("hidden");
            }
            else if(this.any(function(m){ return(m.isNew() || m.hasChanged()); })){
                $('#saveAlert').removeClass("hidden");   
            }                
        }, lesItems); 
                
        lesItems.on('sync', function(e){
            $('#saveAlert').addClass("hidden");
        });                                        
        
        /***************************************************/
        /* callbacks for the alert section to notify user  
        /* some items have been changed
        /***************************************************/
        $('button#saveItem').on('click', function(e){            
            $('#saveAlert').addClass("hidden");
            lesItems.each(function(m){
               if(m.isNew() || m.hasChanged()){
                   m.save({},{wait: true,
                           error : function(mod, resp, opt){
                               console.log("Model " + mod.get("nomItem") + " was not saved, error : ");
                               console.log(resp);
                           },
                           success: function(mod, resp, opt){
                               console.log("Success in saving model " + mod.get("nomItem"));                               
                           }}); 
               }
            });            
        });
        
        $('button#noSaveItem').on('click', function(e){
            $('#saveAlert').addClass("hidden");
            lesItems.fetch({reset:true});
        });
        /***************************************************/
        
        // Create main items view
        var itemsView = new ItemsView({el: '#soumItems', collection: lesItems});
    };
    
    return {
        initialize: initialize
    };
});