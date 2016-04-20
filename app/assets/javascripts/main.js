// RequireJS configuration options

requirejs.config({
	baseUrl: '/assets/javascripts',
	
	paths:{
		      'jsroutes'		: '/javascriptRoutes',
        'jquery'		: '../lib/jquery/jquery.min',
        'jquery.ui'		: '../lib/jquery-ui/jquery-ui.min',
        'jquery.ui.datepicker-fr' : 'lib/datepicker-fr-CA',
        'underscore'	: '../lib/underscorejs/underscore',
        'backbone'		: '../lib/backbonejs/backbone',
        'bootstrap'		: '../lib/bootstrap/js/bootstrap.min',
        'handlebars'	: '../lib/handlebars/handlebars.min',
        'bootstrap_fh'	: 'lib/bootstrap-formhelpers.min',
        'text'			: '../lib/requirejs-text/text',
		      'backgrid'		: '../lib/backgrid/backgrid',
        'backgrid-text-cell': 'lib/backgrid-text-cell_fr',
        'backform'		: 'lib/backform',
        'bootstrap-table' : 'lib/bootstrap-table_eva.min',
        'bootstrap-table-fr' : 'lib/bootstrap-table-fr-FR.min'
    },
    
    shim:{		
        'bootstrap': ['jquery'],

        'bootstrap_fh':['bootstrap'],

        'bootstrap-table' : ['bootstrap'],
        
        'bootstrap-table-fr': ['bootstrap-table'],                
        
        'jquery.ui.datepicker-fr' : ['jquery.ui'],
        
        'handlebars': {
            exports: 'Handlebars'
        },

      'jsroutes': {
                exports: "jsRoutes"
            },

      'backgrid':{
       deps: ['underscore', 'backbone'],
       exports: 'Backgrid'
      },
        
      'backform' :['underscore', 'backbone']      
    }
	
});

require(['jquery'], function(){
    $(document).ready(function(){
      $('#disconn').on('click', function(e){
       e.preventDefault();
       $('#logoutForm').submit();
      });
	   });
});