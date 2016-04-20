define(['underscore',
       'backbone',
       'models/soumission',
       'text!templates/soumheader.html'], 
       function(_, 
                Backbone,
                Soumission,
                SoumHeaderTmpl){
    
    
    var SoumView = Backbone.View.extend({
        
        el: '#soumHeader',
        
        template : _.template(SoumHeaderTmpl),
        
        initialize: function(){
            this.listenTo(this.model, 'change', this.render);
            if(this.model.isNew()){
                this.render();
            }
        },
        
        
        render: function(){
            this.$el.html( this.template( this.model.attributes ) );				  
            return this;
        }
    });
    
    return SoumView;
        
});