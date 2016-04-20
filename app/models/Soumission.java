package models;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;

import play.data.format.Formats;

/**
 * Soumission	classe modèle représentant la Soumission
 * @author Eva
 *
 */
public class Soumission {
/****** Object:  Table dbo.Soumission    Script Date: 11/10/2014 09:57:58 ******/

	private int noSoumission;  //IDENTITY(1,1) NOT NULL,
	private int suite;
	private String version;
	private String clientSoum;	// nvarchar(50) NOT NULL,
	private String projet;
	private String nomVendeur;
	private String noVendeur;	
	
	@Formats.DateTime(pattern= "dd/MM/yyyy")
	private Date dateEntre;
	
	private String DateLivraison;			// nvarchar(25) NULL,
	@Formats.DateTime(pattern= "dd/MM/yyyy")
	private Date dateDebutEstimation;	// datetime NULL,
		
	double commission;			// real NOT NULL,
	private BigDecimal prix; 	//money NOT NULL,
	
	private String geniusNoClient;
	private String contact;
//	private String ClientAdresse;	// nvarchar(100) NULL,
//	private String ClientTelephone;	// nvarchar(100) NULL,
	private String clientTelephoneContact;	// nvarchar(100) NULL,
//	private String ClientFax;		// nvarchar(100) NULL,
//	private String ClientVille;		// nvarchar(100) NULL,
//	private String ClientFactureA;	// nvarchar(100) NULL,
	private String clientEmail;		// nvarchar(100) NULL,
//	private String ClientCP;		// nvarchar(100) NULL,
//	private String ClientAPP;		// nvarchar(100) NULL,
//	private String ClientProvince;	// varchar(50) NULL,
//	private String ClientPays;		// varchar(200) NULL,
	
//	LIVRLieu nvarchar(100) NULL,
//	LIVRAdresse nvarchar(100) NULL,
//	LIVRContact nvarchar(100) NULL,
//	LIVRTelephone nvarchar(100) NULL,
//	LIVRVille nvarchar(100) NULL,
//	LIVRCP nvarchar(100) NULL,
//	LIVRProvince varchar(50) NULL,
//	LIVRPays varchar(200) NULL,
//	LIVRProvinceCode varchar(15) NULL,
	
//	FACTAdresse nvarchar(100) NULL,
//	FACTTelephone nvarchar(20) NULL,
//	FACTVille nvarchar(100) NULL,
//	FACTCP nvarchar(100) NULL,
//	FACTContact nvarchar(100) NULL,	
//	FACTPays varchar(200) NULL,
//	FACTProvince varchar(50) NULL,
//	FACTProvinceCode varchar(15) NULL,
	
	
	private int statut = 0;				// tinyint NULL,
	private int noDossier;			// int NULL,
	private String banniereClient;	// nvarchar(50) NOT NULL,
	private String critere;			// nvarchar(100) NULL,		
	
	// Object liés à une Soumission
	public List<Item> items;
	public Rep rep;
	public Client client;
	
	// Autres champs
	private String texteStatut;		// nécessaire pour la vue liste des soumissions
	
	//*******************************************
	// Utility functions
	public String getContactOrValue(String value){
		if(contact == null || contact.isEmpty())
			return value;
		else
			return contact;
	}
	
	public String getClientTelephoneContactOrValue(String value){
		if(clientTelephoneContact == null || clientTelephoneContact.isEmpty())
			return value;
		else
			return clientTelephoneContact;
	}
	
	public String getClientEmailOrValue(String value){
		if(null == clientEmail || clientEmail.isEmpty())
			return value;
		else
			return clientEmail;
	}
	
	public String getFormattedPrix(){
		if(prix == null){
			return "";
		}
		else{
			DecimalFormat df = new DecimalFormat("### ### ###.00");	    
			return df.format(prix.doubleValue());
		}
	}
		
	/**
	 * Get the list of all sortable field names for a Soumission
	 * @return a java.util.List of field names as strings
	 */
	public static List<String> getSortableFieldNames(){
		return Arrays.asList("suite","noDossier", "critere", "banniereClient", "clientSoum", "projet", "texteStatut");
	}
	
