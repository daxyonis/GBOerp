package models;

/**
 * Classe qui modélise les sous-sections de main d'oeuvre
 * 
 * @author Eva
 *
 */
public class ActiviteMO {

	private String categorie;
	private String activite;
	private String codeMachine;
	private String codeOp;
	private float tauxGlobal;
	
	// Must specify the activity category
	public ActiviteMO(String categorie){
		this.categorie = categorie;
	}
	
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	public String getActivite() {
		return activite;
	}
	public void setActivite(String activite) {
		this.activite = activite;
	}
	public String getCodeMachine() {
		return codeMachine;
	}
	public void setCodeMachine(String codeMachine) {
		this.codeMachine = codeMachine;
	}
	public String getCodeOp() {
		return codeOp;
	}
	public void setCodeOp(String codeOp) {
		this.codeOp = codeOp;
	}
	public float getTauxGlobal() {
		return tauxGlobal;
	}
	public void setTauxGlobal(float tauxGlobal) {
		this.tauxGlobal = tauxGlobal;
	}
	
	
	
}
