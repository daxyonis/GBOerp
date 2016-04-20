//*****************************************************************************
// ITEMSVIEW
// Holds the items collection, generates and adds new ITEMVIEW objects to its el
//*****************************************************************************
define(['jquery',
        'underscore',
        'backbone',
        'handlebars',
        'bootstrap',
        'soumflex/models/item',
        'soumflex/views/itemview'
        ], function($, _, Backbone, Handlebars, Bootstrap, Item, ItemView){
    
    var ItemsView = Backbone.View.extend({
        
        MAX_NUM_ITEMS : 5,
        
        noError : true,            
        
        initialize : function(options){
            this.collection.on('update', this.render, this);           
            this.collection.on('add', this.ajouterItem, this);
            this.collProd = options.collProd;
            this.refInfographie = options.refInfographie;
            this.refMaindOeuvre = options.refMaindOeuvre;
            this.refLaminage = options.refLaminage;            
        },                     
        
        events:{
            "click #btnAjoutItem" : "onAjoutItem",            
            "change #selectItem"  : "onSelectionItem"
        },
        
        ajouterItem : function(){
            var bus = _.extend({}, Backbone.Events); 
            
            //**************************************          
            this.collection.last().set('collProd', this.collProd, {silent : true});
            this.collection.last().set('referenceInfographie', this.refInfographie, {silent : true});
            this.collection.last().set('referenceMaindOeuvre', this.refMaindOeuvre, {silent : true});
            this.collection.last().set('referenceLaminage', this.refLaminage, {silent : true});
            this.collection.last().setTaux();
            //**************************************
            
            var newItemView = new ItemView({model : this.collection.last(), 
                                            id : 'itemView' + this.collection.last().get('id'), 
                                            bus : bus});
            this.$('#listeItems').append(newItemView.render().el);
            this.listenTo(newItemView, 'ItemView:retirer', this.onRetirerItem);            
            
            // Focus on the last item            
            var name = 'item' + this.collection.last().get('_id');
            this.scrollToAnchor(name);
        },
        
        onAjoutItem : function(event){  
            
            var size = this.collection.length;            
            if(size < this.MAX_NUM_ITEMS){
                this.noError = true;
                var nextId = (size == 0) ? 0 : (this.collection.last().get('id') + 1);
                var newItem = new Item({id : nextId, _id: (nextId+1)});
                this.collection.push(newItem);
            }
            else{
                this.noError = false;
                // TODO: ajout message erreur
                var msg = "<p class='text-danger bg-danger'>Le nombre d'items est limité à " + this.MAX_NUM_ITEMS +"</p>";
                this.$('#bottomErrMsg').html(msg);
            }
        },
        
        onRetirerItem : function(item){  
            this.noError = true;
            this.collection.remove(item);         
        },                
        
        scrollToAnchor : function(aName){
            var aTag = this.$("a[name='"+ aName +"']");
		          $(window).scrollTop(aTag.position().top);
        },
        
        render: function(){              
            
            if(this.noError){
                this.$('#bottomErrMsg').html('');
            }
            
            return this;
        }
    });
    
    return  ItemsView;
});

