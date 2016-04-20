define(['underscore',
        'backbone',
        'backgrid'], 
       function(_, Backbone, Backgrid){
    
    var rvSelectCell = Backgrid.SelectCell.extend({
            optionValues: [["",""],
                           ["Recto", "Recto"], 
                           ["Verso", "Verso"],
                           ["R/V", "R/V"]]
           });
    
    return rvSelectCell;
});