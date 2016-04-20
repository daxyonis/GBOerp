/**
 * Module liste des soumissions
 *  
 */
define(['jquery',
        'backbone',                
		      'jsroutes',
		      'appsoumlist/views/toolbarView',
        'appsoumlist/views/tableView'
        
		], function(
		$, Backbone, jsRoutes, ToolbarView, TableView){	
	
     // Functions specified as parameters for the Bootstrap Table
     var queryParams = function(params){
            $('#toolbar').find('select').each(function () {
                params[$(this).attr('name')] = $(this).find('option:selected').val();
            });
            $('#toolbar').find('input[name]').each(function () {
                params[$(this).attr('name')] = $(this).val();
            });
            return params;
     };

     // Formats the column named dossier in the soums table
     // This function adds the link to the order update page
     var dossierFormatter = function(value) {
          if(value){
           var route = jsRoutes.controllers.Soumissions.order(value);		
           return '<a href=' + route.url + ' target=_blank>' + value + '</a>';
          }
          else{ 
           return '-';
          }
    };

        // This function formats the suite column as a link to the soum page
     var suiteFormatter = function(value, row){
          var route = jsRoutes.controllers.app.AppSoumissions.view(row.noSoumission);
          return '<a href=' + route.url + '>' + value + '</a>';
     };

     // This function formats the projet column as a link to the soum page
     var projetFormatter = function(value, row){
          var route = jsRoutes.controllers.app.AppSoumissions.view(row.noSoumission);
          return '<a href=' + route.url + '>' + value + '</a>';
     };

     // Displays the controls column (buttons), value = noSoumission
     var ctrlFormatter = function(value, row){
          var delUrl = jsRoutes.controllers.app.AppSoumissions.delete(value).url;
          var copyUrl = jsRoutes.controllers.app.AppSoumissions.copy(value).url;
          var copyNoItemsUrl = jsRoutes.controllers.app.AppSoumissions.copy(value, false).url;
          var htmlStr = '';
          htmlStr += '<button class="op copyBtn" data-url="' + copyUrl + 
                     '" data-url-noItems = "' + copyNoItemsUrl + 
                     '"><span class="glyphicon glyphicon-duplicate" aria-hidden="true"></span></button>';
          htmlStr += '<button class="op delBtn"  data-url="' + delUrl + 
                     '"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button>';
          return htmlStr;
     };
	
	var initialize = function(){          
		
      window.queryParams = queryParams;
      window.dossierFormatter= dossierFormatter;
      window.suiteFormatter= suiteFormatter;
      window.projetFormatter= projetFormatter;
      window.ctrlFormatter= ctrlFormatter;          

      this.toolbar = new ToolbarView({el:'#toolbar'});
      this.table = new TableView({el: '#table'});
     
     // Re-render the toolbar whenever the table is updated
     this.toolbar.listenTo(this.table, 'load-success.bs.table', this.toolbar.render);
     
     this.table.render();
     
 };	
    
    return {
        initialize: initialize       
    };		
    
    
});

