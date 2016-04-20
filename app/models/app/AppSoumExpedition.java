package models.app;

import java.util.Date;

import play.data.validation.Constraints;

public class AppSoumExpedition {	
	
	private Date dateOuverture;
	
	@Constraints.MaxLength(25)
	private String noBonCommande;
	
	public enum EnumLivraison{
		AUTRE,
	    INSTALLATION,
	    EXPÉDITION_BO,
	    COURRIER,
	    COURRIER_CLIENT,
	    CUEILLETTE
	}
	
	public enum EnumMedium{
		AUCUN,
		FTP,
		EMAIL,
		CD_DVD,
		CLIENT_INTERNE
	}
	
	boolean nouveauClient = false;
	boolean payableReception = false;
	private EnumMedium internetCDZIP= EnumMedium.AUCUN;
	private EnumLivraison livraison = EnumLivraison.AUTRE;	
	
	@Constraints.MaxLength(50)
	private String autre;	
	
	@Constraints.MaxLength(100)
	private String noteAuClient;
	private String noteInstallation;
		
	@Constraints.MaxLength(100)
	private String lIVRLieu;	
	@Constraints.MaxLength(100)
	private String lIVRAdresse;
	@Constraints.MaxLength(100)
	private String lIVRContact;
	@Constraints.MaxLength(100)
	private String lIVRTelephone;
	@Constraints.MaxLength(100)
	private String lIVRVille;
	@Constraints.MaxLength(100)
	private String lIVRCP;
	@Constraints.MaxLength(50)
	private String lIVRProvince;
	@Constraints.MaxLength(15)
	private String lIVRProvinceCode;
	@Constraints.MaxLength(200)
	private String lIVRPays;
	
	@Constraints.MaxLength(100)
	private String extra1;
	private double extra$1 = 0.0;
	@Constraints.MaxLength(100)
	private String extra2;
	private double extra$2 = 0.0;
	@Constraints.MaxLength(100)
	private String extra3;
	private double extra$3 = 0.0;
	@Constraints.MaxLength(100)
	private String extra4;
	private double extra$4 = 0.0;
	@Constraints.MaxLength(100)
	private String extra5;
	private double extra$5 = 0.0;				
	
