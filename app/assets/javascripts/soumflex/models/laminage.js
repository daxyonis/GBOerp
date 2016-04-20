/******************************************************************/
/* MODELE LAMINAGE                                                */
/******************************************************************/
define(['underscore',
				    'backbone'], function(_, Backbone){

					var laminage = Backbone.Model.extend({
         defaults: {
             listLam : [],
             curLamId : 0,
             tauxMO : 0.0,
             MINIMAL_MO_COST : 20.0
         },
         
         tempsHr : function(hauteurPi){
         // Logique : temps = 1 min par pied lineaire
         // Temps(hr) = 1 min/pi x hauteur(pi) x 1hr/60min
         return(hauteurPi / 60.0);
        },
         
        taux : function(){
         return this.attributes.tauxMO;
        },

        setTaux : function(taux){
         this.attributes.tauxMO = taux;
        },

        setListLam : function(liste){
            this.attributes.listLam = liste;
            if(liste.length > 0){
                this.attributes.curLamId = liste[0].id;
            }
            else{
                this.attributes.curLamId = 0;
            }
        },

        currentLam : function(){
            var curId = this.attributes.curLamId;
            var newLaminage = {};
            $.each(this.attributes.listLam, function(i,obj){        
                if(obj.id == curId){
                    $.extend(newLaminage, obj);
                    return newLaminage;
                }        
            });
            return newLaminage;
        },

        cout : function(){
            var curLam = this.currentLam();
            if(curLam.hasOwnProperty("coutPiCar")){        
                    return curLam.coutPiCar;        
            }
            return 0.0;
        },

        // Total materiel
        matTotal : function(supAvecPerte){
         return(this.cout() * supAvecPerte);
        },

        moTotal : function(hauteur){
            if(this.attributes.curLamId > 0)
                return(this.attributes.MINIMAL_MO_COST + this.taux() * this.tempsHr(hauteur));
            else
                return 0.0;
        },

        matVendant : function(supAvecPerte, marge){
         return(this.matTotal(supAvecPerte) / (1.0 - 0.01 * marge));
        },

        moVendant : function(hauteur, marge){
            return(this.moTotal(hauteur) / (1.0 - 0.01 * marge));
        },

        vendantTotal : function(supAvecPerte, hauteur, marge){
            return(this.matVendant(supAvecPerte,marge) + this.moVendant(hauteur, marge));
        },

        coutantTotal : function(supAvecPerte, hauteur){
            return(this.matTotal(supAvecPerte) + this.moTotal(hauteur));
        }
     });
    
    return laminage;
});