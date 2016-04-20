/**************************************************
* BaseModel
* enables computed values as model attributes
* the computed value must be defined as a function
* in the defaults; the computed value does not
* appear in the JSONified model (thus not sent to server).
* Credits to Kilon (http://kilon.org/blog/2012/02/backbone-calculated-fields/)
**************************************************/
define(['underscore', 
        'backbone'], 
       function (_, Backbone){
    
    var BaseModel = Backbone.Model.extend({            
    
        get: function(attr) {
            var value = Backbone.Model.prototype.get.call(this, attr);
            return _.isFunction(value) ? value.call(this) : value;
        },
        
        toJSON: function() {
          var json = Backbone.Model.prototype.toJSON.apply(this, arguments);
          _.each(json, function (value, key) {
            if (typeof value == 'function') {
              delete json[key];
            }
          });
          return json;
        }
        
    });    
    return BaseModel;    
});