	@Constraints.MaxLength(100)
	private String fACTAdresse;
	@Constraints.MaxLength(100)
	private String fACTVille;
	@Constraints.MaxLength(100)
	private String fACTCP;
	@Constraints.MaxLength(50)
	private String fACTProvince;	
	@Constraints.MaxLength(15)
	private String fACTProvinceCode;
	@Constraints.MaxLength(200)
	private String FACTPays;
	@Constraints.MaxLength(100)
	private String FACTContact;
	@Constraints.MaxLength(20)
	private String FACTTelephone;
	@Constraints.MaxLength(20)
	private String FACTTelecopie;
	public Date getDateOuverture() {
		return dateOuverture;
	}
	public void setDateOuverture(Date dateOuverture) {
		this.dateOuverture = dateOuverture;
	}
	public String getNoBonCommande() {
		return noBonCommande;
	}
	public void setNoBonCommande(String noBonCommande) {
		this.noBonCommande = noBonCommande;
	}
	public boolean isNouveauClient() {
		return nouveauClient;
	}
	public void setNouveauClient(boolean nouveauClient) {
		this.nouveauClient = nouveauClient;
	}
	public boolean isPayableReception() {
		return payableReception;
	}
	public void setPayableReception(boolean payableReception) {
		this.payableReception = payableReception;
	}
	public EnumMedium getInternetCDZIP() {
		return internetCDZIP;
	}
	public void setInternetCDZIP(EnumMedium internetCDZIP) {
		this.internetCDZIP = internetCDZIP;
	}
	public EnumLivraison getLivraison() {
		return livraison;
	}
	public void setLivraison(EnumLivraison livraison) {
		this.livraison = livraison;
	}
	public String getAutre() {
		return autre;
	}
	public void setAutre(String autre) {
		this.autre = autre;
	}
	public String getNoteAuClient() {
		return noteAuClient;
	}
	public void setNoteAuClient(String noteAuClient) {
		this.noteAuClient = noteAuClient;
	}
	public String getNoteInstallation() {
		return noteInstallation;
	}
	public void setNoteInstallation(String noteInstallation) {
		this.noteInstallation = noteInstallation;
	}
	public String getlIVRLieu() {
		return lIVRLieu;
	}
	public void setlIVRLieu(String lIVRLieu) {
		this.lIVRLieu = lIVRLieu;
	}
	public String getlIVRAdresse() {
		return lIVRAdresse;
	}
	public void setlIVRAdresse(String lIVRAdresse) {
		this.lIVRAdresse = lIVRAdresse;
	}
	public String getlIVRContact() {
		return lIVRContact;
	}
	public void setlIVRContact(String lIVRContact) {
		this.lIVRContact = lIVRContact;
	}
	public String getlIVRTelephone() {
		return lIVRTelephone;
	}
	public void setlIVRTelephone(String lIVRTelephone) {
		this.lIVRTelephone = lIVRTelephone;
	}
	public String getlIVRVille() {
		return lIVRVille;
	}
	public void setlIVRVille(String lIVRVille) {
		this.lIVRVille = lIVRVille;
	}
	public String getlIVRCP() {
		return lIVRCP;
	}
	public void setlIVRCP(String lIVRCP) {
		this.lIVRCP = lIVRCP;
	}
	public String getlIVRProvince() {
		return lIVRProvince;
	}
	public void setlIVRProvince(String lIVRProvince) {
		this.lIVRProvince = lIVRProvince;
	}
	public String getlIVRProvinceCode() {
		return lIVRProvinceCode;
	}
	public void setlIVRProvinceCode(String lIVRProvinceCode) {
		this.lIVRProvinceCode = lIVRProvinceCode;
	}
	public String getlIVRPays() {
		return lIVRPays;
	}
	public void setlIVRPays(String lIVRPays) {
		this.lIVRPays = lIVRPays;
	}
	public String getExtra1() {
		return extra1;
	}
	public void setExtra1(String extra1) {
		this.extra1 = extra1;
	}
	public double getExtra$1() {
		return extra$1;
	}
	public void setExtra$1(double extra$1) {
		this.extra$1 = extra$1;
	}
	public String getExtra2() {
		return extra2;
	}
	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}
	public double getExtra$2() {
		return extra$2;
	}
	public void setExtra$2(double extra$2) {
		this.extra$2 = extra$2;
	}
	public String getExtra3() {
		return extra3;
	}
	public void setExtra3(String extra3) {
		this.extra3 = extra3;
	}
	public double getExtra$3() {
		return extra$3;
	}
	public void setExtra$3(double extra$3) {
		this.extra$3 = extra$3;
	}
	public String getExtra4() {
		return extra4;
	}
	public void setExtra4(String extra4) {
		this.extra4 = extra4;
	}
	public double getExtra$4() {
		return extra$4;
	}
	public void setExtra$4(double extra$4) {
		this.extra$4 = extra$4;
	}
	public String getExtra5() {
		return extra5;
	}
	public void setExtra5(String extra5) {
		this.extra5 = extra5;
	}
	public double getExtra$5() {
		return extra$5;
	}
	public void setExtra$5(double extra$5) {
		this.extra$5 = extra$5;
	}
	public String getfACTAdresse() {
		return fACTAdresse;
	}
	public void setfACTAdresse(String fACTAdresse) {
		this.fACTAdresse = fACTAdresse;
	}
	public String getfACTVille() {
		return fACTVille;
	}
	public void setfACTVille(String fACTVille) {
		this.fACTVille = fACTVille;
	}
	public String getfACTCP() {
		return fACTCP;
	}
	public void setfACTCP(String fACTCP) {
		this.fACTCP = fACTCP;
	}
	public String getfACTProvince() {
		return fACTProvince;
	}
	public void setfACTProvince(String fACTProvince) {
		this.fACTProvince = fACTProvince;
	}
	public String getfACTProvinceCode() {
		return fACTProvinceCode;
	}
	public void setfACTProvinceCode(String fACTProvinceCode) {
		this.fACTProvinceCode = fACTProvinceCode;
	}
	public String getFACTPays() {
		return FACTPays;
	}
	public void setFACTPays(String fACTPays) {
		FACTPays = fACTPays;
	}
	public String getFACTContact() {
		return FACTContact;
	}
	public void setFACTContact(String fACTContact) {
		FACTContact = fACTContact;
	}
	public String getFACTTelephone() {
		return FACTTelephone;
	}
	public void setFACTTelephone(String fACTTelephone) {
		FACTTelephone = fACTTelephone;
	}
	public String getFACTTelecopie() {
		return FACTTelecopie;
	}
	public void setFACTTelecopie(String fACTTelecopie) {
		FACTTelecopie = fACTTelecopie;
	}
	
	
	
}
