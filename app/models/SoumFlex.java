package models;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import play.data.format.*;
import play.data.validation.*;

/**
 * @author Eva
 * Model for the form Soumission Rapide Flexible
 */
public class SoumFlex {
	
	public static final double MAX_SALE_AMOUNT = 3000.00;
	
	@Constraints.Required(message="Champ requis")
	private String inputProjet; 
	@Constraints.Required(message="Champ requis")
	private String selectRepresentant; 
	@Constraints.Required(message="Champ requis")
	private String selectClient; 
	private String inputContactNom = ""; 
	
	@Constraints.Pattern(value = "^[1-9][0-9]{2}[-|.| ][0-9]{3}[-|.| ][0-9]{4}$",message="Format 10 chiffres")
	private String inputContactTel = ""; 
	
	@Constraints.Pattern(value = "^[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,3}$", message="Format xxx@xx.xx")
	private String inputContactEmail = ""; 
	
	@Formats.DateTime(pattern= "dd/MM/yyyy")
	private Date inputDate;	// -> (23/03/2015),
	
	@Formats.DateTime(pattern= "dd/MM/yyyy")
	private Date inputDateEstimation; // -> (23/03/2015), 		
	
	private int inputCommission = 2; 
	private int margeGlobale;	
	
	// Liste des items
	public List<SoumFlexItem> items;
	
	public SoumFlex(){
		inputDate = new Date();
		inputDateEstimation = new Date();
	}
		
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {		
		String output = "Projet: " + inputProjet + ", " +
			   "Rep: " + selectRepresentant + ", " +
			   "Client: " + selectClient + ", " +
			   "ContactNom: " + inputContactNom + ", " + 
			   "ContactEmail: " + inputContactEmail+ ", " +
			   "ContactTel: " + inputContactTel + ", " +
			   "Date requise: " + inputDate.toString() + ", " +
			   "Date estimation: " + inputDateEstimation.toString() + ", ";
				if(items.size() > 0){
					output += "Qte: " + items.get(0).getQte() + ", " +
				   "Haut: "  + items.get(0).getHaut() + ", " +
				   "Larg: " + items.get(0).getLarg() + ", " +
				   "Perte: "+ items.get(0).getPerte() + ", " +
				   "RV: " + items.get(0).getRv() + ", " +
				   "MatType: " + items.get(0).getMatType() + ", " +
				   "MatCat: " + items.get(0).getMatCat() + ", " +
				   "MatProd: " + items.get(0).getMatProd() + ", " +
				   "NbVisuels: "+ items.get(0).getInfoNbVisuels() + ", " +
				   "MOtemps: "+ items.get(0).getMoNbrHeure() + " h, ";
				}
				output += "Commission: "+ inputCommission;
		
		return output;
	}
	/**
	 * @return the inputProjet
	 */
	public String getInputProjet() {
		return inputProjet;
	}
	/**
	 * @param inputProjet the inputProjet to set
	 */
	public void setInputProjet(String inputProjet) {
		this.inputProjet = inputProjet;
	}
	/**
	 * @return the selectRepresentant
	 */
	public String getSelectRepresentant() {
		return selectRepresentant;
	}
	/**
	 * @param selectRepresentant the selectRepresentant to set
	 */
	public void setSelectRepresentant(String selectRepresentant) {
		this.selectRepresentant = selectRepresentant;
	}
	/**
	 * @return the selectClient
	 */
	public String getSelectClient() {
		return selectClient;
	}
	/**
	 * @param selectClient the selectClient to set
	 */
	public void setSelectClient(String selectClient) {
		this.selectClient = selectClient;
	}
	/**
	 * @return the inputContactNom
	 */
	public String getInputContactNom() {
		return inputContactNom;
	}
	/**
	 * @param inputContactNom the inputContactNom to set
	 */
	public void setInputContactNom(String inputContactNom) {
		this.inputContactNom = inputContactNom;
	}
	/**
	 * @return the inputContactTel
	 */
	public String getInputContactTel() {
		return inputContactTel;
	}
	/**
	 * @param inputContactTel the inputContactTel to set
	 */
	public void setInputContactTel(String inputContactTel) {
		this.inputContactTel = inputContactTel;
	}
	/**
	 * @return the inputContactEmail
	 */
	public String getInputContactEmail() {
		return inputContactEmail;
	}
	/**
	 * @param inputContactEmail the inputContactEmail to set
	 */
	public void setInputContactEmail(String inputContactEmail) {
		this.inputContactEmail = inputContactEmail;
	}
	/**
	 * @return the inputDate
	 */
	public Date getInputDate() {
		return inputDate;
	}
	/**
	 * @param inputDate the inputDate to set
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = (Date)inputDate.clone();
	}
	/**
	 * @return the inputDateEstimation
	 */
	public Date getInputDateEstimation() {
		return inputDateEstimation;
	}
	/**
	 * @param inputDateEstimation the inputDateEstimation to set
	 */
	public void setInputDateEstimation(Date inputDateEstimation) {
		this.inputDateEstimation = (Date)inputDateEstimation.clone();
	}
	
	/**
	 * @return the inputCommission
	 */
	public int getInputCommission() {
		return inputCommission;
	}
	/**
	 * @param inputCommission the inputCommission to set
	 */
	public void setInputCommission(int inputCommission) {
		this.inputCommission = inputCommission;
	}	

	
	/**
	 * @return the margeGlobale
	 */
	public int getMargeGlobale() {
		return margeGlobale;
	}

	/**
	 * @param margeGlobale the margeGlobale to set
	 */
	public void setMargeGlobale(int margeGlobale) {
		this.margeGlobale = margeGlobale;
	}
	
	public double getTotalVente(){
		Optional<Double> total = this.items.stream()
										   .map(i -> i.getMontantVente())
										   .reduce((a,b) -> a + b);
		
		return total.isPresent() ? total.get() : 0.0;
	}
	
			
}

