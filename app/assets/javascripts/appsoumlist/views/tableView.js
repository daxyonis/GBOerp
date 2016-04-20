/**
 * Vue de la table des soumission (#table)
 *  
 */
define(['jquery',
        'backbone',
        'bootstrap-table',
        'bootstrap-table-fr',
        'appsoumlist/views/dialogView'
        
		], function(
		$, Backbone, BootstrapTable, BootstrapTableFR, DialogView
		){	
	
    var TableView = Backbone.View.extend({
        
        events: {
          'click .copyBtn'  : 'copy',
          'click .delBtn'   : 'del',
          //'click .newBtn': 'newSoum'      // this does not work, see comment in render
        },                           
        
        render: function(){
            this.$el.bootstrapTable();
            this.delegateEvents();
            
            // NOTE: Bootstrap-table crée une table header par-dessus la table originale,
            // ce qui fait que le binding des events de Backbone.View ne fonctionne plus pour le bouton New
            // (c'est le bouton clone dans la nouvelle table qui est cliqué, il n'est pas relié à la
            // table originale). On fait donc un binding jQuery traditionnel ici.
            var self = this;
            $('button.newBtn').on('click', function(e){
                self.newSoum(e);
            });
            
            return this;
        },
        
        copy: function(e){
          // Operation model
          var operation = new Backbone.Model({op: "copy", label: "Copier la soumission", message: "Copier aussi les items ?", 
                                                url: $(e.currentTarget).attr("data-url"), 
                                                urlNoItems: $(e.currentTarget).attr("data-url-noItems")});
           
          this.doConfirm(operation);
        },
        
        del: function(e){
            // Operation model
          var operation = new Backbone.Model({op: "del", label: "Supprimer la soumission", message: "Certain de vouloir supprimer ?", 
							                                       url: $(e.currentTarget).attr("data-url")});
           
          this.doConfirm(operation);
        },
        
        newSoum: function(e){            
            var operation = new Backbone.Model({op: "new", label: "Soumissioner", message: "Créer une nouvelle soumission ?",
                                               url: $(e.currentTarget).attr("data-url")});
            this.doConfirm(operation);
        },
        
        doConfirm: function(op){
            // Create confirmDialog view to handle confirmation
              var confirm = new DialogView({model: op});
              this.listenTo(confirm, 'removeSoum', this.removeRow);
              this.listenTo(confirm, 'addSoum', this.insertRow);

              $('.mainContent').append(confirm.render().el);
        },
        
        removeRow: function(data){
            //console.log("removing row");
            this.$el.bootstrapTable('remove',{field: "noSoumission", values: [data.noSoumission]});
            this.$el.bootstrapTable('refresh', {silent: true});
        },
        
        insertRow: function(data){
            //console.log("inserting row");
            this.$el.bootstrapTable('insertRow', {index: 0, row: data});		
        }
    });
    
    return TableView;
    
});