	/**
	 * Get the DB field name for a sortable field
	 * @param field
	 * @return
	 */
	public static String getSortableDBFieldNameFor(String field) throws IllegalArgumentException{
		if(getSortableFieldNames().indexOf(field)< 0){
			throw new IllegalArgumentException("Champ invalide");
		}
		if(field.equals("texteStatut")){
			return "Statut";
		}
		else{
			return (field.substring(0, 1).toUpperCase() + field.substring(1));
		}
	}
	/*****************************************************/
	/*                  GETTERS AND SETTERS              */
	/**
	 * @return the noSoumission
	 */
	public int getNoSoumission() {
		return noSoumission;
	}
	/**
	 * @param noSoumission the noSoumission to set
	 */
	public void setNoSoumission(int noSoumission) {
		this.noSoumission = noSoumission;
	}
	/**
	 * @return the suite
	 */
	public int getSuite() {
		return suite;
	}
	/**
	 * @param suite the suite to set
	 */
	public void setSuite(int suite) {
		this.suite = suite;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the client
	 */
	public String getClientSoum() {
		return clientSoum;
	}
	/**
	 * @param client the client to set
	 */
	public void setClientSoum(String clientSoum) {
		this.clientSoum = clientSoum;
	}
	/**
	 * @return the projet
	 */
	public String getProjet() {
		return projet;
	}
	/**
	 * @param projet the projet to set
	 */
	public void setProjet(String projet) {
		this.projet = projet;
	}  // nvarchar(100) NOT NULL,
	/**
	 * @return the nomVendeur
	 */
	public String getNomVendeur() {
		return nomVendeur;
	}
	/**
	 * @param nomVendeur the nomVendeur to set
	 */
	public void setNomVendeur(String nomVendeur) {
		this.nomVendeur = nomVendeur;
	}
	/**
	 * @return the dateLivraison
	 */
	public String getDateLivraison() {
		return DateLivraison;
	}
	/**
	 * @param dateLivraison the dateLivraison to set
	 */
	public void setDateLivraison(String dateLivraison) {
		this.DateLivraison = dateLivraison;
	}
	/**
	 * @return the dateDebutEstimation
	 */
	public Date getDateDebutEstimation() {
		return dateDebutEstimation;
	}
	/**
	 * @param dateDebutEstimation the dateDebutEstimation to set
	 */
	public void setDateDebutEstimation(Date dateDebutEstimation) {
		this.dateDebutEstimation = dateDebutEstimation;
	}
	/**
	 * @return the commission
	 */
	public double getCommission() {
		return commission;
	}
	/**
	 * @param commission the commission to set
	 */
	public void setCommission(double commission) {
		this.commission = commission;
	}
	/**
	 * @return the prix
	 */
	public BigDecimal getPrix() {
		return prix;
	}
	/**
	 * @param prix the prix to set
	 */
	public void setPrix(BigDecimal prix) {
		this.prix = prix;
	}
	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	/**
	 * @return the clientTelephoneContact
	 */
	public String getClientTelephoneContact() {
		return clientTelephoneContact;
	}
	/**
	 * @param clientTelephoneContact the clientTelephoneContact to set
	 */
	public void setClientTelephoneContact(String clientTelephoneContact) {
		this.clientTelephoneContact = clientTelephoneContact;
	}
	/**
	 * @return the clientEmail
	 */
	public String getClientEmail() {
		return clientEmail;
	}
	/**
	 * @param clientEmail the clientEmail to set
	 */
	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}
	/**
	 * @return the noVendeur
	 */
	public String getNoVendeur() {
		return noVendeur;
	}
	/**
	 * @param noVendeur the noVendeur to set
	 */
	public void setNoVendeur(String noVendeur) {
		this.noVendeur = noVendeur;
	}
	/**
	 * @return the geniusNoClient
	 */
	public String getGeniusNoClient() {
		return geniusNoClient;
	}
	/**
	 * @param geniusNoClient the geniusNoClient to set
	 */
	public void setGeniusNoClient(String geniusNoClient) {
		this.geniusNoClient = geniusNoClient;
	}
	/**
	 * @return the dateEntre
	 */
	public Date getDateEntre() {
		return dateEntre;
	}
	/**
	 * @param dateEntre the dateEntre to set
	 */
	public void setDateEntre(Date dateEntre) {
		this.dateEntre = dateEntre;
	}

	public int getStatut() {
		return statut;
	}

	public void setStatut(int statut) {
		this.statut = statut;
	}

	public int getNoDossier() {
		return noDossier;
	}

	public void setNoDossier(int noDossier) {
		this.noDossier = noDossier;
	}

	public String getBanniereClient() {
		return banniereClient;
	}

	public void setBanniereClient(String banniereClient) {
		this.banniereClient = banniereClient;
	}

	public String getCritere() {
		return critere;
	}

	public void setCritere(String critere) {
		this.critere = critere;
	}

	public String getTexteStatut() {
		return texteStatut;
	}

