//*****************************************************/
// SOUMFLEXVIEW
// Vue qui contrôle et réagit à l'ensemble de la
// soumission rapide flexible.
//*****************************************************/
define(['jquery',
        'underscore',
        'backbone',
        'handlebars',
        'bootstrap',
        'bootstrap_fh'
    ], function($, _, Backbone, Handlebars, Bootstrap, BootstrapFH){
    
    var SoumflexView = Backbone.View.extend({
        
        // Reflechir sur comment passer les evenements d'items a cette vue (sur changement dans un item on doit mettre a jour cette vue)
        initialize : function(options){
            this.bus = options.bus;            
            
        },
        
        events : {
            "click #soumettreSoum" : "validateAndPost"
        },

        
        // Render soum stats & prices
        render : function(){
            var soum = this.model;
            if(soum.get('items').length > 0 && 
               soum.get('items').any(function(m){
                                    return m.produitChoisi();
                                    })) {
                this.$('#soumCoutantTotal').html(soum.coutantTotal().toFixed(2) + "$");
                this.$('#soumVendantTotal').html(soum.vendantTotal().toFixed(2) + "$");
                this.$('#soumMargeEffective').html(soum.margeEffective().toFixed(0) + "%");
                this.$('.soumCommission').html(soum.commissionTotal().toFixed(2) + "$");        
                this.$('#soumPrixPiCar').html(soum.totalPiCar().toFixed(2) + "$");
                this.$('#soumPrixTotal').html(soum.grandTotal().toFixed(2) + "$");
                this.$('.soumVentesTotal').html(soum.ventesTotal().toFixed(2) + "$");
                
                // Values for the server
                $('#margeGlobale:input').val(soum.get('items').first().get("marge"));          
                $('#inputCommission').val(soum.get('SOUM_COMMISSION') * 100);
            }
            else {
                this.$('#soumCoutantTotal').html("-");
                this.$('#soumVendantTotal').html("-");
                this.$('#soumMargeEffective').html("-");
                this.$('.soumCommission').html("-");        
                this.$('#soumPrixPiCar').html("-");
                this.$('#soumPrixTotal').html("-");
                this.$('.soumVentesTotal').html("");
            }                        
            
            return this;
        },
        
       
        updateMontantVente : function(montant){
            this.$('#soumMontantVente').html(parseFloat(montant).toFixed(2) + "$");
        },       
        
        /**************************************/
        /* FORM FINAL VALIDATION AND SUBMIT   */
        /**************************************/
        validateAndPost : function(event){       	  

         event.preventDefault();
         var self = this;         
         this.bus.trigger('submit');    // for enteteView         
            
         setTimeout(function(){            
            var noError = self.model.get('entete').get('noError') &&
                          self.model.get('items').all(function(i){
                            return(i.isValid());
                          });
            if(noError){
              // submit
              $('form').submit();
             }  
         }, 0);       	

        },
        
        
    });
    
    return SoumflexView;
});

