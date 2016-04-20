//*****************************************************************************
// ENTETEVIEW
// Handles the entete rendering and validation
//*****************************************************************************
define(['jquery',
        'underscore',
        'backbone',
        'handlebars',
        'bootstrap',
        'bootstrap_fh'
        ], function($, _, Backbone, Handlebars, Bootstrap, BootstrapFH){
    
    var EnteteView = Backbone.View.extend({
        
        phonePattern : new RegExp("^[1-9][0-9]{2}[-|.| ][0-9]{3}[-|.| ][0-9]{4}$"),
        
        emailPattern : new RegExp("^[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,3}$"),
        
        initialize : function(options){
            this.bus = options.bus;
            this.listenTo(this.bus, 'submit', this.validateForPost);
         //*****************************************
         // VALIDATION 
         // VALIDATION DES CHAMPS REQUIS
         $('#inputProjet').popover(
             {content:"SVP entrer un nom de projet", 
              template:'<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content requiredError"></div></div>',
              placement: 'bottom', trigger: 'manual', html: true});

         $('#selectRepresentant').popover(
             {content:"SVP choisir un représentant", 
              template:'<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content requiredError"></div></div>',
              placement: 'bottom', trigger: 'manual', html: true});

         $('#selectClient').popover(
             {content:"SVP choisir un client", 
              template:'<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content requiredError"></div></div>',
              placement: 'bottom', trigger: 'manual', html: true});
                        
        // VALIDATION DU TELEPHONE
        $('#inputContactTel').popover(
            {content:'<span class=text-danger>Invalide (format xxx-xxx-xxxx)</span>', 
             placement: 'bottom', trigger: 'manual', html: true});
        
         // VALIDATION DU EMAIL
        $('#inputContactEmail').popover(
            {container: 'body', 
             content:'<span class=text-danger>Invalide (format xxx@xxx.xxx)</span>', 
             placement: 'bottom', trigger: 'manual', html: true});  
        //*****************************************  
                    
        var laDate = this.model.get('dateEstimation');
        var leJour = laDate.getDate();
        leJour = (leJour < 10) ? ("0" + leJour) : leJour;
        var leMois = parseInt(laDate.getMonth())+1;
        leMois = (leMois < 10)  ? ("0"+ leMois.toString()) : leMois.toString();
        var lAnnee = laDate.getFullYear();
        this.$("#inputDateEstimation").val(leJour + "/"+leMois+"/"+lAnnee);
        },
        
        events : {
                    "change .inputAValider" : "onChangeInput"
        },
        
        repSelectTmpl : Handlebars.compile(this.$('#rep-select-template').html()),
        
        clientSelectTmpl : Handlebars.compile(this.$('#client-select-template').html()),

        render: function(){
            var collClient = this.model.get('collClient');
            var collRep = this.model.get('collRep');
            
            // Render the template
            this.$('#selectRepresentant').html(this.repSelectTmpl({rep : collRep.toJSON()}));
            this.$('#selectClient').html(this.clientSelectTmpl({client : collClient.toJSON()}));
            
            return this;
        },
        
        onChangeInput : function(event){
            var $elem = $(event.currentTarget);
            if($elem.val().length > 0){
                var regex = this.phonePattern;

                if($elem.attr("id") == "inputContactEmail"){
                    regex = this.emailPattern;
                }                
                if(! regex.test($elem.val())){
                    $elem.popover('show');
                }
                else {
                    $elem.popover('hide');
                }       
            }
            else{
                $elem.popover('hide');
            } 
        },
        
        // Callback for the submit event
        validateForPost : function(){
            this.model.set('noError', true);
            
            // Validate the required fields
             var projet = $('#inputProjet');
             var rep = $('#selectRepresentant')
             var client = $('#selectClient');             

             // EMPTY project
             if(!projet.val()){ 
              this.model.set('noError', false);
              projet.popover('show');
              projet.bind("change", function(){
                  $(this).popover('hide');
                 });
             }

             // UNCHOSEN value
             if(rep.val() == "NA"){
              this.model.set('noError', false);
              rep.popover('show');
              rep.bind("change", function(){
                  $(this).popover('hide');
                 });
             }

             // TEST client
             if(client.val() == "5697"){
              this.model.set('noError', false);
              client.popover('show');
              client.bind("change", function(){
                  $(this).popover('hide');
                 });
             }            

        }
    });
    
    return EnteteView;
});

