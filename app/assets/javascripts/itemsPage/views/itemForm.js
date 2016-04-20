define(['backbone',
        'backform'], 
       function(Backbone, Backform){
       
    var generate_my_form = function(model){
        return new Backform.Form({
            tagName: "form",
            id: "form",
            className: "form-horizontal",            
            model: model,
            fields: [
                    {name: "quantite", label: "Quantité" , control:"input", type:"number" },
                    {name: "nomItem", label:"Nom" , control:"textarea" },
                    {name: "description", label:"Description" , control:"textarea" },
                    {name: "notes", label:"Notes" , control:"textarea" },
                    {name: "facturation", label:"Description pour facturation", control:"textarea"},
                    {name: "largeur", label:"Largeur" , control:"input" },
                    {name: "hauteur", label:"hauteur" , control:"input" },
                    {
                        name: "rectoVerso", 
                        label:"RectoVerso" , 
                        control:"select", 
                        options:[
                            {label:"", value:null},
                            {label:"Recto",value:"Recto"},
                            {label:"Verso",value:"Verso"},
                            {label:"R/V",value:"R/V"}
                        ] 
                    },
                    {name: "fichierSource", label:"Fichier source", control:"input"},
                    {
                        name: "sourceProd", 
                        label:"Source Prod" , 
                        control:"select",
                        options:[
                                   {label:"Bo-Concept", value:"Bo-Concept"},
                                   {label:"Sérigraphie", value:"Sérigraphie"},
                                   {label:"Inventaire", value:"Inventaire"},
                                   {label:"Externe", value:"Externe"},
                                   {label:"Tempo", value:"Tempo"},
                                   {label:"Roland", value:"Roland"},
                                   {label:"Expédio", value:"Expédio"},
                                   {label:"Latex", value:"Latex"},
                                   {label:"Corejet", value:"Corejet"},
                                   {label:"Numérique", value:"Numérique"},
                                   {label:"Edge", value:"Edge"}
                        ]
                    },
                    {
                        name: "famille", 
                        label:"Famille" ,
                        control:"select", 
                        options: [{label:"", value:null}].concat(_.map(productFamilies, function(obj,key){
                                return {label: obj.family + " " + obj.description1, value: obj.family};
                            }))                    
                    },
                    {name: "fraisVariables", label:"Frais variables" , control:"input", type:"number" },
                    {name: "fraisInstallation", label:"Frais d'installation" , control:"input", type:"number" },
                    {name: "prixFab", label:"Prix de fabrication" , control: "input", type:"number"},
                    {name: "catalogue", label:"Catalogue" , control:"checkbox" },
                    {name: "codeCatalogue", label:"Code" , control:"input" },
                    {name: "notesInternes", label:"Notes internes", control:"input"},
                    {name: "sousCategorie", label:"Sous-catégorie (catalogue)", control:"input"}                    
                ]
        });
    }        
    
    return generate_my_form;
});