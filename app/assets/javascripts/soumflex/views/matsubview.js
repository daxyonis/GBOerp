//*****************************************************************************
// MATSUBVIEW
// Subview for Materiel
//*****************************************************************************
define(['jquery',
        'underscore',
        'backbone',
        'handlebars',
        'bootstrap',
        'text!soumflex/templates/itemMatTpl.html',
        'text!soumflex/templates/prodSelTpl.html',
        'text!soumflex/templates/prodDescTpl.html'
        ], function($, _, Backbone, Handlebars, Bootstrap, 
                     MatTemplate, ProdSelectTemplate, ProdDescTemplate){    
    
    var matSubView = Backbone.View.extend({
        
        tagName : 'div',
        
        className : 'tab-pane mat',
        
        matTemplate : Handlebars.compile(MatTemplate),
        
        prodSelectTemplate : Handlebars.compile(ProdSelectTemplate),
        
        prodDescTemplate : Handlebars.compile(ProdDescTemplate),
        
        initialize : function(options){
            this.bus = options.bus;
            this.listenTo(this.bus, 'ItemView:retirer', this.remove);
            this.listenTo(this.model, 'change', this.onModelChange);
        },
               
        events : {
            "change .matCat:input"   :   "onUpdateCat",
            "change .matProd:input"  :   "onUpdateProd"
        },               
        
        render : function(){
            
            var obj4Tmpl = this.model.toJSON();
            obj4Tmpl.catArr = this.model.getCategories();
            this.$el.html(this.matTemplate(obj4Tmpl));
            
            this.renderProdSelection();
            return this;
        }, 
        
        renderProdSelection : function(){
            this.$('select.matProd').html(this.prodSelectTemplate({flexArr : this.model.getListeProd()}));            
            this.renderProdDescription();
        },
        
        renderProdDescription : function(){
            this.$('#MatDescription'+this.model.get('id')).html(this.prodDescTemplate(this.model.leProduit()));
            this.updateMatResumeTable();      
        },
        
        onModelChange : function(){
          var item = this.model;
          if(item.hasChanged('qte')  || item.hasChanged('haut') || 
             item.hasChanged('larg') || item.hasChanged('perte')||
             item.hasChanged('rv')){
              this.updateMatResumeTable();
          }
        },
        
        onUpdateCat : function(){
            var item = this.model;
            var itemNo = item.get('id');
            item.set({'type': this.$('#matType'+itemNo+' option:selected').val(),
                      'cat' : this.$('#matCat'+itemNo+' option:selected').val(),
                      'prod': 0});
            this.renderProdSelection();
        },
        
        onUpdateProd : function(){
            var item = this.model;
            var itemNo = item.get('id');
            item.set({'type': this.$('#matType'+itemNo+' option:selected').val(),
                      'cat' : this.$('#matCat'+itemNo+' option:selected').val(),
                      'prod': parseInt(this.$('#matProd'+itemNo+' option:selected').val())});
        
            this.renderProdDescription();            
        },
        
        // Update la vue Materiel
        updateMatResumeTable : function(){
            var item = this.model;
            var i = item.get('id');
            if(item.produitChoisi()){
                var coutant = item.matCoutant();
                this.$('#matCoutant'+i).html(coutant.toFixed(2));
                var vendant = item.matVendant();
                this.$('#matVendant'+i).html(vendant.toFixed(2));
                var prixTot = item.matPrixTotal();
                this.$('#matPrixTotal'+i).html(prixTot.toFixed(2)+'$');     
                this.$('.profit-margin').html(item.get('marge')+'%');
            }
            else{
                this.$('#matCoutant'+i).html("");
                this.$('#matVendant'+i).html("");
                this.$('#matPrixTotal'+i).html("");    
                this.$('#MatDescription'+i).html("");
            }        
        },
        
    });
    
    return matSubView;
});