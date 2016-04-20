define(['jquery',
        'jquery.ui.datepicker-fr',
        'handlebars',
        'backbone',
        'jsroutes'    
        ], function($, jUI, Handlebars, Backbone, jsRoutes){
	
	var ToolbarView = Backbone.View.extend({
		     
     initialize: function(options){
         
         var self = this; 
         // Ajax load of all Representants       
         var route = jsRoutes.controllers.Reps.findAll();
            var def1 = $.getJSON(route.url,
                     function(jsonData) {
                self.updateRepresentants(jsonData);
          })
          .fail(function( jqxhr, textStatus, error ) {
              var err = textStatus + ", " + error;
              console.log( "Request Failed: " + err );
          });

            route = jsRoutes.controllers.Statuts.findAll();
            var def2 = $.getJSON(route.url,
                    function(jsonData) {
               self.updateStatuts(jsonData);
         })
         .fail(function( jqxhr, textStatus, error ) {
             var err = textStatus + ", " + error;
             console.log( "Request Failed: " + err );
         });

         // Sync all asynchronous ajax calls before updating view              
            $.when(def1, def2).done(function(){          	        
              // set select width
                self.$("select").css({"width":"175px"});                
            });
                           
         //*********************************************	
         // Setting the datepicker config and options
          $.datepicker.setDefaults( $.datepicker.regional[ "fr-CA" ] );
          this.$( ".mydatepicker" ).datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "yy-mm-dd"
          });
         
     },
     
     //*********************
     // Templating                        
     compileAndAddHtml : function(templateId, context, targetId){
       // Handlebars
       // Grab the template script
       var theTemplateScript = $('#'+templateId).html();    
       // Compile the template
       var theTemplate = Handlebars.compile(theTemplateScript);
       // Pass our data to the template
       var theCompiledHtml = theTemplate(context);
       // Add the compiled html to the page
       $('#'+targetId).html(theCompiledHtml);
     },
	
     updateRepresentants : function(jsonData){
      this.compileAndAddHtml('rep-select-template',{rep : jsonData},'rep');
      this.$('#rep').css("width","200px");
     },

     updateStatuts : function(jsonData){
      this.compileAndAddHtml('statut-select-template',{obj : jsonData},'statut');
      this.$('#statut').css("width","200px");
     },
     //*********************
     
     refresh: function(){
         $("#table").bootstrapTable('refresh');
     },
     
     onDateDebutChange : function(e){
        // Date debut change : c'est le min de Date fin		
			     var currentDate = $(e.currentTarget).datepicker( "getDate" );
			     this.$('#dateFin').datepicker("option", "minDate", currentDate);		
			     $('#table').bootstrapTable('refresh');		   
     },
     
     onDateFinChange: function(e){
        // Date debut change : c'est le min de Date fin		
       var currentDate = $(e.currentTarget).datepicker( "getDate" );
       this.$('#dateDebut').datepicker("option", "maxDate", currentDate);
       $('#table').bootstrapTable('refresh');		
     },
     
     removeAllFilters : function(){
        this.$('#numero').val("");
        this.$('#rep').val("NA");
        this.$("#statut").val("0");
        this.$('#dateDebut').val("");
        this.$('#dateFin').val("");
       // Reset search and sort options
       var opts = $('#table').bootstrapTable('getOptions');
       if(opts.sortName === 'suite' && opts.sortOrder ==='desc'){				
            $('#table').bootstrapTable('refreshReset');				
       }else{
            $('#table').bootstrapTable('refreshOptionsReset',{sortName : 'suite', sortOrder: 'desc'});
           }
     },

     events: {
         "change #numero"   : "refresh",
			      "change #rep"      : "refresh",
		       "change #statut"   : "refresh",
				     "change #dateDebut" : "onDateDebutChange",
		       "change #dateFin"   : "onDateFinChange",
			      "click #removeFilters" : "removeAllFilters"
     },
		
     render: function(){        
         return this;
     }
	});
    
    return ToolbarView;
});