package models.app;

import java.util.Date;

import play.data.format.Formats;
import play.data.validation.Constraints;

/**
 * This class models the new soumission entete structure
 * @author Eva
 *
 */
public class AppSoumEntete {
		
	private int noSoumission;	// foreign key

	@Constraints.MaxLength(100)
	private String critere;
			
	@Formats.DateTime(pattern= "dd-MM-yyyy")
	private Date dateEntre;
	
	// On ne peut mettre un pattern sur ce champ car il peut valoir un texte
	@Constraints.MaxLength(25)	
	private String dateLivraison = "À confirmer";
	
	@Constraints.Required
	@Constraints.MaxLength(100)
	private String projet = "À compléter";
	
	@Constraints.MaxLength(64)
	private String clientSoum = ".";
	
	@Constraints.MaxLength(10)
	private String geniusNoClient = "TEST";	
	
	@Constraints.Required
	@Constraints.MaxLength(50)
	private String banniereClient = "À compléter";
	
	@Constraints.Required
	@Constraints.MaxLength(50)
	private String noVendeur = "1";
	
	@Constraints.MaxLength(50)
	private String nomVendeur = "Roger Perron";
	
	private String description;
	
	@Constraints.MaxLength(50)
	private String contact;		
	
	@Constraints.MaxLength(100)
	private String clientAdresse;
	
	@Constraints.MaxLength(100)	
	@Constraints.Pattern(value = "([1]?[-|.| ]?[(]?[1-9][0-9]{2}[)]?[-|.| ][0-9]{3}[-|.| ][0-9]{4})+.*",
	 					 message="Format telephone incorrect.")
	private String clientTelephone;
	// value = "([1]?[-|.| ]?[(]?[1-9][0-9]{2}[)]?[-|.| ][0-9]{3}[-|.| ][0-9]{4}\\s*(#|ext|EXT|poste|POSTE|Poste)?.*)+",
//	@Constraints.Pattern(value = "^[1-9][0-9]{2}[-|.| ][0-9]{3}[-|.| ][0-9]{4}\\s*(#|ext|poste)?\\s*[.]?\\s*[0-9]{0,6}",
	
	@Constraints.MaxLength(100)
	@Constraints.Pattern(value = "([1]?[-|.| ]?[(]?[1-9][0-9]{2}[)]?[-|.| ][0-9]{3}[-|.| ][0-9]{4})+.*",
	 					 message="Format telephone incorrect.")
	private String clientTelephoneContact;
	
	@Constraints.MaxLength(100)
	private String clientFax;
	@Constraints.MaxLength(100)
	private String clientVille;
	@Constraints.MaxLength(100)
	private String clientFactureA;
	
	@Constraints.MaxLength(100)
	@Constraints.Pattern(value = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,4}$", message="Format xxx@xx.xx")
	private String clientEmail;
	
	@Constraints.MaxLength(100)
	private String clientCP;
	@Constraints.MaxLength(100)
	private String clientAPP;
	@Constraints.MaxLength(50)
	private String clientProvince;
	@Constraints.MaxLength(200)
	private String clientPays;
	
	public AppSoumEntete(){
		
	};
	
	public AppSoumEntete(AppSoumEntete entete){
		this.noSoumission = 0;		// because the copy is still a new soum
		this.banniereClient = entete.banniereClient;
		this.clientAdresse = entete.clientAdresse;
		this.clientAPP = entete.clientAPP;
		this.clientCP = entete.clientCP;		
		this.clientEmail = entete.clientEmail;
		this.clientFactureA = entete.clientFactureA;
		this.clientFax = entete.clientFax;
		this.clientPays = entete.clientPays;
		this.clientProvince = entete.clientProvince;
		this.clientSoum = entete.clientSoum;
		this.clientTelephone = entete.clientTelephone;
		this.clientTelephoneContact = entete.clientTelephoneContact;
		this.clientVille = entete.clientVille;
		this.contact = entete.contact;
		this.critere = entete.critere;
		this.dateEntre = new Date();	// because copy is a new soum
		this.dateLivraison = "À confirmer";	// as new soum, to be determined
		this.description = entete.description;
		this.geniusNoClient = entete.geniusNoClient;
		this.nomVendeur = entete.nomVendeur;
		this.noVendeur = entete.noVendeur;
		this.projet = entete.projet;
	}
	
	
	public String toString(){
		String str = "NoSoumission = " + noSoumission + "  ";
		str += "critere = " + (critere == null ? "NULL" : critere) + "  ";
		str += "dateEntre = " + (dateEntre == null ? "NULL" : dateEntre.toString()) + "  "; 
		str += "banniere Client = " + (banniereClient == null ? "NULL" : banniereClient) + "  ";
		str += "dateLivraison = " + dateLivraison;
		return str;
		
	}
	
