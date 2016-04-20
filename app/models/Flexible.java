/**
 * 
 */
package models;

import play.data.validation.Constraints;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Eva
 *
 */
public class Flexible {
	
	// Fields from table MaitreFlexibles
	private short Id;					// smallint IDENTITY(1001,1)  ,
	private String Categorie; 			// nvarchar(30) ,	
	private String Utilite; 			// nvarchar(50) ,
	private boolean EnStock;			// bit ,
	private String FicheTechniqueNom; 	// nvarchar(20) ,
	private String FicheTechniqueLien;	// nvarchar(200) ,
	private String Distributeur;	  	// nvarchar(30) ,
	
	@Constraints.Required(message="Champ requis")
	private String Produit;			  	// nvarchar(50)  ,	
	private String NoItemGenius;	  	// varchar(25) ,
	
	@Constraints.Min(value = 0 ,message="Valeur admissible : entre 0.00 et 999.99")
	@Constraints.Max(value = 999 ,message="Valeur admissible : entre 0.00 et 999.99")
	private double Epaisseur = 0.0;	  	// decimal(5, 2) ,
	private String EpaisseurUnites;	  	// nchar(3) ,
	
	private String Format;				// nvarchar(50) ,
	private int formatMax = 0;				// int
	
	@Constraints.Min(value = 0, message="Valeur admissible : entre 0.00 et 9.99")
	@Constraints.Max(value = 9, message="Valeur admissible : entre 0.00 et 9.99")
	private double PrixCoutant1 = 0.0;	// decimal(3, 2) ,
	@Constraints.Min(value = 0,message="Valeur admissible : entre 0.00 et 9.99")
	@Constraints.Max(value = 9, message="Valeur admissible : entre 0.00 et 9.99")
	private double PrixCoutant2 = 0.0;	// decimal(3, 2) ,
	
	private String Description;			// nvarchar(max) ,
	private String Caracteristiques;	// nvarchar(80) ,
	private String InterieurExterieur;	// nchar(12) ,
	private String DureeSupport;		// nvarchar(25) ,
	private String Impression;			// nvarchar(25) ,
	private String CommentaireUtile;	// nvarchar(80) ,
	
	private List<Imprimante> imprimantes = new LinkedList<Imprimante>();		
	
