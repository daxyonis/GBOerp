define(['underscore',
        'backbone',
        'backgrid'], 
       function(_, Backbone, Backgrid){
    
    var famSelectCell = Backgrid.SelectCell.extend({                
        
        optionValues : [["",null]].concat(_.map(productFamilies, function(obj,key){return [obj.family + " " + obj.description1, obj.family];})),
        
        render: function(){
            this.$el.empty();

        var optionValues = _.result(this, "optionValues");
        var model = this.model;
        var rawData = this.formatter.fromRaw(model.get(this.column.get("name")), model);

        var selectedText = [];

        try {
          if (!_.isArray(optionValues) || _.isEmpty(optionValues)) throw new TypeError;

          for (var k = 0; k < rawData.length; k++) {
            var rawDatum = rawData[k];

            for (var i = 0; i < optionValues.length; i++) {
              var optionValue = optionValues[i];

              if (_.isArray(optionValue)) {
                var optionText  = optionValue[0];
                var optionValue = optionValue[1];
                
                if (optionValue == rawDatum) selectedText.push(optionValue);    // THIS is line modified from Backgrid.js
              }
              else if (_.isObject(optionValue)) {
                var optionGroupValues = optionValue.values;

                for (var j = 0; j < optionGroupValues.length; j++) {
                  var optionGroupValue = optionGroupValues[j];
                  if (optionGroupValue[1] == rawDatum) {
                    selectedText.push(optionGroupValue[0]);
                  }
                }
              }
              else {
                throw new TypeError;
              }
            }
          }

          this.$el.append(selectedText.join(this.delimiter));
        }
        catch (ex) {
          if (ex instanceof TypeError) {
            throw new TypeError("'optionValues' must be of type {Array.<Array>|Array.<{name: string, values: Array.<Array>}>}");
          }
          throw ex;
        }

        this.delegateEvents();

        return this;
        }
        
    });
    
    return famSelectCell;
});