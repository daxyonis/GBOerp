define(['underscore',
        'backbone'], 
       function(_, Backbone){
    
    var tmplString =  '<div id="my-confirm-dialog" class="dialog-overlay">';
    tmplString += '<div class="dialog-card">';
    tmplString += '<div class="dialog-question-sign"><span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span></div>';
    tmplString += '<div class="dialog-info">';
    tmplString += '<h5>Certain de faire ça ?</h5>';
    tmplString += "<p>L'item sera effacé de la soumission et n'existera plus.</p>";
    tmplString += '<button class="btn btn-primary dialog-confirm-button">Oui</button>';
    tmplString += '<button class="btn btn-danger dialog-reject-button">Non</button>';
    tmplString += '</div>        </div>    </div>'
        
    var ConfirmView = Backbone.View.extend({
        template: _.template(tmplString),
        
        events:{
          "click .dialog-reject-button"  : "hideAllDialogs",
          "click .dialog-confirm-button" : "okToDelete"
        },
        
        render : function(){
            this.$el.html(this.template());          
            this.delegateEvents();   
            
            var self = this;
            $('.dialog-overlay').on('click', function (e) {
                if(e.target == this){
                    // If the overlay was clicked/touched directly, hide the dialog
                    self.hideAllDialogs();
                }

            }); 
            
            $(document).keyup(function(e) {
                if (e.keyCode == 27) {
                    // When escape is pressed, hide all dialogs
                    self.hideAllDialogs();
                }
            });
            
            this.showDialog();
            return this;
        },
        
        okToDelete : function(e){
            this.hideAllDialogs();
            this.trigger('okToDelete');
        },
        
        showDialog: function(){
                // Find the dialog and show it
                var dialog = this.$('#my-confirm-dialog'),
                    card = dialog.find('.dialog-card');
                dialog.fadeIn();
                // Center it on screen
                card.css({
                    'margin-top' : -card.outerHeight()/4
                });
            },
        
        hideAllDialogs: function(){
                // Hide all visible dialogs
                this.$('.dialog-overlay').fadeOut();                
            },
        
    });
    
    return ConfirmView;
});