define(['backbone',
        'handlebars',
       'text!appsoumlist/templates/dialog.html',], 
       function(Backbone, Handlebars, DialogTemplate){       
        
    var ConfirmView = Backbone.View.extend({
        
        tagName : 'div',
        
        className : 'modal',
        
        attributes : {"tabindex":"-1", "role":"dialog", "aria-labelledby":"confirmLabel"},
        
        template : Handlebars.compile(DialogTemplate),  
        
        events: {
          'hidden.bs.modal'  : 'onModalHidden',
          'click button.non' : 'non',
          'click button.oui' : 'oui'
        },
        
        render : function(){
            this.$el.html(this.template(this.model.toJSON()));
            this.$el.modal();
            return this;
        },
        
        // Callback when confirmation answer is Non
        non: function(){
            
            var self = this;
            var model = this.model;
            
            switch(model.get('op')){
                case "new":
                case "del":
                    // do nothing for these cases
                    break;
                case "copy":
                    // It means we copy the soum without its items
                    $.get(model.get('urlNoItems'),
                      function(data){
                          self.trigger('addSoum', data);                          				
                      }
                    );
                    break;
                default: 
                    console.log("Unsupported operation: " + model.get('op'));
            }
            
        },
        
        // Callback when confirmation answer is Oui
        oui: function(){
            var self = this;
            var model = this.model;
            
            switch(model.get('op')){
                    
                case "new":
                    // Goto link href
                    location.href = model.get('url');
                    break;
                    
                case "del":
                    // We want to delete
                    $.ajax({method: 'DELETE',
                     url: model.get('url'),
                     success: function(data){
                        self.trigger('removeSoum', data);
                     },
                     error: function(xhr,textStatus,errorThrown){
                        console.log('Error: ' + errorThrown + ', Status: ' + textStatus);
                     }});
                    break;
                    
                case "copy":
                    // It means we copy the soum with its items
                    $.get(model.get('url'),
                      function(data){
                          self.trigger('addSoum', data);                          				
                      }
                    );
                    break;
                default: 
                    console.log("Unsupported operation: " + model.get('op'));
            }
            
                
           
        },
        
        // Callback when modal disappears : we wait a little then we remove this view
        onModalHidden : function(){
            var self = this;
            //console.log("Modal disappeared!");
            setTimeout(function(){
                self.remove();
                //console.log('Dialog view removed');
            }, 500);
        }
    });
    
    return ConfirmView;
});