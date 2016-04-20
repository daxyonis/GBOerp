package models.app;

import java.time.LocalDateTime;

public class AppSoumSuivi {

	private LocalDateTime dateDemande = LocalDateTime.now(); //a mettre dans une section Suivi -->
	private LocalDateTime dateDebutEstimation;
	private LocalDateTime dateFinEstimation;
	private LocalDateTime dateRemiseClient;
	private LocalDateTime dateAcceptationRefus; 
	private int accepterRefuser = 0;
	private String raisonRefuser;
	public LocalDateTime getDateDemande() {
		return dateDemande;
	}
	public void setDateDemande(LocalDateTime dateDemande) {
		this.dateDemande = dateDemande;
	}
	public LocalDateTime getDateDebutEstimation() {
		return dateDebutEstimation;
	}
	public void setDateDebutEstimation(LocalDateTime dateDebutEstimation) {
		this.dateDebutEstimation = dateDebutEstimation;
	}
	public LocalDateTime getDateFinEstimation() {
		return dateFinEstimation;
	}
	public void setDateFinEstimation(LocalDateTime dateFinEstimation) {
		this.dateFinEstimation = dateFinEstimation;
	}
	public LocalDateTime getDateRemiseClient() {
		return dateRemiseClient;
	}
	public void setDateRemiseClient(LocalDateTime dateRemiseClient) {
		this.dateRemiseClient = dateRemiseClient;
	}
	public LocalDateTime getDateAcceptationRefus() {
		return dateAcceptationRefus;
	}
	public void setDateAcceptationRefus(LocalDateTime dateAcceptationRefus) {
		this.dateAcceptationRefus = dateAcceptationRefus;
	}
	public int getAccepterRefuser() {
		return accepterRefuser;
	}
	public void setAccepterRefuser(int accepterRefuser) {
		this.accepterRefuser = accepterRefuser;
	}
	public String getRaisonRefuser() {
		return raisonRefuser;
	}
	public void setRaisonRefuser(String raisonRefuser) {
		this.raisonRefuser = raisonRefuser;
	}
	
	
}
