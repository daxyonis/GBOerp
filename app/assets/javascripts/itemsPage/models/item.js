define(['jquery',
        'underscore', 
        'backbone',
        'itemsPage/models/base'], 
       function ($,_, Backbone, BaseModel){
    
    var Item = BaseModel.extend({
        idAttribute: 'noItem',                
        
        defaults: {
            prixVendu : function(){
                return (this.get("fraisVariables") + this.get("fraisInstallation") + this.get("prixFab"));
            }
        },
        
        initialize : function(){
          // We use an error model within the item model to keep the errors for each attribute
          // (this is the approach taken by BackForm, see https://amiliaapp.github.io/backform/#Validation)
          this.errorModel = new Backbone.Model();
        },                
        
        // Inspired by Backform validation : https://amiliaapp.github.io/backform/#Validation
        validate: function(attrs){
            this.errorModel.clear();
            
            if(attrs.noSoumission <= 0){
                this.errorModel.set({noSoumission: "le no de soumission est invalide "});
            }
            if(!$.isNumeric(attrs.quantite) || (attrs.quantite < 0)){    
                this.errorModel.set({quantite: "quantité doit être supérieure ou égale à 0 "});
            }
            if(attrs.nomItem.length < 1){                
                this.errorModel.set({nomItem: "nom de l'item ne peut être vide "});
            }
            if(attrs.description.length < 1){ 
                this.errorModel.set({description: "description de l'item ne peut être vide "});          
            }    
            if(!$.isNumeric(attrs.largeur.replace(/"|,/g, ""))){
                this.errorModel.set({largeur: "largeur n'est pas numérique "});
            }
            if(!$.isNumeric(attrs.hauteur.replace(/"|,/g, ""))){
                this.errorModel.set({hauteur: "hauteur n'est pas numérique "});
            }
            if(attrs.fraisVariables < 0.0){
                this.errorModel.set({fraisVariables: "frais variables doivent être >= 0.00 "});
            }
            if(attrs.fraisInstallation < 0.0){
                this.errorModel.set({fraisInstallation: "frais d'installation doivent être >= 0.00 "});              
            }
            if(attrs.prixFab < 0.0){
                this.errorModel.set({prixFab: "prix fab. doit être >= 0.00 "});            
            }          
            
            // Returns an array of all found errors.
            if(!this.errorModel.isEmpty())
                return (_.compact(this.errorModel.toJSON()));
        }        
                
    });    
    return Item;    
});