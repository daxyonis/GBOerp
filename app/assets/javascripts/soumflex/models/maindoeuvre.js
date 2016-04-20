/******************************************************************/
/* MODELE MAIN D'OEUVRE                                           */
/******************************************************************/
define(['underscore',
        'backbone'], 
        function(_, Backbone){

    var mo = Backbone.Model.extend({
        
    defaults:{
        tempsManip : 30,   // min
        tauxManip : 30,    // $/hr
        tauxDivers : 30,
        tempsDivers : 0,   // min
        prixSac : 6,       // $
        qteSacs : 1,
        MIN_QTE : 5,
        MIN_TEMPS_MANIP : 30
    },
        
    setTaux : function(taux){
     this.attributes.tauxManip = taux;
     this.attributes.tauxDivers = taux;
    },
    updateTempsManip : function(numItems){
        this.attributes.tempsManip = this.attributes.MIN_TEMPS_MANIP;
        if(numItems > this.attributes.MIN_QTE){
            this.attributes.tempsManip += (numItems - this.attributes.MIN_QTE) * 6; //min
        }
    },
    ssTotManip : function(){
        return(this.attributes.tauxManip * this.attributes.tempsManip/60.0);
    },
    ssTotDivers : function(){
        return(this.attributes.tauxDivers * this.attributes.tempsDivers/60.0);
    },

    totalTemps : function(){
        return(this.attributes.tempsDivers + this.attributes.tempsManip);
    },

    prixTotalSacs : function(){
        return(this.attributes.prixSac * this.attributes.qteSacs);
    },

    coutantTotal : function(){
        return(this.ssTotManip() + this.ssTotDivers() + this.prixTotalSacs());
    },

    totalManip : function(marge){
        return(this.ssTotManip() / (1.0 - 0.01 * marge));
    },

    totalDivers : function(marge){
        return(this.ssTotDivers() / (1.0 - 0.01 * marge));
    },

    grandTotal : function(marge){
        return(this.totalManip(marge) + this.totalDivers(marge) + this.prixTotalSacs());
    },
        
        
    });
    
    return mo;
});