//*****************************************************************************
// FINISUBVIEW
// Subview for Finition
//*****************************************************************************
define(['jquery',
        'underscore',
        'backbone',
        'handlebars',
        'text!soumflex/templates/itemFiniTpl.html',
        'jsroutes'], 
       function($, _, Backbone, Handlebars, FiniTemplate, jsRoutes){
    
    
    var finiView = Backbone.View.extend({
        
        tagName : 'div',
        
        className : 'tab-pane',
        
        finiTemplate : Handlebars.compile(FiniTemplate), 
        
        // HANDLEBARS TEMPLATE for Finition type 
        finiTypeTemplate : Handlebars.compile("{{#each finition}}" + 
                                              "<option value={{id}}>{{type}} {{taille}}</option>"+
                                              "{{/each}}"),
        
        // HANDLEBARS TEMPLATE for Finition cotes
        finiCotesTemplate : Handlebars.compile("<option value='{{cotes1}}'>{{cotes1}}</option>" +
                                               "{{#if cotes2}}" +
                                               "<option value='{{cotes2}}'>{{cotes2}}</option>" +
                                               "{{/if}}"),
        
         // HANDLEBARS TEMPLATE for transfer tape options
        finiTTapeTemplate : Handlebars.compile("{{#each ttape}}" +
                                               "<option value='{{opt}}'>{{opt}}</option>"+
                                               "{{/each}}"),

        initialize : function(options){
            this.bus = options.bus;
            this.listenTo(this.bus, 'ItemView:retirer', this.remove);
            this.listenTo(this.model, 'change:prod', this.updateModelFinitionList);
            this.listenTo(this.model,'change:qte', this.updateFinition);
            this.listenTo(this.model,'change:haut', this.updateFinition);
            this.listenTo(this.model,'change:larg', this.updateFinition);
            this.listenTo(this.model,'change:perte', this.updateFinition);
        },            
    
        events : {
            "change .finiSelectType:input" : "updateModelFinition",
            "change .finiSelectCote:input" : "updateModelFinitionCotes",
            "change .finiSelectTtape:input" : "updateModelFinitionCotes"
        },
        
        render : function(){
            this.$el.html(this.finiTemplate(this.model.toJSON()));
            return this;
        },          
        
        updateFiniType : function(){
            var fini = this.model.get('finition');
            this.$('select.finiSelectType').html(this.finiTypeTemplate({finition : fini.getList()}));
        },
        
        updateFiniCotes : function(){
            var fini = this.model.get('finition');
            this.$('select.finiSelectCote').html(this.finiCotesTemplate(fini.curFinition()));
        },
        
        updateFiniTTape : function(){
            var fini = this.model.get('finition');
            this.$('select.finiSelectTtape').html(this.finiTTapeTemplate({ttape: fini.get('optTransferTape')}));
        },
        
       updateModelFinitionList : function(itemNo){
            var item = this.model;           
            var prod = item.leProduit();
            var self = this;
           if(prod.flex.id > 0){
                //*******************
                //--> ajax call to get the list of finitions
                var route = jsRoutes.controllers.Finitions.findForFlexibleCategory(prod.flex.categorie);
                $.getJSON(route.url, 
                    function(jsonData){
                      if(prod.flex.id == 0)
                        self.model.get('finition').setList(jsonData[0], item);
                      else
                        self.model.get('finition').setList(jsonData, item); 		
                })
                .fail(function( jqxhr, textStatus, error ) {
                    var err = textStatus + ", " + error;
                    console.log( "Request Failed: " + err );
                })
                .always(function(){
                    self.updateFiniType();
                    self.$("#finiSelectType"+ item.get('id')).trigger("change");
                });
                //<--
                //******************* 
           }
           else{
               self.model.get('finition').set({list : [], curFinitionId : 0, curCotes : " - "},
                                              {silent:true});
               self.updateFiniType();
               self.$("#finiSelectType"+ item.get('id')).trigger("change");
           }
        },
    
        updateModelFinition : function(){
            var item = this.model;
            var itemNo = item.get('id');
            var chosenFinitionId = this.$('#finiSelectType'+itemNo+' option:selected').val();        
            this.model.get('finition').setCurFinitionId(chosenFinitionId, item);                
            this.updateFiniCotes();

            this.$('#finiSelectCote'+itemNo).trigger("change");
        },

        updateModelFinitionCotes : function(){
            var item = this.model;
            var itemNo = item.get('id');
            var chosenCotes = this.$('#finiSelectCote'+itemNo+' option:selected').val();
            var ttape = this.$('#finiSelectOptTransferTape'+itemNo+' option:selected').val();
            this.model.get('finition').setCurCotes(chosenCotes);
            this.model.get('finition').set('transferTape', ttape);
            this.model.trigger('change:finition');
            this.updateFiniTTape();
            this.updateFinition();
        },
        
        // Update la vue Finition
        updateFinition : function(){
            var item = this.model;
            var fini = this.model.get('finition');
            var i = this.model.get('id');
            if(fini.get('list').length > 0){
                var curFini = fini.curFinition();
                var prix = curFini.prix;
                if(curFini.type != "Aucune"){
                    this.$("#finiPrix"+i).html(curFini.coutFixe + "$ + " + prix.toFixed(2) + "$/pi<span class='exp'>2</span>");
                }
                else{
                    this.$("#finiPrix"+i).html(prix);
                }
                var piTraites = item.superficieAvecPerte();
                this.$(".finiPiTraites"+i).html(piTraites.toFixed(2) + " pi2");
                this.$(".finiSsTotal"+i).html(fini.sousTotal(item).toFixed(2) + " $");
                this.$(".finiPrixTransferTape"+i).html(fini.prixTransTape(item).toFixed(2) + " $");            
                this.$(".finiTotal"+i).html(fini.vendant(item, item.get('marge')).toFixed(2) + " $");

             // hidden inputs to send to server
                this.$('#finiPiTraites').val(piTraites);
            }
            
            this.$('.profit-margin').html(this.model.get('marge') + "%");
        },

    });
    
    
    return finiView;
});