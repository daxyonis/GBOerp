package models;

import play.data.validation.Constraints;

/**
 * @author Eva
 * Model for the items of the form Soumission Rapide Flexible
 */
public class SoumFlexItem {

	@Constraints.Required(message="Champ requis")
	private int qte = 1;  
	
	@Constraints.Required(message="Champ requis")
	private double haut; 	// en PO
	
	private int perte; 
	
	@Constraints.Required(message="Champ requis")
	private double larg;	// en PO
	
	private String rv;
	
	@Constraints.Required(message="Champ requis")
	private double montantVente;
	
	private String matType = "Flexible"; 
	
	@Constraints.Required(message="Champ requis")
	private String matCat; 
	
	@Constraints.Min(value = 1001)
	@Constraints.Required(message="Champ requis")
	private int matProd;
	
	private String impSelect;
	private int impTempsManip;	// min
	private int infoNbVisuels = 1; 
	private double infoTaux;
	private double infoNbrHeure; 
	private double moTaux;
	private double moNbrHeure;
	private double moCoutSac;
	private int finiSelectType = 0; // id de finition
	private String finiSelectCote="";
	private double finiPiTraites;
	
	private int lamSelectType = 0; // id de laminage
    private double lamTaux;			// taux de mo laminage
    private double lamTempsHr;    	// temps en hres laminage
    
//	private int accSelectProd0 = 0;	// id de l'accessoire
//	private int accQte;	// qte de l'accessoire
//	private double accMarge;	// marge de l'accessoire
//	private double accPrixTotal;// prix total accessoire (coutant * qte / (1 - 0.01 * marge))
    
    
	public int getQte() {
		return qte;
	}
	public void setQte(int qte) {
		this.qte = qte;
	}
	public double getHaut() {
		return haut;
	}
	public void setHaut(double haut) {
		this.haut = haut;
	}
	public int getPerte() {
		return perte;
	}
	public void setPerte(int perte) {
		this.perte = perte;
	}
	public double getLarg() {
		return larg;
	}
	public void setLarg(double larg) {
		this.larg = larg;
	}
	
	
	public double getItemLarg(String unit){
		return convertPOtoUnit(larg, unit);
	}
	
	public double getItemHaut(String unit){
		return convertPOtoUnit(haut, unit);
	}

	private double convertPOtoUnit(double val, String finalUnit){
		switch(finalUnit){
			case "PI":
			case "PI2":
				return val / 12.0;
			default:
				return val;
		}
	}

	
	public String getRv() {
		return rv;
	}
	public void setRv(String rv) {
		this.rv = rv;
	}
	public String getMatType() {
		return matType;
	}
	public void setMatType(String matType) {
		this.matType = matType;
	}
	public String getMatCat() {
		return matCat;
	}
	public void setMatCat(String matCat) {
		this.matCat = matCat;
	}
	public int getMatProd() {
		return matProd;
	}
	public void setMatProd(int matProd) {
		this.matProd = matProd;
	}
	public String getImpSelect() {
		return impSelect;
	}
	public void setImpSelect(String impSelect) {
		this.impSelect = impSelect;
	}
	public int getImpTempsManip() {
		return impTempsManip;
	}
	public void setImpTempsManip(int impTempsManip) {
		this.impTempsManip = impTempsManip;
	}
	public int getInfoNbVisuels() {
		return infoNbVisuels;
	}
	public void setInfoNbVisuels(int infoNbVisuels) {
		this.infoNbVisuels = infoNbVisuels;
	}
	public double getInfoTaux() {
		return infoTaux;
	}
	public void setInfoTaux(double infoTaux) {
		this.infoTaux = infoTaux;
	}
	public double getInfoNbrHeure() {
		return infoNbrHeure;
	}
	public void setInfoNbrHeure(double infoNbrHeure) {
		this.infoNbrHeure = infoNbrHeure;
	}
	public double getMoTaux() {
		return moTaux;
	}
	public void setMoTaux(double moTaux) {
		this.moTaux = moTaux;
	}
	public double getMoNbrHeure() {
		return moNbrHeure;
	}
	public void setMoNbrHeure(double moNbrHeure) {
		this.moNbrHeure = moNbrHeure;
	}
	public double getMoCoutSac() {
		return moCoutSac;
	}
	public void setMoCoutSac(double moCoutSac) {
		this.moCoutSac = moCoutSac;
	}
	public int getFiniSelectType() {
		return finiSelectType;
	}
	public void setFiniSelectType(int finiSelectType) {
		this.finiSelectType = finiSelectType;
	}
	public String getFiniSelectCote() {
		return finiSelectCote;
	}
	public void setFiniSelectCote(String finiSelectCote) {
		this.finiSelectCote = finiSelectCote;
	}
	public double getFiniPiTraites() {
		return finiPiTraites;
	}
	public void setFiniPiTraites(double finiPiTraites) {
		this.finiPiTraites = finiPiTraites;
	}
	public int getLamSelectType() {
		return lamSelectType;
	}
	public void setLamSelectType(int lamSelectType) {
		this.lamSelectType = lamSelectType;
	}
	public double getLamTaux() {
		return lamTaux;
	}
	public void setLamTaux(double lamTaux) {
		this.lamTaux = lamTaux;
	}
	public double getLamTempsHr() {
		return lamTempsHr;
	}
	public void setLamTempsHr(double lamTempsHr) {
		this.lamTempsHr = lamTempsHr;
	}	
	public double getMontantVente() {
		return montantVente;
	}	
	public void setMontantVente(double montantVente) {
		this.montantVente = montantVente;
	}
	  
	
}
