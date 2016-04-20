require.config({
    baseUrl: 'js/app',
    
    paths:{
        jquery: 'lib/jquery-min',
        underscore: 'lib/underscore-min',
        backbone: 'lib/backbone-min',
        bootstrap: 'lib/bootstrap.min',
        text: 'lib/text',
        backgrid: 'lib/backgrid.min',
        backbone_paginator : 'lib/backbone.paginator.min',
        backgrid_paginator : 'lib/backgrid-paginator.min'
    },
    
    shim:{
        bootstrap:{
            deps: ['jquery']
        },
        backgrid:{
            deps: ['backbone', 'jquery'],
            exports: 'Backgrid'
        }
        
//        backbone_paginator: {
//            deps: ['backbone', 'jquery'],
//            exports: 'Backbone.Paginator'
//        }
    }
});