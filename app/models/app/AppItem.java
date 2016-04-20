package models.app;

import java.text.DecimalFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import play.data.validation.Constraints;

public class AppItem {
	
	private static final String IMAGE_SOURCE = "P:\\Soumission\\Formulaires\\AucuneImage.jpg";

	@Constraints.Min(0)
	private int noItem = 0;	// cannot be null; but 0 means that is not yet saved		
	private int noSoumission = 0;
	
	@Constraints.Min(0)
	private int noOrdre= 0;
	
	@Constraints.MinLength(1)
	private String nomItem = "Nom de l'item";
	
	@Constraints.MinLength(1)
	private String description = "Description de l'item";
	
	@Constraints.Min(0)
	private int quantite = 1;
	
	@Constraints.MaxLength(5)
	private String rectoVerso;
	
	@Constraints.MaxLength(20)
	@Constraints.MinLength(1)
	private String sourceProd = "Bo-Concept";		
	
	@Constraints.MaxLength(255)
	private String fichierSource= IMAGE_SOURCE;		
	
	@Constraints.MaxLength(15)
	@Constraints.MinLength(1)
	private String largeur = "0"; 	//nvarchar(15) NOT NULL DEFAULT 0 CHECK ((len(Largeur)>(0))),
	
	@Constraints.MaxLength(15)
	@Constraints.MinLength(1)
	private String hauteur = "0";	//nvarchar(15) NOT NULL DEFAULT 0 CHECK ((len(Hauteur)>(0))),
	
	private String notes;			// mappé vers Notes
	
	@Constraints.MaxLength(250)
	private String notesInternes;
	
	@Constraints.MaxLength(50)
	private String sousCategorie;
	
	//Avis tinyint NOT NULL DEFAULT 0, -- what is it ?
	private int geniusDetailID = 0;
	private int flagDetail  = 0;
	//SSMA_TimeStamp timestamp NOT NULL,
	
	@Constraints.MaxLength(10)
	private String famille;
	
	@Constraints.MaxLength(3000)
	private String facturation;
	
	private int geniusItem;	
	
	private double fraisVariables = 0.0; // money NOT NULL DEFAULT 0,
	private double fraisInstallation = 0.0; // money NOT NULL DEFAULT 0,
	private double prixFab = 0.0;	// money NOT NULL DEFAULT 0,	
	
	@Constraints.MaxLength(50)
	private String codeCatalogue;
	
	private boolean catalogue;
	
	public AppItem(){
		
	}

	public AppItem(AppItem item){
		
		this.catalogue = item.catalogue;
		this.codeCatalogue = item.codeCatalogue;
		this.description = item.description;
		this.facturation = item.facturation;
		this.famille = item.famille;
		this.fichierSource = item.fichierSource;
		this.fraisInstallation = item.fraisInstallation;
		this.fraisVariables = item.fraisVariables;
		this.geniusDetailID = item.geniusDetailID;
		this.geniusItem = item.geniusItem;
		this.hauteur = item.hauteur;
		this.largeur = item.largeur;
		this.noItem = 0;	// because the copy is in fact a new AppItem
		this.nomItem = item.nomItem;
		this.noSoumission = 0; // because the copy is in fact a new AppItem
		this.notes = item.notes;
		this.notesInternes = item.notesInternes;
		this.prixFab = item.prixFab;
		this.quantite = item.quantite;
		this.rectoVerso = item.rectoVerso;
		this.sourceProd = item.sourceProd;
		this.sousCategorie = item.sousCategorie;
	}
	
	//**********************************
	// GETTERS AND SETTERS
	public int getNoItem() {
		return noItem;
	}

	public void setNoItem(int noItem) {
		this.noItem = noItem;
	}		

	public int getNoSoumission() {
		return noSoumission;
	}

	public void setNoSoumission(int noSoumission) {
		this.noSoumission = noSoumission;
	}

	public int getNoOrdre() {
		return noOrdre;
	}

	public void setNoOrdre(int noOrdre) {
		this.noOrdre = noOrdre;
	}

	public String getNomItem() {
		return nomItem;
	}

	public void setNomItem(String nomItem) {
		this.nomItem = nomItem;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public String getRectoVerso() {
		return rectoVerso;
	}

	public void setRectoVerso(String rectoVerso) {
		this.rectoVerso = rectoVerso;
	}

	public String getSourceProd() {
		return sourceProd;
	}

	public void setSourceProd(String sourceProd) {
		this.sourceProd = sourceProd;
	}

	public String getFichierSource() {
		return fichierSource;
	}

	public void setFichierSource(String fichierSource) {
		this.fichierSource = fichierSource;
	}

	public String getLargeur() {
		return largeur;
	}

	public void setLargeur(String largeur) {
		this.largeur = largeur;
	}

	public String getHauteur() {
		return hauteur;
	}

	public void setHauteur(String hauteur) {
		this.hauteur = hauteur;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotesInternes() {
		return notesInternes;
	}

	public void setNotesInternes(String notesInternes) {
		this.notesInternes = notesInternes;
	}

	public String getSousCategorie() {
		return sousCategorie;
	}

	public void setSousCategorie(String sousCategorie) {
		this.sousCategorie = sousCategorie;
	}

	public int getGeniusDetailID() {
		return geniusDetailID;
	}

	public void setGeniusDetailID(int geniusDetailID) {
		this.geniusDetailID = geniusDetailID;
	}

	public int getFlagDetail() {
		return flagDetail;
	}

	public void setFlagDetail(int flagDetail) {
		this.flagDetail = flagDetail;
	}

	public String getFamille() {
		return famille;
	}

	public void setFamille(String famille) {
		this.famille = famille;
	}

	public String getFacturation() {
		return facturation;
	}

	public void setFacturation(String facturation) {
		this.facturation = facturation;
	}

	public int getGeniusItem() {
		return geniusItem;
	}

	public void setGeniusItem(int geniusItem) {
		this.geniusItem = geniusItem;
	}

	public double getFraisVariables() {
		return fraisVariables;
	}

	public void setFraisVariables(double fraisVariables) {
		this.fraisVariables = fraisVariables;
	}

	public double getFraisInstallation() {
		return fraisInstallation;
	}

	public void setFraisInstallation(double fraisInstallation) {
		this.fraisInstallation = fraisInstallation;
	}

	public double getPrixFab() {
		return prixFab;
	}

	public void setPrixFab(double prixFab) {
		this.prixFab = prixFab;
	}

	public String getCodeCatalogue() {
		return codeCatalogue;
	}

	public void setCodeCatalogue(String codeCatalogue) {
		this.codeCatalogue = codeCatalogue;
	}

	public boolean isCatalogue() {
		return catalogue;
	}

	public void setCatalogue(boolean catalogue) {
		this.catalogue = catalogue;
	}
	//**********************************

	@JsonIgnore
	public String getFormattedPrixFab(){		
		DecimalFormat df = new DecimalFormat("### ###.00");	    
	    return df.format(this.prixFab);
	}

	@Override
	public String toString() {
		String str = "";
		str += "NoItem = " + noItem;
		str += ", NoSoumission = " + noSoumission;
		str += ", Quantité = " + quantite;
		str += ", NomItem = " + nomItem;
		str += ", Largeur = " + largeur;
		str += ", Hauteur = " + hauteur;
		
		return str; 	
	}
	
	
}
