define(['underscore',
        'backbone',
        'backgrid'], 
       function(_, Backbone, Backgrid){
    
        var srcSelectCell = Backgrid.SelectCell.extend({
                    optionValues: [["Bo-Concept","Bo-Concept"],
                                   ["Sérigraphie","Sérigraphie"],
                                   ["Inventaire","Inventaire"],
                                   ["Externe","Externe"],
                                   ["Tempo","Tempo"],
                                   ["Roland","Roland"],
                                   ["Expédio","Expédio"],
                                   ["Latex","Latex"],
                                   ["Corejet","Corejet"],
                                   ["Numérique","Numérique"],
                                   ["Edge","Edge"]]
                });
        return srcSelectCell;
});