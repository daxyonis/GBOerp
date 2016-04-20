//*****************************************************************************
// INFOSUBVIEW
// Subview for Infographie
//*****************************************************************************
define(['jquery',
        'underscore',
        'backbone',
        'handlebars',
        'text!soumflex/templates/itemInfoTpl.html',
        ], function($, _, Backbone, Handlebars, InfoTemplate){    
    
    var infoSubView = Backbone.View.extend({
        
        tagName : 'div',
        
        className : 'tab-pane',
        
        infoTemplate : Handlebars.compile(InfoTemplate),                
        
        initialize : function(options){
            this.bus = options.bus;
            this.listenTo(this.bus, 'ItemView:retirer', this.remove);
            this.listenTo(this.model, 'change:prod', this.updateNbVisuels);
        },
        
        events : {
            "change .infoNbVisuels:input" : "updateModelInfographie"
        },
        
        render : function(){
            this.$el.html(this.infoTemplate(this.model.toJSON()));
            return this;
        },            
        
        updateNbVisuels : function(){
            this.model.get('infographie').set('nbVisuels', 1); 
            this.model.trigger('change:infographie');
            this.updateInfogResumeTable();
        },
        
        updateModelInfographie : function(){
            var item = this.model;
            var itemNo = item.get('id');
            var nbVisuels = this.$('#infoNbVisuels'+itemNo+' option:selected').val();
            this.model.get('infographie').set('nbVisuels', parseInt(nbVisuels));
            this.model.trigger('change:infographie');

            this.updateInfogResumeTable();            
        },
                
        updateInfogResumeTable : function(){
              var i = this.model.get('id');
              var marge = this.model.get('marge');
              var infog = this.model.get('infographie');
              this.$('.infogTaux').html(infog.get('tauxDemarr').toFixed(2)+'$/h');
              this.$('#infoNbVisuels'+i).val(infog.get('nbVisuels'));
              this.$('#infoTmpsXtra'+i).html(infog.get('tempsInfogXtra')+' min');
              this.$('#infoTempsDemarr'+i).html(infog.get('tempsDemarr')+' min');
              var ssTotDemarr = infog.ssTotDemarr();          
              var ssTotInfogXtra = infog.ssTotInfogXtra();
              var totalTemps = infog.totalTemps();
              this.$('#infoSsTotFrais'+i).html(ssTotDemarr.toFixed(2)+'$');    
              this.$('#infoSsTotXtra'+i).html(ssTotInfogXtra.toFixed(2)+'$');
              this.$('#infoSsTotTmps'+i).html(totalTemps+' min');
              this.$('#infoTotFrais'+i).html(infog.totalDemarr(marge).toFixed(2)+'$');
              this.$('#infoTotXtra'+i).html(infog.totalInfogXtra(marge).toFixed(2)+'$');
              this.$('.infoTotal'+i).html(infog.prixTotal(marge).toFixed(2)+'$');
              var tmpsHres =Math.floor(totalTemps/60.0);
              var tmpsMin = Math.round(totalTemps - (tmpsHres * 60.0));
              this.$('#infoTmpsTotal'+i).html(tmpsHres+"h "+tmpsMin+"min"); 
              this.$('.profit-margin').html(marge+'%');

              // hidden inputs to send to server
              this.$('#infoTaux'+i+':input').val(infog.get('tauxDemarr'));
              this.$('#infoNbrHeure'+i+':input').val(totalTemps / 60.0);
              

        },
    });
    
    return infoSubView;
});