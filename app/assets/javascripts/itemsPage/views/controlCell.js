define(['underscore',
        'backbone',
        'backgrid',
        'backform',
        'itemsPage/models/item',
        'itemsPage/views/itemForm',
        'itemsPage/views/confirmView'],
       function(_, Backbone, Backgrid, Backform, Item, itemForm, ConfirmView){
    
    var tmplString = '<button class="ediBtn"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>' +
                     '<button class="copyBtn"><span class="glyphicon glyphicon-duplicate" aria-hidden="true"></span></button>'+
                     '<button class="delBtn"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button>';
    
    var ControlCell = Backgrid.Cell.extend({
        template: _.template(tmplString),  
        
        initialize: function(options){
            Backgrid.Cell.prototype.initialize.call(this, options);
            this.confirm = new ConfirmView({model: this.model});
            this.listenTo(this.confirm, 'okToDelete', this.destroyModel);
        },
        
        events: {
          "click .delBtn":  "deleteRow",
          "click .ediBtn":  "editRow",
          "click .copyBtn": "copyRow"          
        },
        
        deleteRow: function (e) {         
          e.preventDefault();          
          $('body').append(this.confirm.render().$el);
        },                
        
        editRow: function(e){
           e.preventDefault();            
           var item = this.model;                       
           var ItemView = itemForm(item).render();           
           
           // Remove the form view when modal closes
           $('#itemModal').on('hidden.bs.modal', function(e){
               ItemView.remove();
               // Trigger form-closed event so the items grid can revalidate
               item.trigger("form:closed");
            });            
           $('.modal-body').append(ItemView.$el);
           $('#itemModal').modal();
        },
        
        copyRow: function(e){            
            e.preventDefault();
            var ordre = this.model.collection.last().get('noOrdre') + 1;
            var newModel = new Item(this.model.toJSON());
            newModel.set('noOrdre', ordre);
            newModel.unset("noItem");            
            this.model.collection.add(newModel);
        },
        
        destroyModel: function(){            
            this.model.destroy({wait: true, 
                             success: function(mod, resp){
                                 console.log("Success in removing model " + mod.get("nomItem"));
                             },
                             error: function(mod, resp){
                                 console.log(resp);
                             }});
        },
            
        render: function () {
          
            this.$el.html(this.template());
            this.delegateEvents();            
            return this;
        },
  
            
        
    });
    
    return ControlCell;
    
});
