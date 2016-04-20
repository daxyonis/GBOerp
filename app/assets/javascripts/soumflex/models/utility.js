define([],function(){
    
    var Utility = {
        
        objCopy : function(obj){
            var newObj = {};
            $.extend(newObj, obj);
            $.each(newObj, function(key,value){
               newObj[key] = ""; 
            });
            return newObj;
        }
       
    };
    
    return Utility;
    
});