package models.app;

import java.util.Date;
import java.util.List;

import models.Client;
import models.Rep;
import play.data.validation.*;

/**
 * This class models the new Soumission data structure
 * @author Eva
 *
 */
public class AppSoumission {
		
	private int noSoumission;	
	private int suite = 0;
	
	@Constraints.MaxLength(1)
	@Constraints.MinLength(1)
	private String version;			
			
	private float commission = 0;	
	private int statut = 1;	
	
	// Concernant la commande
	private int noDossier;		
	@Constraints.MaxLength(50)
	private String geniusNoCom;	
	private Date dateProductionDebut;
	private Date dateProductionNecessaire;	
	private int flex = 0; //nb de jours flexibles sur la commande
	
	private String noteInterne;	
		
	private int flagHeader = 0;
	
	// Sous-objets
	public Rep rep;
	public Client client;
	public AppSoumEntete entete = new AppSoumEntete();
	public AppSoumExpedition expedition = new AppSoumExpedition();
	public AppSoumSuivi suivi = new AppSoumSuivi();
	// Liste des items
	public List<AppItem> items;
	
	// Autres champs
	private String texteStatut;		// nécessaire pour la vue liste des soumissions	
	
	//*****************************************
	// GETTERS AND SETTERS
	public int getNoSoumission() {
		return noSoumission;
	}
	public void setNoSoumission(int noSoumission) {
		this.noSoumission = noSoumission;
	}
	public int getSuite() {
		return suite;
	}
	public void setSuite(int suite) {
		this.suite = suite;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public float getCommission() {
		return commission;
	}
	public void setCommission(float commission) {
		this.commission = commission;
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
	public String getGeniusNoCom() {
		return geniusNoCom;
	}
	public void setGeniusNoCom(String geniusNoCom) {
		this.geniusNoCom = geniusNoCom;
	}
	public Date getDateProductionDebut() {
		return dateProductionDebut;
	}
	public void setDateProductionDebut(Date dateProductionDebut) {
		this.dateProductionDebut = dateProductionDebut;
	}
	public Date getDateProductionNecessaire() {
		return dateProductionNecessaire;
	}
	public void setDateProductionNecessaire(Date dateProductionNecessaire) {
		this.dateProductionNecessaire = dateProductionNecessaire;
	}
	public int getFlex() {
		return flex;
	}
	public void setFlex(int flex) {
		this.flex = flex;
	}
	public String getNoteInterne() {
		return noteInterne;
	}
	public void setNoteInterne(String noteInterne) {
		this.noteInterne = noteInterne;
	}
	public int getFlagHeader() {
		return flagHeader;
	}
	public void setFlagHeader(int flagHeader) {
		this.flagHeader = flagHeader;
	}
	public AppSoumEntete getEntete() {
		return entete;
	}
	public void setEntete(AppSoumEntete entete) {
		this.entete = entete;
	}
	public AppSoumExpedition getExpedition() {
		return expedition;
	}
	public void setExpedition(AppSoumExpedition expedition) {
		this.expedition = expedition;
	}
	public AppSoumSuivi getSuivi() {
		return suivi;
	}
	public void setSuivi(AppSoumSuivi suivi) {
		this.suivi = suivi;
	}
	public String getTexteStatut() {
		return texteStatut;
	}
	public void setTexteStatut(String texteStatut) {
		this.texteStatut = texteStatut;
	} 	
	//*****************************************
}