	public void setTexteStatut(String texteStatut) {
		this.texteStatut = texteStatut;
	}

	
	
	
	/*****************************************************/
	
	/*
	NoDossier int NULL,
	NoDossierRef nvarchar(50) NULL,
	Critere nvarchar(100) NULL,
	dateEntre datetime NULL,
	nomVendeur nvarchar(50) NOT NULL,
	
	contact nvarchar(50) NULL,
	Quantite smallint NOT NULL,
	Description nvarchar(max) NULL,
	commission real NOT NULL,
	prix money NOT NULL,
	Copie int NULL,
	Livraison tinyint NULL,
	ClientAdresse nvarchar(100) NULL,
	ClientTelephone nvarchar(100) NULL,
	clientTelephoneContact nvarchar(100) NULL,
	ClientFax nvarchar(100) NULL,
	ClientVille nvarchar(100) NULL,
	ClientFactureA nvarchar(100) NULL,
	clientEmail nvarchar(100) NULL,
	ClientCP nvarchar(100) NULL,
	ClientAPP nvarchar(100) NULL,
	ClientProvince varchar(50) NULL,
	Statut tinyint NULL,
	DateDemandeDessin datetime NULL,
	DateDessinNecessaire datetime NULL,
	DateDessinTermine datetime NULL,
	DateProductionDebut datetime NULL,
	DateProductionNecessaire datetime NULL,
	DateProductionTermine datetime NULL,
	DatePretFacturation datetime NULL,
	DateFacturationComplete datetime NULL,
	NouveauClient bit NULL,
	PayableReception bit NULL,
	InternetCDZIP tinyint NULL,
	NumSerBannAutre tinyint NULL,
	Autre nvarchar(50) NULL,
	Permissive tinyint NULL,
	LIVRAdresse nvarchar(100) NULL,
	LIVRContact nvarchar(100) NULL,
	LIVRTelephone nvarchar(100) NULL,
	LIVRVille nvarchar(100) NULL,
	LIVRCP nvarchar(100) NULL,
	Extra1 nvarchar(100) NULL,
	Extra$1 money NOT NULL,
	Extra2 nvarchar(100) NULL,
	Extra$2 money NOT NULL,
	Extra3 nvarchar(100) NULL,
	Extra$3 money NOT NULL,
	Extra4 nvarchar(100) NULL,
	Extra$4 money NOT NULL,
	Extra5 nvarchar(100) NULL,
	Extra$5 money NOT NULL,
	DateLivraison nvarchar(25) NULL,
	NoMagasin nvarchar(25) NULL,
	DateOuverture datetime NULL,
	Entrepreneur nvarchar(50) NULL,
	Affichage tinyint NULL,
	NoBonCommande nvarchar(25) NULL,
	ChargeProjetClient nvarchar(50) NULL,
	TelChargeProjetClient nvarchar(20) NULL,
	DateDebutInstallation datetime NULL,
	DateFinInstallation datetime NULL,
	BanniereClient nvarchar(50) NOT NULL,
	NouvelleSoumission int NULL,
	LOGO nvarchar(255) NULL,
	NoteAuClient nvarchar(100) NULL,
	MessageEnTete nvarchar(50) NULL,
	FACTAdresse nvarchar(100) NULL,
	FACTVille nvarchar(100) NULL,
	FACTCP nvarchar(100) NULL,
	FACTContact nvarchar(100) NULL,
	NoteInstallation nvarchar(255) NULL,
	LIVRLieu nvarchar(100) NULL,
	DateDemande datetime NULL,
	dateDebutEstimation datetime NULL,
	DateFinEstimation datetime NULL,
	DateRemiseClient datetime NULL,
	DateAcceptationRefus datetime NULL,
	AccepterRefuser tinyint NULL,
	RaisonRefuser nvarchar(max) NULL,
	Flex tinyint NULL,
	NoteInerne nvarchar(max) NULL,
	FACTTelephone nvarchar(20) NULL,
	FACTTelecopie nvarchar(20) NULL,
	geniusNoClient nvarchar(10) NULL,
	GeniusNoCom nvarchar(50) NULL,
	noVendeur nvarchar(50) NULL,
	FlagHeader tinyint NULL,
	SSMA_TimeStamp timestamp NOT NULL,
	ClientPays varchar(200) NULL,
	FACTPays varchar(200) NULL,
	LIVRPays varchar(200) NULL,
	FACTProvince varchar(50) NULL,
	LIVRProvince varchar(50) NULL,
	clientSoum varchar(64) NULL,
	LIVRProvinceCode varchar(15) NULL,
	FACTProvinceCode varchar(15) NULL,
*/
}