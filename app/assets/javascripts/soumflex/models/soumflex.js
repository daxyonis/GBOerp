/**
 * Modele pour la soumission rapide flexible
 */
define(['underscore',
		      'backbone'], 
		function(_, Backbone){

			var soumFlex = Backbone.Model.extend({
       defaults :{           
           // items --> the items Collection
           // entete --> the entete Model
           "profitMargin_default" : 40,
           "SOUM_COMMISSION" : 0.02, // 2%
           "SOUM_PRIX_MIN" : 100.0           
       },              
       
       //**********************************************
       // TOUTES LES FONCTIONS DU PROTOTYPE       
        Item : function(index){
            if((index >= 0) && (index < this.attributes.items.length)){
                return this.get('items').get(index);
            }
            else{
                return null;
            }
        },
       
        getFilledPercent : function(){
            return 0.0;
        },
       
       ventesTotal : function(){
           var ventesTotal = 0.0;
            var itemsColl = this.get('items');
            itemsColl.each(function(item, i){            
               ventesTotal += item.get('montantVente');
            });
            return ventesTotal;
       },
       
        coutantTotal : function(){
            var coutTotal = 0.0;
            var itemsColl = this.get('items');
            itemsColl.each(function(item, i){            
                coutTotal += item.coutantTotal();
            });
            return coutTotal;
        },
       
        vendantTotal : function(){
            var prixTotal = 0.0;
            var itemsColl = this.get('items');
            itemsColl.each(function(item,i){ 
                prixTotal += item.vendantTotal();
            });
            return prixTotal;
        },
       
        totalPiCar : function(){
            var piCar = 0;
            var itemsColl = this.get('items');
            itemsColl.each(function(item, i){ 
               piCar += item.superficie();
            });
            if(piCar)
                return(this.grandTotal() / piCar);
            else
                return 0.0;
        },
       
        margeEffective : function(){
            var vendant = this.vendantTotal();
            var comm = this.commissionTotal();
            var cout = this.coutantTotal();
            if(vendant > 0.0)
                return(100.0 * (vendant + comm - cout) / (vendant + comm) );
            else
                return 0;
        },
       
        commissionTotal : function(){
            return(this.attributes.SOUM_COMMISSION * this.vendantTotal());
        },
       
       
        grandTotal : function(){
            var prixTotal = 0.0;
            var itemsColl = this.get('items');
            itemsColl.each(function(item,i){ 
                prixTotal += item.grandTotalItem();
            });
            return prixTotal;
        }
       //**********************************************
       
   });
	
			return soumFlex;
});
