/******************************************************************/
/* MODELE INFOGRAPHIE                                             */
/******************************************************************/
define(['underscore',
      'backbone'], 
      function(_, Backbone){

    var infog = Backbone.Model.extend({
        
    defaults:{
        tauxDemarr : 30,
        tauxInfogXtra : 30,
        tempsDemarr : 30,      // min
        tempsInfogXtra : 0,    // min
        nbVisuels : 1,
        MAX_FREE_VISUALS : 2,
        XTRA_TIME_INCREMENT : 15,  // min
        MIN_FRAIS_DEMARR : 30 // min
    },
        
    initialize : function(){
        this.on('change',this.updateTempsXtra, this);
    },
        
    reset : function(opt){
        this.set(this.defaults, opt);
    },
        
   //*****************************************
   // fonctions du prototype
    setTaux : function(taux){
     this.attributes.tauxDemarr = taux;
     this.attributes.tauxInfogXtra = taux;
    },
        
    updateTempsXtra : function(){
        if(this.attributes.nbVisuels > this.attributes.MAX_FREE_VISUALS){
            this.attributes.tempsInfogXtra = (this.attributes.nbVisuels - this.attributes.MAX_FREE_VISUALS) * this.attributes.XTRA_TIME_INCREMENT;
        }
        else{
            this.attributes.tempsInfogXtra = 0;
        }
    },
    ssTotDemarr : function(){
        return(this.attributes.tauxDemarr * this.attributes.tempsDemarr/60.0);
    },
    ssTotInfogXtra : function(){
        return(this.attributes.tauxInfogXtra * this.attributes.tempsInfogXtra/60.0);
    },
    coutantTotal : function(){
        return(this.ssTotDemarr() + this.ssTotInfogXtra());
    },

    totalTemps : function(){
        return(parseInt(this.attributes.tempsDemarr) + parseInt(this.attributes.tempsInfogXtra));
    },
    totalDemarr : function(marge){
        return(this.ssTotDemarr() / (1.0 - 0.01 * marge));
    },
    totalInfogXtra : function(marge){
        return(this.ssTotInfogXtra() / (1.0 - 0.01 * marge));
    },
    prixTotal : function(marge){
        return(this.totalDemarr(marge) + this.totalInfogXtra(marge));
    }
    //*****************************************

    }); // end of Backbone.Model.extend()

    return infog;
});