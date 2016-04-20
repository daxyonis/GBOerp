//*****************************************************************************
// LAMSUBVIEW
// Subview for Laminage
//*****************************************************************************
define(['jquery',
        'underscore',
        'backbone',
        'handlebars',
        'text!soumflex/templates/itemLamTpl.html',
        'jsroutes'], 
       function($, _, Backbone, Handlebars, LamTemplate, jsRoutes){
    
    
    var lamView = Backbone.View.extend({
        
        tagName : 'div',
        
        className : 'tab-pane',
        
        lamTemplate : Handlebars.compile(LamTemplate), 
        
        // HANDLEBARS TEMPLATE for laminage type options -->                                    
        lamTypeTemplate : Handlebars.compile("{{#each lam}}<option value='{{id}}'>{{nom}}</option>{{/each}}"),
        
        // HANDLEBARS TEMPLATE for Laminage description -->                                    
        lamDescTemplate : Handlebars.compile("<span class='desc'>Caracteristique: {{caracteristique}}</span>"),
        
        initialize : function(options){
            this.bus = options.bus;
            this.listenTo(this.bus, 'ItemView:retirer', this.remove);
            this.listenTo(this.model, 'change:prod', this.updateModelLamList);
            this.listenTo(this.model, 'change:qte', this.updateLaminage);
            this.listenTo(this.model, 'change:haut', this.updateLaminage);
            this.listenTo(this.model, 'change:larg', this.updateLaminage);
            this.listenTo(this.model, 'change:perte', this.updateLaminage);
            this.listenTo(this.model, 'change:rv', this.updateLaminage);
        },   
        
        render : function(){
            this.$el.html(this.lamTemplate(this.model.toJSON()));
            return this;
        },
    
        events: {
            "change .lamSelectType:input" : "updateModelLaminage"            
        },
        
        updateListeLaminages : function(){
           var lamin = this.model.get('laminage');
           this.$('.lamSelectType').html(this.lamTypeTemplate({lam : lamin.get('listLam')}));
        },
        
        updateDescLaminage : function(){
           var lamin = this.model.get('laminage');
           this.$('#lamDesc'+this.model.get('id')).html(this.lamDescTemplate(lamin.currentLam()));
        },
        
        updateModelLamList : function(){
            // Set list of Laminage
            var self = this;
            var item = this.model;            
            var prod = item.leProduit();            
            if(prod.flex.id > 0){
                //*******************
                //--> ajax call to get list of laminages supported by this product category          
                var route = jsRoutes.controllers.Laminages.findForCategory(prod.flex.categorie);
                $.getJSON(route.url, 
                  function(jsonData){
                   self.model.get('laminage').setListLam(jsonData);        	
                })
                .fail(function( jqxhr, textStatus, error ) {
                      var err = textStatus + ", " + error;
                      console.log( "Request Failed: " + err );
                })
                .always(function(){
                    // Update select template
                    self.updateListeLaminages();
                    self.updateDescLaminage();
                    self.$("#lamSelectType"+item.get('id')).trigger("change");
                });
            }
            else{
                self.model.get('laminage').set({listLam: [], curLamId: 0}, {silent : true});
                self.updateListeLaminages();
                self.updateDescLaminage();
                self.$("#lamSelectType"+item.get('id')).trigger("change");
            }
       
        },
        
        updateModelLaminage : function(){            
            var chosenLam = this.$("#lamSelectType"+this.model.get('id') + " option:selected").val();
            this.model.get('laminage').set('curLamId', chosenLam);
            this.model.trigger('change:laminage');

            this.updateDescLaminage(this.model.get('laminage').currentLam());
            this.updateLaminage();
        },
        
        // Update la vue Laminage
        updateLaminage : function(){ 
            var item = this.model;
            var sup = item.superficieAvecPerteRV();
            var i = item.get('id');
            var marge = this.model.get('marge');
            this.$('#lamAire'+i).html(sup.toFixed(2)+" pi<span class='exp'>2</span>");
            this.$('.profit-margin').html(marge + "%");

            var lam = this.model.get('laminage');            
            var haut = item.rvFactor() * item.get('haut')/12.0;
            if(lam.get('curLamId') > 0){
                // Materiel
             this.$('#lamCout'+i).html(lam.cout().toFixed(2)+" $/pi<span class='exp'>2</span>")
                this.$('#lamMatSsTotal'+i).html(lam.matTotal(sup).toFixed(2) + " $");
                this.$('#lamMatTotal'+i).html(lam.matVendant(sup, marge).toFixed(2) + " $");
                // Main d'oeuvre
                var totalTemps = lam.tempsHr(haut);
                this.$('#lamTaux'+i).html(lam.get('MINIMAL_MO_COST').toFixed(2) + "$ + " + lam.taux().toFixed(2) + " $/h");
                this.$('#lamTempsHr'+i).html(totalTemps.toFixed(2) + " h");
                this.$('#lamMoSsTotal'+i).html(lam.moTotal(haut).toFixed(2) + " $");
                this.$('#lamMoTotal'+i).html(lam.moVendant(haut, marge).toFixed(2) + " $");
                totalTemps = totalTemps * 60.0;// en min
                var tmpsHres =Math.floor(totalTemps/60.0);
                var tmpsMin = Math.round(totalTemps - (tmpsHres * 60.0));            
                this.$('#lamTempsHrMin'+i).html(tmpsHres + "h " + tmpsMin + "min");
                // TOTAL
                this.$('#lamCoutantTotal'+i).html(lam.coutantTotal(sup,haut).toFixed(2) + " $");
                this.$('#lamTotal'+i).html(lam.vendantTotal(sup, haut, marge).toFixed(2) + " $");
            }
            else{
             // Materiel
                this.$('#lamCout'+i).html("");
                this.$('#lamMatSsTotal'+i).html("");            
                this.$('#lamMatTotal'+i).html("");
                // Main d'oeuvre
                this.$('#lamTaux'+i).html("");
                this.$('#lamTempsHr'+i).html("");
                this.$('#lamMoSsTotal'+i).html("");
                this.$('#lamMoTotal'+i).html("");
                this.$('#lamTempsHrMin'+i).html("");
                // TOTAL
                this.$('#lamCoutantTotal'+i).html("0.00$");
                this.$('#lamTotal'+i).html("0.00$");
            }

            // hidden inputs to send to server
            this.$('#lamTaux').val(lam.taux());
            this.$('#lamTempsHr').val(lam.tempsHr(haut));        
        },

        
    });
    
    return lamView;
});