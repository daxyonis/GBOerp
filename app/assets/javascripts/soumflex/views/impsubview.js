//*****************************************************************************
// IMPSUBVIEW
// Subview for Impression
//*****************************************************************************
define(['jquery',
        'underscore',
        'backbone',
        'handlebars',
        'bootstrap',
        'text!soumflex/templates/itemImpTpl.html',
        'text!soumflex/templates/impSelTpl.html',        
        'jsroutes'
        ], function($, _, Backbone, Handlebars, Bootstrap, 
                     ImpTemplate, ImpSelectTemplate,
                    jsRoutes){    
    
    var impSubView = Backbone.View.extend({
        
        tagName : 'div',
        
        className : 'tab-pane imp',
        
        impTemplate : Handlebars.compile(ImpTemplate),
        
        impSelectTemplate : Handlebars.compile(ImpSelectTemplate),
        
        initialize : function(options){
            this.bus = options.bus;
            this.listenTo(this.bus, 'ItemView:retirer', this.remove);
            this.listenTo(this.model, 'change', this.onItemChange);
        },
        
        events : {
            "change .impSelect" : "updateModelImpression"
        },
        
        render : function(){
            this.$el.html(this.impTemplate(this.model.toJSON()));
            return this;
        },
        
        renderImpSelection : function(){
            var listeImprimantes = this.model.get('impression').get('listeImpr');
            this.$('select.impSelect').html(this.impSelectTemplate({impr : listeImprimantes}));
            $('#impSelect'+this.model.get('id')).trigger("change");
        },
                
        
        // When item material product changes, update list of printers
        onItemChange : function(){
            var self = this;
            var item = this.model;
            if(item.hasChanged('prod')){
                // Update the list of printers
                var prod = item.leProduit();                
                var self = this;
        
                //--> ajax call to get list of printers that support the given product       
                var route = jsRoutes.controllers.Imprimantes.findForFlexible(prod.flex.id);
                $.getJSON(route.url,
                          function(listImprimantes) {
                            self.model.get('impression').setListImpr(listImprimantes);        			
                })
               .fail(function( jqxhr, textStatus, error ) {
                    self.model.get('impression').setListImpr([]);
                    var err = textStatus + ", " + error;
                    console.log( "Request Failed: " + err );
                })
               .always(function(){
                    self.renderImpSelection();                    
               });
                //<--
            }
            else{
                this.updateImpResumeTable();
            }
        },     
        
        updateModelImpression : function(){
            var item = this.model;
            var itemNo = item.get('id');
            var imprimanteCode = this.$('#impSelect'+itemNo+' option:selected').val();              
            this.model.get('impression').setImpr(imprimanteCode);
            this.model.trigger('change:impression');            
            this.updateImpResumeTable();            
        },
        
        updateImpResumeTable : function(){
            var item = this.model;
            var imp = item.get('impression');
            var i = item.get('id');
            if(imp.impChoisi()){
                var aire = item.superficieRV();
                var qte =  item.get('qte') * item.rvFactor();
                var encreTotal = imp.impEncreTotal(aire);
                var tmps = imp.impTempsTotal(aire, qte);
                var tmpsManip = imp.tempsManip(qte);
                var tmpsImpr = imp.tempsImpr(aire);
                var impTotal = imp.impTotalAvecMarge(aire, qte, item.get('marge'));            
                var vendant = imp.prixTotal(aire, qte, item.get('marge'));

                this.$(".impEncreTotal"+i).html(encreTotal.toFixed(2) + "$");
                this.$('#impTempsManip'+i).html(tmpsManip.toFixed(2)+"h");
                this.$('#impTempsImpr'+i).html(tmpsImpr.toFixed(2)+"h");            
                this.$("#impTemps"+i).html(tmps.toFixed(2)+"h");
                this.$("#impCout"+i).html(impTotal.toFixed(2) + '$');            
                this.$(".impPrixTotal"+i).html(vendant.toFixed(2) + "$");
                var tmpsHres = Math.floor(tmps);
                var tmpsMin = Math.round((tmps - tmpsHres)*60.0);
                this.$("#impTempsHrMin"+i).html(tmpsHres + "h " + tmpsMin + "min"); 
                
                // hidden input to send to server
                this.$('.impTempsManipHidden:input').val(Math.round(imp.get('IMPRESSION_TEMPS_MANIP')));
            }
            else{
                this.$(".impEncreTotal"+i).html("");
                this.$('#impTempsManip'+i).html("");
                this.$('#impTempsImpr'+i).html("");
                this.$("#impTemps"+i).html("");
                this.$("#impCout"+i).html("");            
                this.$(".impPrixTotal"+i).html("");
                this.$("#impTempsHrMin"+i).html("00h 00min");   
                this.$('.impTempsManipHidden:input').val(0);
            }
                        
        }
        
        
    });
    
    return impSubView;
});