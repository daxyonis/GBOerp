//*****************************************************************************
// MOSUBVIEW
// Subview for MO=Main d'Oeuvre
//*****************************************************************************
define(['jquery',
        'underscore',
        'backbone',
        'handlebars',
        'text!soumflex/templates/itemMOTpl.html'], 
       function($, _, Backbone, Handlebars, MOTemplate){
    
    
    var moView = Backbone.View.extend({
        
        tagName : 'div',
        
        className : 'tab-pane',
        
        moTemplate : Handlebars.compile(MOTemplate),                
        
        initialize : function(options){
            this.bus = options.bus;
            this.listenTo(this.bus, 'ItemView:retirer', this.remove);
            this.listenTo(this.model, 'change:qte', this.onChangeQte);
            this.listenTo(this.model, 'change:prod', this.onChangeProd);
            this.listenTo(this.model.get('maindoeuvre'), 'change:qteSacs change:tempsDivers', this.triggerModelChange)
        },          
    
        events : {
            "change .moDiversTemps:input" : "updateModelMO"
        },
        
        render : function(){
            this.$el.html(this.moTemplate(this.model.toJSON()));
            return this;
        },  
        
        triggerModelChange : function(){
            this.model.trigger('change:maindoeuvre');
        },
        
        onChangeQte : function(){
            var qte = this.model.get('qte');
            this.model.get('maindoeuvre').updateTempsManip(qte);
            this.model.get('maindoeuvre').set('qteSacs', qte);
            this.updateMoResumeTable();
        },
        
        onChangeProd : function(){
            this.model.get('maindoeuvre').set('tempsDivers',0);
            this.updateMoResumeTable();
        },
        
        updateModelMO : function(){
            var itemNo = this.model.get('id');
            var diversTemps = this.$('#moDiversTemps'+itemNo+' option:selected').val();
            this.model.get('maindoeuvre').set('tempsDivers', parseInt(diversTemps));                 
            this.updateMoResumeTable();
        },
        
         // Update la vue Main d'Oeuvre
        updateMoResumeTable : function(){        
            var item = this.model;
            var i = item.get('id');
            var marge = item.get('marge');
            var mo = item.get('maindoeuvre');
            this.$("#moManipTaux"+i).html(mo.get('tauxManip').toFixed(2) + " $/h");
            this.$("#moManipTemps"+i).html(mo.get('tempsManip') + " min");
            this.$("#moDiversTaux"+i).html(mo.get('tauxDivers').toFixed(2) + " $/h");
            this.$("#moDiversTemps"+i).val(mo.get('tempsDivers'));
            this.$("#moSacPrix"+i).html(mo.get('prixSac').toFixed(2) + " $");
            this.$("#moSacQte"+i).html(mo.get('qteSacs') + " sacs");
            var tmpsTotal = mo.totalTemps();
            var tmpsHres = Math.floor(tmpsTotal/60.0);
            var tmpsMin = Math.round(tmpsTotal - tmpsHres*60.0);
            this.$("#moTempsTotal"+i).html(tmpsTotal + " min");
            this.$("#moHresTotales"+i).html(tmpsHres + "h " + tmpsMin + "min");
            this.$("#moManipSsTotal"+i).html(mo.ssTotManip().toFixed(2) + " $");
            this.$("#moManipTotal"+i).html(mo.totalManip(marge).toFixed(2) + " $");
            this.$("#moDiversSsTotal"+i).html(mo.ssTotDivers().toFixed(2) + " $");
            this.$("#moDiversTotal"+i).html(mo.totalDivers(marge).toFixed(2) + " $");                        
            this.$("#moSacTotal"+i).html(mo.prixTotalSacs().toFixed(2) + " $");
            this.$(".moPrixTotal"+i).html(mo.grandTotal(marge).toFixed(2) + " $");
            this.$('.profit-margin').html(marge+'%');

            // hidden inputs to send to server
            this.$('#moTaux:input').val(mo.get('tauxManip'));
            this.$('#moNbrHeure:input').val(tmpsTotal / 60.0);
            this.$('#moCoutSac:input').val(mo.get('prixSac'));
        },
        
    });
    
    
    return moView;
});