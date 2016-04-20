package models;

import java.text.DecimalFormat;
//import java.sql.Timestamp;

public class Item {
/****** Object:  Table dbo.Items    Script Date: 11/05/2014 10:05:35 ******/
	
	private int NoItem;
	private int NoSoumission;
	private String NomItem;
	private String Description;
	private int Quantite;
	private String SourceProd;
	private double FraisVariables; // money
	private double FraisInstallation; //money 
	private double PrixFab; // money 
	private String Hauteur; // nvarchar(15) 
	private String Largeur; // nvarchar(15) 
	private String rectoVerso;
	//private Timestamp SSMA_TimeStamp ; //timestamp
	private String Famille;			
	
	// Getters and setters
	public int getNoItem() {
		return NoItem;
	}
	public void setNoItem(int noItem) {
		NoItem = noItem;
	}
	public int getNoSoumission() {
		return NoSoumission;
	}
	public void setNoSoumission(int noSoumission) {
		NoSoumission = noSoumission;
	}
	public String getNomItem() {
		return NomItem;
	}
	public void setNomItem(String nomItem) {
		NomItem = nomItem;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public int getQuantite() {
		return Quantite;
	}
	public void setQuantite(int quantite) {
		Quantite = quantite;
	}
	public String getSourceProd() {
		return SourceProd;
	}
	public void setSourceProd(String sourceProd) {
		SourceProd = sourceProd;
	}
	public double getFraisVariables() {
		return FraisVariables;
	}
	public void setFraisVariables(double fraisVariables) {
		FraisVariables = fraisVariables;
	}
	public double getFraisInstallation() {
		return FraisInstallation;
	}
	public void setFraisInstallation(double fraisInstallation) {
		FraisInstallation = fraisInstallation;
	}
	public double getPrixFab() {
		return PrixFab;
	}
	public void setPrixFab(double prixFab) {
		PrixFab = prixFab;
	}
	public String getHauteur() {
		return Hauteur;
	}
	public void setHauteur(String hauteur) {
		Hauteur = hauteur;
	}
	public String getLargeur() {
		return Largeur;
	}
	public void setLargeur(String largeur) {
		Largeur = largeur;
	}
	public String getFamille() {
		return Famille;
	}
	public void setFamille(String famille) {
		Famille = famille;
	}
		
	public String getFormattedPrixFab(){		
		DecimalFormat df = new DecimalFormat("### ###.00");	    
	    return df.format(PrixFab);
	}
	
	public String getFormattedPrixUnitaire(){		
		double total = (PrixFab + FraisVariables + FraisInstallation) / Quantite;
		DecimalFormat df = new DecimalFormat("### ###.00");	    
	    return df.format(total);			   
	}
	public String getRectoVerso() {
		return rectoVerso;
	}
	public void setRectoVerso(String rectoVerso) {
		this.rectoVerso = rectoVerso;
	}
	
	
}