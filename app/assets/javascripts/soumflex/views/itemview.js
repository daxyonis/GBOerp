//*****************************************************************************
// ITEMVIEW
// View for one item, creates all subviews and appends it to its el
//*****************************************************************************
define(['jquery',
        'underscore',
        'backbone',
        'handlebars',
        'bootstrap',
        'text!soumflex/templates/itemTpl.html',
        'soumflex/views/matsubview',
        'soumflex/views/impsubview',
        'soumflex/views/infosubview',
        'soumflex/views/mosubview',
        'soumflex/views/finisubview',
        'soumflex/views/lamsubview'
        ], function($, _, Backbone, Handlebars, Bootstrap, 
                     ItemTemplate, MatSubview, ImpSubView, 
                     InfoSubView, MoSubView, FiniSubView,
                     LamSubView){        
        
    var ItemView = Backbone.View.extend({
        
    itemTemplate : Handlebars.compile(ItemTemplate),                                  
    
    initialize : function(options){
            // bus d'evenements a partager entre cette view et ses subviews
            this.bus = options.bus;
            this.listenTo(this.model, 'change', this.updateError);
            this.listenTo(this.model,'change ' +
                                     'change:impression '+
                                     'change:infographie '+
                                     'change:maindoeuvre '+
                                     'change:finition '+
                                     'change:laminage', this.updatePrix);
            this.listenTo(this.model, 'invalid', this.onInvalidModel);                         
        },     
        
    events:{
        "click .btnRetirerItem"  :   "onRetirerItem",
        "change .itemQte"        : "updateQte",
        "change .itemHaut"       : "updateHaut",
        "change .itemLarg"       : "updateLarg",
        "change select.itemRV"   : "updateRV",
        "change select.itemPerte" : "updatePerte",
        "change input.montantVente" : "updateVente"
    },

    render : function(){
        
        var self = this;
        self.$el.html(this.itemTemplate(this.model.toJSON()));                       
        
        // WATCHOUT : when rendering with a template,
        // all javascript event bindings must be done after
        // the template has rendered.
        setTimeout(function(){             
            
            // Create subviews
            var id = self.model.get('id');
            var matSubview = new MatSubview({model : self.model, id : 'mat'+id, bus : self.bus});
            self.$('.tab-content').append(matSubview.render().el);
            
            var impSubView = new ImpSubView({model : self.model, id : 'imp'+id, bus : self.bus});
            self.$('.tab-content').append(impSubView.render().el);
            
            var infoSubView = new InfoSubView({model : self.model, id : 'inf'+id, bus : self.bus});
            self.$('.tab-content').append(infoSubView.render().el);
            
            var moSubView = new MoSubView({model : self.model, id : 'mo'+id, bus : self.bus});
            self.$('.tab-content').append(moSubView.render().el);
            
            var finiSubView = new FiniSubView({model : self.model, id : 'fini'+id, bus : self.bus});
            self.$('.tab-content').append(finiSubView.render().el);
            
            var lamSubView = new LamSubView({model : self.model, id : 'lam'+id, bus : self.bus});
            self.$('.tab-content').append(lamSubView.render().el);
                        
            // VALIDATION DES QUANTITE, LARGEUR, HAUTEUR                    
            self.$('.item:text').popover(
            {content:'<span class=text-danger>Invalide</span>', 
             placement: 'right', trigger: 'manual', html: true});
            
            self.delegateEvents();
            }, 0);    
        
        
                
        return this;
    },            

   onRetirerItem :  function(e){           
       this.trigger("ItemView:retirer", this.model);    // for parent view
       this.bus.trigger("ItemView:retirer", this.model);// for subviews
       this.remove();
    },     
        
    onInvalidModel : function(){       
       var self = this;
       var $montantVente = this.$('#montantVente'+this.model.get('id'));        
       $montantVente.popover(
           {template : '<div class="popover" role="tooltip"><div class="arrow"></div><div class="popover-content requiredError"></div></div>',
            content: function() {
                return self.model.validationError; 
            },
            placement: 'top', 
            trigger: 'manual',
            html:true});
        $montantVente.popover('show');
    },                
  

    updateProgress : function(filledPercent){
        $('div.progress-bar').html(filledPercent + "%");
    },
    
    updateError : function(){
    	var errMsg = this.model.hasError();
    	
    	if(errMsg){
        this.$('div#itemErrorMsg').html(errMsg);
    	}
    	else{
        this.$('div#itemErrorMsg').empty();
    	}
    },
    
    // Update superficie
    updateSuperficie : function (){
        var item = this.model;
        var i = this.model.get('id');
        var sup = item.superficie();
        this.$('#itemArea'+ i).val(sup.toFixed(2));        
        var supPerte = item.superficieAvecPerte();
        this.$('#itemAreaPerte' + i).val(supPerte.toFixed(2));
    },
    
    updatePrix : function(){
        var item = this.model;
        var i = this.model.get('id');
        this.$('#itemCoutantTotal').html(item.coutantTotal().toFixed(2)+'$');
        this.$('#itemVendantTotal').html(item.vendantTotal().toFixed(2)+'$');
        this.$('.itemCommission').html(item.commissionTotalItem().toFixed(2)+'$');
        this.$('#itemPrixPiCar').html(item.prixPiCar().toFixed(2)+'$');
        this.$('#itemPrixTotal').html(item.grandTotalItem().toFixed(2)+'$');
    },
     
        
    dimInputValidate : function(event){
        if(! this.model.numericPattern.test($(event.currentTarget).val())){
                $(event.currentTarget).popover('show');
            }
            else {
                $(event.currentTarget).popover('hide');            
            }  
    },          
    updateQte : function(event){
        this.dimInputValidate(event);
        this.model.set('qte', this.$('.itemQte').val());
        this.updateSuperficie();
    },
    updateHaut : function(event){
        this.dimInputValidate(event);
        this.model.set('haut', this.$('.itemHaut').val());
        this.updateSuperficie();
    },
    updateLarg : function(event){
        this.dimInputValidate(event);
        this.model.set('larg', this.$('.itemLarg').val());
        this.updateSuperficie();
    },        
    updateRV : function(event){
        this.dimInputValidate(event);
        this.model.set('rv', this.$('.itemRV').val());
        this.updateSuperficie();
    },
    updatePerte : function(event){
        this.dimInputValidate(event);
        this.model.set('perte', this.$('.itemPerte').val());
        this.updateSuperficie();
    },
    updateVente : function(){
        var self = this;
        var $montantVente = this.$('#montantVente'+this.model.get('id'));        
        setTimeout(function(){    
            self.model.set('montantVente', parseFloat($montantVente.val()));
            if(self.model.isValid()){   // calls model.validate
                $montantVente.popover('destroy');
            }
        }, 10);
    }
        
        
        
    });
    
    return ItemView;
});