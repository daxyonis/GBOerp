/******************************************************************/
/* MODELE FINITION                                                */
/******************************************************************/
define(['underscore',
				    'backbone'], function(_, Backbone){

					var finition = Backbone.Model.extend({
         
         defaults:{
            list : [],
            curFinitionId : 0,
            curCotes : " - ",
            optTransferTape : [{opt: "Non"}],
            transferTape :"Non",
            COUT_TRANSFER_TAPE : 0.12   // $/pi2            
        },         
         
        getList : function(){
            return(this.attributes.list);
        },

        setCurCotes : function(cotes){
            this.attributes.curCotes = cotes;    
        },

        curFinition : function(){
            var curId = this.attributes.curFinitionId;
            var newFinition = {};
            $.each(this.attributes.list, function(i,obj){        
                if(obj.id == curId){
                    $.extend(newFinition, obj);
                    return newFinition;
                }        
            });
            return newFinition;
        },

        setList : function(listOfFinitions, item){
            this.attributes.list = listOfFinitions;
            this.setCurFinitionId(listOfFinitions[0].id, item);    
            var curFinition = this.curFinition();
            this.attributes.curCotes = curFinition.cotes1;
        },

        setCurFinitionId : function(finitionId, item){
            this.attributes.curFinitionId = finitionId;    
            this.updateTransferTapeOpt(item);
        },

        updateTransferTapeOpt : function(item){
            var prod = item.leProduit();
            if(prod.flex.id && prod.flex.categorie.toLowerCase().indexOf("vinyle") >= 0){
             //*****************************************************
             // TO BE MODIFIED WHEN TRANSFER TAPE IS CLARIFIED
             // Currently vinyls do not support any finition. 
             // Does not make sense to enable transfer tape alone 
             // with no finition for vinyls.
                // transfer tape ok
                //this.optTransferTape = [{opt: "Non"}, {opt: "Oui"}];
             //*****************************************************
             this.attributes.optTransferTape = [{opt: "Non"}];
            }
            else{
                this.attributes.optTransferTape = [{opt: "Non"}];
            }
        },

        sousTotal : function(item){
            if(!this.attributes.curFinitionId)
                return 0.0;

            var curFin = this.curFinition();
            if(curFin.type == "Aucune"){
                return 0.0;
            }
            else{
                return(curFin.coutFixe + item.superficieAvecPerte() * curFin.prix);
            }  
        },

        prixTransTape : function(item){
            if(this.attributes.transferTape == "Oui"){
                return(this.attributes.COUT_TRANSFER_TAPE * item.superficieAvecPerte());
            }
            return 0.0;    
        },

        total : function(item){
            return(this.sousTotal(item) + this.prixTransTape(item));
        },

        vendant : function(item, marge){
         return(this.total(item) / (1.0 - 0.01 * marge));
        }

     });
	
					return finition;
});
