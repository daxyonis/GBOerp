/**
 * Modele pour chaque item de la soumission rapide flexible
 */
define(['underscore',
		'backbone',
        'soumflex/models/impression',
        'soumflex/models/infographie',
        'soumflex/models/maindoeuvre',
        'soumflex/models/finition',
        'soumflex/models/laminage',
        'soumflex/models/utility'        
       ], 
function(_, Backbone, Impression, Infographie, MainDoeuvre, Finition, Laminage, Utility){

			var item = Backbone.Model.extend({
       
       defaults: { 
        nom: "Nouvel item",
        qte : 1,
        larg : 0,      // po
        haut : 0,      // po
        perte : 10,    // %
        rv : "",
        type : "Flexible",
        cat : "",
        prod : 0,
        collProd : undefined,
        referenceInfographie : undefined,
        referenceMaindOeuvre : undefined,
        referenceLaminage : undefined,
        marge : 40.0,
        montantVente : 0.0,
        SOUM_COMMISSION : 0.02, // 2%
        SOUM_PRIX_MIN : 100.0
       },     
       
       numericPattern : new RegExp("^[0-9]{1,}[.]?[0-9]{0,}$"),
       
       initialize: function(){
           // We initialize the liste of possible products for this item                      
            this.set('cat','TOUS'); 
            var myDefaults = {
                impression : new Impression(),
                infographie : new Infographie(),
                maindoeuvre : new MainDoeuvre(),
                finition : new Finition(),
                laminage : new Laminage()
            };
           this.set(myDefaults);           
        },
       
       getCategories : function(){
           var self = this;
               if(self.attributes.collProd){
                   var catObj = [{categorie : 'TOUS'}];
                   var catArr = _.keys(_.groupBy(self.attributes.collProd.pluck('flex'),'categorie'));
                   _.each(catArr, function(elem){
                       catObj.push({categorie : elem});
                   });
                   return catObj;
               }
               else {
                   return {};
               }           
        },
       
       //*****************************************
       // fonctions du prototype       
       setTaux : function(){
           this.attributes.infographie.setTaux(this.attributes.referenceInfographie.tauxGlobal);
           this.attributes.maindoeuvre.setTaux(this.attributes.referenceMaindOeuvre.tauxGlobal);
           this.attributes.laminage.setTaux(this.attributes.referenceLaminage.tauxGlobal);   
       },
       
        // Check if there are errors
        hasError : function(){	

         // Verify that the product dimensions
         // do not exceed max allowed dimensions for chosen printer
         var errMsg = null;	
         if(this.attributes.prod > 0){
          var prod = this.leProduit().flex;
          var isVinyle = (prod.categorie.toLowerCase().indexOf("vinyle") >= 0);

          if(this.attributes.impression.impChoisi()){
           var impr = this.attributes.impression.get('impr');			
           if(impr.hauteurMax_po && (this.attributes.haut > impr.hauteurMax_po)){
            errMsg = "La hauteur de l'item ne doit pas excéder la hauteur max permise pour l'imprimante " + 
              impr.machine + " : Hauteur Max(po) = " + impr.hauteurMax_po;
           }
           if(impr.largeurMax_po && (this.attributes.larg > impr.largeurMax_po)){
            errMsg = "La largeur de l'item ne doit pas excéder la largeur max permise pour l'imprimante " +
              impr.machine + " : Largeur Max(po) = " + impr.largeurMax_po;
           }
           if(errMsg){
            if(isVinyle) {			
             errMsg += ". Ceci est permis pour des tuiles de vinyle.";
             return("<p class='bg-warning text-warning'>ATTENTION : "+errMsg+"</p>")
            }
            else{
             return("<p class='bg-danger'><span class='error'>ERREUR : </span>"+errMsg+"</p>")
            }
           }
          }

          if(prod.formatMax && (this.attributes.haut > prod.formatMax)){
           errMsg = "Le produit choisi a un format maximal de " + 
               prod.formatMax + "po, alors que la hauteur de l'item est de " + this.attributes.haut + "po.";
           if(errMsg)
           {
            if(isVinyle){
             errMsg += " Ceci est permis pour des tuiles de vinyle.";
             return("<p class='bg-warning text-warning'>ATTENTION : "+ errMsg +"</p>")
            }			
            else{
             return("<p class='bg-danger'><span class='error'>ERREUR : </span>"+ errMsg +"</p>")
            }
           }
          }
         }

         return errMsg;
        },
       
       getListeProd : function(){ 
           var self = this;
           var prodArr = self.attributes.collProd.toJSON();
           var emptyObj = Utility.objCopy(prodArr[0]);
           emptyObj.flex = {"id" : 0};
           if(self.attributes.cat == "TOUS"){
               return [emptyObj].concat(prodArr);
           }
           else{
            var newProdArr = [];
            _.map(prodArr, function(obj){
                if(obj.flex.categorie === self.attributes.cat){
                    newProdArr.push(obj);
                }
            });
            return [emptyObj].concat(newProdArr);
           }
       },
       
        rvFactor : function(){
         var factor = (this.attributes.rv === "R/V") ? 2 : 1;
         return factor;
         },
       
        superficie : function(){
            var attr = this.attributes;
            return((attr.qte * attr.larg * attr.haut) /  144.0);
        },
       
        superficieAvecPerte : function(){
            return (this.superficie() / (1.0 - 0.01 * this.attributes.perte));
        },
       
        superficieRV : function(){
        var attr = this.attributes;
         var factor = (attr.rv === "R/V") ? 2 : 1;
         return((factor * attr.qte * attr.larg * attr.haut) /  144.0);	
        },
       
        superficieAvecPerteRV : function(){
         return (this.superficieRV() / (1.0 - 0.01 * this.attributes.perte));
        },
       
        produitChoisi : function(){
            var self = this;
            return((self.attributes.collProd.length > 0) && (self.attributes.prod > 0));
        },
       
        leProduit : function(){     
           var listeProd = this.getListeProd();
           var prodObj = {};
           var prodId = this.attributes.prod;
           $.each(listeProd, function(i, obj){
                if(obj.flex.id == prodId) {
                   $.extend(prodObj, obj);
                }
            });    
            return prodObj;
        },
       
        // coutant en $/pi2
        matCoutant : function(){
            var curProd = this.leProduit();
            return(curProd.maxCost);
        },
       
        // vendant en $/pi2
        matVendant : function(){
            return(this.matCoutant() / (1.0 - 0.01 * this.attributes.marge));	
        },
       
        matPrixTotal : function(){
            return(this.matVendant() * this.superficieAvecPerte());
        },
                                    
        matCoutantTotal : function(){
            return(this.matCoutant() * this.superficieAvecPerte());
        },       
       
       coutantTotal : function() {
           var coutTotal = 0.0;
            if(this.produitChoisi()){                    
                    var supAvecPerteRV = this.superficieAvecPerteRV();                    
                    var hauteur = this.rvFactor() * this.get('haut')/12.0; // en pieds                    
                    var aire = this.superficieRV();
                    var qte =  this.get('qte') * this.rvFactor();
                    //***************************
                    // DEBUG
//                    console.log("item.coutantTotal(): " +this.matCoutantTotal());
//                    console.log("item.impression.coutantTotal(sup): " + this.get('impression').coutantTotal(aire, qte));
//                    console.log("item.infographie.coutantTotal(): "+this.get('infographie').coutantTotal());
//                    console.log("item.maindoeuvre.coutantTotal(): "+this.get('maindoeuvre').coutantTotal());
//                    console.log("item.finition.total(item): "+this.get('finition').total(this));
//                    console.log("item.laminage.coutantTotal(sup): "+ this.get('laminage').coutantTotal(supAvecPerteRV, hauteur));
                    //***************************

                    coutTotal += this.matCoutantTotal();
                    coutTotal += this.get('impression').coutantTotal(aire, qte);
                    coutTotal += this.get('infographie').coutantTotal();
                    coutTotal += this.get('maindoeuvre').coutantTotal();
                    coutTotal += this.get('finition').total(this);
                    coutTotal += this.get('laminage').coutantTotal(supAvecPerteRV, hauteur);                    
                }
           return coutTotal;
        },
       
       vendantTotal : function(){
           var prixTotal = 0.0;
           if(this.produitChoisi()){
                    var marge = this.attributes.marge;
                    var supAvecPerteRV = this.superficieAvecPerteRV();
                    var hauteur = this.rvFactor() * this.get('haut')/12.0; // en pieds
                    var aire = this.superficieRV();
                    var qte =  this.get('qte') * this.rvFactor();
                    //***************************
                    // DEBUG
//                    console.log("item.prixTotal(marge): "+this.matPrixTotal());
//                    console.log("item.impression.prixTotal(sup,qte,marge): "+this.get('impression').prixTotal(aire,qte,marge));
//                    console.log("item.infographie.prixTotal(marge): "+this.get('infographie').prixTotal(marge));
//                    console.log("item.maindoeuvre.grandTotal(marge): "+this.get('maindoeuvre').grandTotal(marge));
//                    console.log("item.finition.total(item): "+this.get('finition').vendant(this,marge));
//                    console.log("item.laminage.vendantTotal(sup, hauteur,marge): "+this.get('laminage').vendantTotal(supAvecPerteRV, hauteur, marge));
                    //***************************

                    prixTotal += this.matPrixTotal();
                    prixTotal += this.get('impression').prixTotal(aire, qte, marge);
                    prixTotal += this.get('infographie').prixTotal(marge);
                    prixTotal += this.get('maindoeuvre').grandTotal(marge);
                    prixTotal += this.get('finition').vendant(this, marge);
                    prixTotal += this.get('laminage').vendantTotal(supAvecPerteRV, hauteur, marge);                    
                }
           return prixTotal;
       },              
       
       commissionTotalItem : function(){
           return(this.get('SOUM_COMMISSION') * this.vendantTotal());
       },
       
       ajustementItem : function(){
           return( Math.max(0.0, 100.0 - (1 + this.get('SOUM_COMMISSION')) * this.vendantTotal()) );
       },
       
       grandTotalItem : function(){
           return( (1 + this.get('SOUM_COMMISSION')) * this.vendantTotal() + this.ajustementItem() );
       },
       
       prixPiCar : function(){
           if(this.produitChoisi()){
            return( this.grandTotalItem() / this.superficie() );           
           }
           else{
               return 0.0;
           }
       },
       
       validate : function(attrs, options){
           if(!attrs.montantVente){
               return("SVP entrer un montant des ventes");
           }
           var totalEstime = this.grandTotalItem();
            var montantEntre = attrs.montantVente;
            var montantTropPetit = (totalEstime && (montantEntre < totalEstime));
            var montantNonNumerique = !this.numericPattern.test(montantEntre);
            if(montantTropPetit || montantNonNumerique){           
               return("Entrer un nombre supérieur au Total Estimé");
           }
       }
                                    
   });
	
			return item;
});