	public int getNoSoumission() {
		return noSoumission;
	}
	public void setNoSoumission(int noSoumission) {
		this.noSoumission = noSoumission;
	}
	
	public String getCritere() {
		return critere;
	}
	public void setCritere(String critere) {
		this.critere = critere;
	}
	public Date getDateEntre() {
		return dateEntre;
	}
	public void setDateEntre(Date dateEntre) {
		if(dateEntre != null){
			this.dateEntre = (Date)dateEntre.clone();
		}
	}
	public String getDateLivraison() {
		return dateLivraison;
	}
	public void setDateLivraison(String dateLivraison) {
		this.dateLivraison = dateLivraison;
	}
	public String getProjet() {
		return projet;
	}
	public void setProjet(String projet) {
		this.projet = projet;
	}
	public String getClientSoum() {
		return clientSoum;
	}
	public void setClientSoum(String clientSoum) {
		this.clientSoum = clientSoum;
	}
	public String getGeniusNoClient() {
		return geniusNoClient;
	}
	public void setGeniusNoClient(String geniusNoClient) {
		this.geniusNoClient = geniusNoClient;
	}
	public String getBanniereClient() {
		return banniereClient;
	}
	public void setBanniereClient(String banniereClient) {
		this.banniereClient = banniereClient;
	}
	public String getNoVendeur() {
		return noVendeur;
	}
	public void setNoVendeur(String noVendeur) {
		this.noVendeur = noVendeur;
	}
	public String getNomVendeur() {
		return nomVendeur;
	}
	public void setNomVendeur(String nomVendeur) {
		this.nomVendeur = nomVendeur;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getClientAdresse() {
		return clientAdresse;
	}
	public void setClientAdresse(String clientAdresse) {
		this.clientAdresse = clientAdresse;
	}
	public String getClientTelephone() {
		return clientTelephone;
	}
	public void setClientTelephone(String clientTelephone) {
		this.clientTelephone = clientTelephone;
	}
	public String getClientTelephoneContact() {
		return clientTelephoneContact;
	}
	public void setClientTelephoneContact(String clientTelephoneContact) {
		this.clientTelephoneContact = clientTelephoneContact;
	}
	public String getClientFax() {
		return clientFax;
	}
	public void setClientFax(String clientFax) {
		this.clientFax = clientFax;
	}
	public String getClientVille() {
		return clientVille;
	}
	public void setClientVille(String clientVille) {
		this.clientVille = clientVille;
	}
	public String getClientFactureA() {
		return clientFactureA;
	}
	public void setClientFactureA(String clientFactureA) {
		this.clientFactureA = clientFactureA;
	}
	public String getClientEmail() {
		return clientEmail;
	}
	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}
	public String getClientCP() {
		return clientCP;
	}
	public void setClientCP(String clientCP) {
		this.clientCP = clientCP;
	}
	public String getClientAPP() {
		return clientAPP;
	}
	public void setClientAPP(String clientAPP) {
		this.clientAPP = clientAPP;
	}
	public String getClientProvince() {
		return clientProvince;
	}
	public void setClientProvince(String clientProvince) {
		this.clientProvince = clientProvince;
	}
	public String getClientPays() {
		return clientPays;
	}
	public void setClientPays(String clientPays) {
		this.clientPays = clientPays;
	}
	
	
	
}