	public List<Imprimante> getImprimantes() {
		return imprimantes;
	}
	public void setImprimantes(List<Imprimante> imprimantes) {
		this.imprimantes = imprimantes;
	}
	/**
	 * @return the id
	 */
	public short getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(short id) {
		Id = id;
	}
	/**
	 * @return the categorie
	 */
	public String getCategorie() {
		return Categorie;
	}
	/**
	 * @param categorie the categorie to set
	 */
	public void setCategorie(String categorie) {
		Categorie = categorie;
	}
	/**
	 * @return the utilite
	 */
	public String getUtilite() {
		return Utilite;
	}
	/**
	 * @param utilite the utilite to set
	 */
	public void setUtilite(String utilite) {
		Utilite = utilite;
	}
	/**
	 * @return the enStock
	 */
	public boolean isEnStock() {
		return EnStock;
	}
	/**
	 * @param enStock the enStock to set
	 */
	public void setEnStock(boolean enStock) {
		EnStock = enStock;
	}
	/**
	 * @return the ficheTechniqueNom
	 */
	public String getFicheTechniqueNom() {
		return FicheTechniqueNom;
	}
	/**
	 * @param ficheTechniqueNom the ficheTechniqueNom to set
	 */
	public void setFicheTechniqueNom(String ficheTechniqueNom) {
		FicheTechniqueNom = ficheTechniqueNom;
	}
	/**
	 * @return the ficheTechniqueLien
	 */
	public String getFicheTechniqueLien() {
		return FicheTechniqueLien;
	}
	/**
	 * @param ficheTechniqueLien the ficheTechniqueLien to set
	 */
	public void setFicheTechniqueLien(String ficheTechniqueLien) {
		FicheTechniqueLien = ficheTechniqueLien;
	}
	/**
	 * @return the distributeur
	 */
	public String getDistributeur() {
		return Distributeur;
	}
	/**
	 * @param distributeur the distributeur to set
	 */
	public void setDistributeur(String distributeur) {
		Distributeur = distributeur;
	}
	/**
	 * @return the produit
	 */
	public String getProduit() {
		return Produit;
	}
	/**
	 * @param produit the produit to set
	 */
	public void setProduit(String produit) {
		Produit = produit;
	}
	/**
	 * @return the noItemGenius
	 */
	public String getNoItemGenius() {
		return NoItemGenius;
	}
	/**
	 * @param noItemGenius the noItemGenius to set
	 */
	public void setNoItemGenius(String noItemGenius) {
		NoItemGenius = noItemGenius;
	}
	/**
	 * @return the epaisseur
	 */
	public double getEpaisseur() {
		return Epaisseur;
	}
	/**
	 * @param epaisseur the epaisseur to set
	 */
	public void setEpaisseur(double epaisseur) {
		Epaisseur = epaisseur;
	}
	/**
	 * @return the epaisseurUnites
	 */
	public String getEpaisseurUnites() {
		return EpaisseurUnites;
	}
	/**
	 * @param epaisseurUnites the epaisseurUnites to set
	 */
	public void setEpaisseurUnites(String epaisseurUnites) {
		EpaisseurUnites = epaisseurUnites;
	}
	/**
	 * @return the format
	 */
	public String getFormat() {
		return Format;
	}
	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		Format = format;
	}
	
	public int getFormatMax() {
		return formatMax;
	}
	public void setFormatMax(int formatMax) {
		this.formatMax = formatMax;
	}
	/**
	 * @return the prixCoutant1
	 */
	public double getPrixCoutant1() {
		return PrixCoutant1;
	}
	/**
	 * @param prixCoutant1 the prixCoutant1 to set
	 */
	public void setPrixCoutant1(double prixCoutant1) {
		PrixCoutant1 = prixCoutant1;
	}
	/**
	 * @return the prixCoutant2
	 */
	public double getPrixCoutant2() {
		return PrixCoutant2;
	}
	/**
	 * @param prixCoutant2 the prixCoutant2 to set
	 */
	public void setPrixCoutant2(double prixCoutant2) {
		PrixCoutant2 = prixCoutant2;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return Description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		Description = description;
	}
	/**
	 * @return the caracteristiques
	 */
	public String getCaracteristiques() {
		return Caracteristiques;
	}
	/**
	 * @param caracteristiques the caracteristiques to set
	 */
	public void setCaracteristiques(String caracteristiques) {
		Caracteristiques = caracteristiques;
	}
	/**
	 * @return the interieurExterieur
	 */
	public String getInterieurExterieur() {
		return InterieurExterieur;
	}
	/**
	 * @param interieurExterieur the interieurExterieur to set
	 */
	public void setInterieurExterieur(String interieurExterieur) {
		InterieurExterieur = interieurExterieur;
	}
	/**
	 * @return the dureeSupport
	 */
	public String getDureeSupport() {
		return DureeSupport;
	}
	/**
	 * @param dureeSupport the dureeSupport to set
	 */
	public void setDureeSupport(String dureeSupport) {
		DureeSupport = dureeSupport;
	}
	/**
	 * @return the impression
	 */
	public String getImpression() {
		return Impression;
	}
	/**
	 * @param impression the impression to set
	 */
	public void setImpression(String impression) {
		Impression = impression;
	}
	/**
	 * @return the commentaireUtile
	 */
	public String getCommentaireUtile() {
		return CommentaireUtile;
	}
	/**
	 * @param commentaireUtile the commentaireUtile to set
	 */
	public void setCommentaireUtile(String commentaireUtile) {
		CommentaireUtile = commentaireUtile;
	}
	
	/**
	 * Concatenates the two fields of PrixCoutant (if second field is nonzero)
	 * as a string
	 * @return	a String representing concatenation of the two PrixCoutant fields
	 */
	public String prixCoutantToString(){		
		DecimalFormat df = new DecimalFormat("##0.00");	    
		if(this.PrixCoutant2 > Double.MIN_VALUE)
			return df.format(PrixCoutant1) + " & " + df.format(PrixCoutant2);
		else if(this.PrixCoutant1 > Double.MIN_VALUE)
			return df.format(PrixCoutant1);
		else
			return "";		
	}
	
	/**
	 * Concatenates both Epaisseur and EpaisseurUnites fields as a string
	 * @return	a String representing Epaisseur and its units
	 */
	public String epaisseurToString(){	
		DecimalFormat df = new DecimalFormat("##0.00");
		if(this.Epaisseur > Double.MIN_VALUE)
			return df.format(Epaisseur) + " " + this.EpaisseurUnites;
		else
			return "";
	}
	
	/**
	 * String representation of isEnStock()
	 * @return   String : "X" if isEnStock() is true; empty String otherwise
	 */
	public String enStockToString(){
		if (this.EnStock)
			return "X";
		else 
			return "";
	}
	
	

}
