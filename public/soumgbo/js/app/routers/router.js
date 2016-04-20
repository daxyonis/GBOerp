define(['underscore',
       'backbone',
        'backgrid',
        'backbone_paginator',
        'backgrid_paginator',
       'models/soumission',
       'views/soumview'], 
       function(_,
                Backbone,
                Backgrid,
                PageableCollection,
                BackGridPaginator,
                Soumission,
                SoumView){
    
    var SoumRouter = Backbone.Router.extend({
        
        routes: {
            'soumissions'     : 'newSoum',
            
            'soumissions/:id' : 'populateSoum',
            
            '*other'    : 'defaultRoute'
        },
        
        newSoum : function(){
            // Instancie le nouveau modele et le passe a la view
            // var soumView = new SoumView({model : new Soumission()});
            
            //****************************************
            // TEST backgrid
            var Client = Backbone.Model.extend({});
            var Clients = Backbone.Collection.extend({
               model : Client,
                url  : "/api/clients"
            });
            
            var clients = new Clients();
            var columns = [{
                name: "noClient", // The key of the model attribute
                label: "No", // The name to display in the header
                editable: false, // By default every cell in a column is editable, but *ID* shouldn't be
                // Defines a cell type
                cell: "string"                
              }, {
                name: "nom",
                label: "Nom",                
                cell: "string"
              }, {
                name: "ville",
                label: "Ville",
                cell: "string" // An integer cell is a number cell that displays humanized integers
              }, {
                name: "email",
                label: "Email",
                cell: "string" // A cell type for floating point value, defaults to have a precision 2 decimal numbers
              }];
            
            // Initialize a new Grid instance
            var grid = new Backgrid.Grid({
              columns: columns,
              collection: clients
            });
            
            // Render the grid and attach the root to your HTML document
            //$("#exemple-backgrid").append(grid.render().el);

            // Fetch 
            //clients.fetch({reset: true});
            
            var PageableClients = PageableCollection.extend({
                model : Client,
                url : "/api/clients",
                state : {
                    pageSize: 20
                },
                mode : "client"
            });
            
            var pageableClients = new PageableClients();
            // Set up a grid to use the pageable collection
            var pageableGrid = new Backgrid.Grid({
              columns,
              collection: pageableClients
            });

            // Render the grid
            var $example2 = $("#exemple-backgrid");
            $example2.append(pageableGrid.render().el)

            // Initialize the paginator
            var paginator = new BackGridPaginator({
              collection: pageableClients
            });

            // Render the paginator
            $example2.after(paginator.render().el);
            
            pageableClients.fetch({reset: true});
            //****************************************
        },
        
        populateSoum : function(id){
            console.log("You are trying to reach soum #" + id);
            // Instancie le modele, le passe a la view et fetch model data
            var soum = new Soumission({id : id});
            var soumView = new SoumView({model : soum});
            soum.fetch();            
        },
        
        defaultRoute: function(other){
            console.log('Invalid. You attempted to reach:' + other);
        }
        
    
    });
    
    return SoumRouter;
});