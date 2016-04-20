/******************************************************************/
/* MODELE IMPRESSION                                              */
/******************************************************************/
define(['underscore',
      'backbone',
       'soumflex/models/utility'], 
      function(_, Backbone, Utility){

    var impress = Backbone.Model.extend({
        
        defaults:{   
            impr : undefined,
            listeImpr : [],   
            IMPRESSION_TEMPS_MANIP : 5	// min
        },                
        
        //*****************************************
       // fonctions du prototype
        coutantTotal : function(superficie, qteItem){
            if(this.impChoisi())
                return(this.impTotal(superficie, qteItem) + this.impEncreTotal(superficie));
            else
                return 0.0;
        },
        prixTotal : function(superficie, qteItem, marge){
            if(this.impChoisi())   
                return(this.impTotalAvecMarge(superficie, qteItem, marge) + this.impEncreTotal(superficie));
            else
                return 0.0;
        },
        impTotal : function(superficie, qteItem){
            return(this.tempsImpr(superficie) * this.impTauxImpr() + this.tempsManip(qteItem) * this.impTauxManip());
        },
        impTotalAvecMarge : function(superficie, qteItem, marge){
         return( this.impTotal(superficie, qteItem) / (1.0 - 0.01 * marge) );
        },
        impTempsTotal : function(superficie, qteItem){
         return(this.tempsImpr(superficie) + this.tempsManip(qteItem));
        },
        tempsManip : function(qteItem){
         return(qteItem * this.attributes.IMPRESSION_TEMPS_MANIP/60.0);
        },
        tempsImpr : function(superficie){
         var tmps = 0.0;
            var vitesse = this.impVitesse();
            if(vitesse) {
                        tmps = superficie / vitesse;
                    }
            // Regle qui provient de vboSoumNumerique (et Access)    
            if(tmps < 0.17)
             tmps = 0.17;

            return tmps;
        },
        impEncreTotal : function(superficie){
            return( this.impEncre() * superficie);
        },
        impVitesse : function(){
            var imp = this.attributes.impr;
            if(imp.hasOwnProperty("vitesse"))
                return imp.vitesse;
            else
                return 0;
        },
        impEncre : function(){
            var imp = this.attributes.impr;
            if(imp.hasOwnProperty("pourcentEncre"))
                return ((0.01 * imp.pourcentEncre * imp.prixEncre) / (1.0 - 0.01 * imp.perteEncre) / (1.0 - 0.01 * imp.margeEncre));
            else
                return 0;
        },
        impTauxImpr : function(){
            var imp = this.attributes.impr;
            if(imp.hasOwnProperty("tauxImpr"))
                return imp.tauxImpr;
            else
                return 0;
        },
        impTauxManip : function(){
            var imp = this.attributes.impr;
            if(imp.hasOwnProperty("tauxManip"))
                return imp.tauxManip;
            else
                return 0;
        },
        Imprimante : function(code){    
            var impObj = {};
            $.each(this.attributes.listeImpr, function(i, imp){
                   if(imp.codeSelection == code) {
                       $.extend(impObj, imp);
                   }
                });    
            return impObj;
        },
        impChoisi : function(){
            return((typeof(this.attributes.impr)!='undefined') && 
              this.attributes.impr.hasOwnProperty("machine") &&
                   (this.attributes.impr.machine != "Aucune"));
        },
        setListImpr : function(impArr){
         if(impArr.length > 0){
             var imp = Utility.objCopy(impArr[0]);
             imp.machine = "Aucune";    
             this.attributes.listeImpr = [imp].concat(impArr);    
             this.attributes.impr = imp;
         }
         else{
          this.attributes.impr = undefined;		
          this.attributes.listeImpr = [];
         }
        },
        setImpr : function(impCode){
         this.attributes.impr = this.Imprimante(impCode);	
        }
        //******************************************
    });
    
    return impress;
});