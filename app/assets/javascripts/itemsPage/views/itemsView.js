/**
/* itemsView.js
/* The main items view that contains the grid
/* E.Maciejko
/* 09.03.2016
**/
define(['underscore',
        'backbone',
        'backgrid',
        'backgrid-text-cell',
       'itemsPage/views/rvSelectCell',
       'itemsPage/views/srcSelectCell',
       'itemsPage/views/famSelectCell',
       'itemsPage/views/controlCell',
       'itemsPage/models/item',
       'itemsPage/views/itemForm'], 
       function(_, Backbone, Backgrid, BackgridTxt,
                RVSelectCell, SrcSelectCell, FamSelectCell, ControlCell,
                Item, itemForm){
    
    var ItemsView = Backbone.View.extend({
        
        initialize: function(options){
            
            this.noSoumission = parseInt(location.pathname.split("/")[2]);
            
             var columns = [    
            {name: "", editable: false, label: "", cell: ControlCell},
            {name: "noOrdre", label:"Ordre", cell:"integer"},
           {name: "quantite", label: "Qté", cell:"integer"},
           {name: "nomItem", label: "Nom", cell:"text"},
           {name: "description", label: "Description", cell:"text"},
           {name: "notes", label: "Notes", cell:"text"},
           {name: "largeur", label: "Larg (po)", cell:"string"},
           {name: "hauteur", label: "Haut (po)", cell:"string"},
           {name: "rectoVerso", label: "RV", cell: RVSelectCell},
            {name: "sourceProd", label:"Source", cell: SrcSelectCell},
            {name: "famille", label: "Famille", cell: FamSelectCell},
            {name: "fraisVariables", label: "Frais var.", cell:"number"},
            {name: "fraisInstallation", label: "Frais inst.", cell: "number"},
            {name: "prixFab", label: "Prix Fab.", cell: "number"},
            {name: "prixVendu", label: "Prix Vendu", editable: false, cell: "number"},
            {name: "catalogue", label: "Catalogue", cell: "boolean"},
            {name: "codeCatalogue", label: "Code", cell: "string"}            
          ];                        
        
        var ButtonFooter = Backgrid.Footer.extend({
          render: function () {            
            var htmlStr = '<tr><td colspan = "' + columns.length + '">';     
              htmlStr += '<button class="btn btn-default" id="newItem">Nouvel item</button>';
              htmlStr += '<button class="btn btn-default" id="catalog">Catalogue</button>';
            htmlStr += '</td></tr>';
            this.el.innerHTML = htmlStr;
            return this;
          }
        });                

        
        // Initialize a new Grid instance
        this.grid = new Backgrid.Grid({
            columns: columns,
            collection: this.collection,
            className: "backgrid table table-bordered table-hover",
            footer: ButtonFooter
        });
        
        this.rowListen();           

        // Render the grid and attach the root to your HTML document
        this.$el.append(this.grid.render().el);
        
        //********************************
        // VALIDATION-related events        
        // Whenever an item was edited in a form, revalidate
        this.listenTo(this.collection, 'form:closed', this.revalidate);
        // Whenever an item is edited in the grid, revalidate
        this.listenTo(this.collection, 'backgrid:edited', this.revalidate);
        //********************************
            
        this.listenTo(this.collection, 'backgrid:refresh', this.rowListen);
            
        },   // end initialize
        
        events : {
            'click button#newItem' : 'editNewItem'            
        }, 
        
        // Each row view must listen to price changes and re-render itself so the computed prixVendu column is updated
        rowListen: function(){
            var grid = this.grid;            
            _.each(grid.body.rows, function(r){
               r.listenTo(r.model, 'change:fraisVariables change:fraisInstallation change:prixFab', r.render);
            });  
        },
        
        editNewItem : function(){
              // Get a void item from server
              var lesItems = this.collection;       
              var noSoum = this.noSoumission;
              var	route = jsRoutes.controllers.app.AppItems.voidItem();
              $.getJSON(route.url,
                function(jsonData) {
                    var newItem = new Item(jsonData);
                    var ordre = (lesItems.length > 0) ? (lesItems.last().get('noOrdre') + 1) : 0;
                    newItem.unset('noItem');    // remove it so we can add multiple
                    newItem.set('noSoumission',noSoum); // set its noSoumission
                    newItem.set('noOrdre', ordre);
                    lesItems.add(newItem);
                    var ItemView = itemForm(newItem).render();                    
                    // Remove the form view when modal closes
                    $('#itemModal').on('hidden.bs.modal', function(e){
                       ItemView.remove();
                       // Trigger form-closed event so the items grid can revalidate
                       newItem.trigger("form:closed");
                    }); 
                    $('.modal-body').append(ItemView.$el);
                    $('#itemModal').modal(); 
                    
                })
              .fail(function( jqxhr, textStatus, error ) {
                var err = textStatus + ", " + error;
                console.log( "Request Failed: " + err );
              });
                       
        },
        
        render : function(){
            return this;    
        },                    
        
        revalidate : function(){
            // remove past errors that could have been fixed elsewhere than in grid (such as in form)
            this.$('td').removeClass('error'); 
            if(this.collection.all(function(m) { return m.isValid(); })){
                this.$('.alert-danger').addClass('hidden');                   
            }
            else{
                var zeGrid = this.grid;
                var invalidModels = this.collection.filter(function(m) { return !m.isValid(); });
                var htmlStr = "";
                _.each(invalidModels, function(m){
                   htmlStr +=  "<p>Erreur(s) item '" + m.get('nomItem') + "' : " + m.validationError + "</p>";
                   _.each(m.errorModel.attributes, function(value, key){
                      var zeCol = zeGrid.columns.findWhere({ name: key });
                      m.trigger('backgrid:error', m, zeCol) ;   // this is to css-color error cell on grid
                   });
                });
                this.$('.alert-danger').html(htmlStr);
                this.$('.alert-danger').removeClass('hidden');
                return;   
            }
        }                
                
    });
    
    return ItemsView;
});