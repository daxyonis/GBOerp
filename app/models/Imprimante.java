/**
 * 
 */
package models;

import java.math.BigDecimal;
/**
 * @author Eva
 *
 */
public class Imprimante {
	private String codeSelection;
	private String machine;
	private String mode;
	private int vitesse;
	private int pourcentEncre;
	private BigDecimal prixEncre;
	private int perteEncre;
	private int margeEncre;
	private String codeMachine;
	private int hauteurMax_po;
	private int largeurMax_po;
	private boolean active;
	
	// Note : ces champs proviennent d'une autre table
	private float tauxImpr;
	private float tauxManip;
	
	/**
	 * @return the codeSelection
	 */
	public String getCodeSelection() {
		return codeSelection;
	}
	/**
	 * @param codeSelection the codeSelection to set
	 */
	public void setCodeSelection(String codeSelection) {
		this.codeSelection = codeSelection;
	}
	/**
	 * @return the machine
	 */
	public String getMachine() {
		return machine;
	}
	/**
	 * @param machine the machine to set
	 */
	public void setMachine(String machine) {
		this.machine = machine;
	}
	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	/**
	 * @return the vitesse
	 */
	public int getVitesse() {
		return vitesse;
	}
	/**
	 * @param vitesse the vitesse to set
	 */
	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}
	/**
	 * @return the pourcentEncre
	 */
	public int getPourcentEncre() {
		return pourcentEncre;
	}
	/**
	 * @param pourcentEncre the pourcentEncre to set
	 */
	public void setPourcentEncre(int pourcentEncre) {
		this.pourcentEncre = pourcentEncre;
	}
	/**
	 * @return the prixEncre
	 */
	public BigDecimal getPrixEncre() {
		return prixEncre.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	/**
	 * @param prixEncre the prixEncre to set
	 */
	public void setPrixEncre(BigDecimal prixEncre) {
		this.prixEncre = prixEncre;
	}
	/**
	 * @return the perteEncre
	 */
	public int getPerteEncre() {
		return perteEncre;
	}
	/**
	 * @param perteEncre the perteEncre to set
	 */
	public void setPerteEncre(int perteEncre) {
		this.perteEncre = perteEncre;
	}
	/**
	 * @return the margeEncre
	 */
	public int getMargeEncre() {
		return margeEncre;
	}
	/**
	 * @param margeEncre the margeEncre to set
	 */
	public void setMargeEncre(int margeEncre) {
		this.margeEncre = margeEncre;
	}
	/**
	 * @return the codeMachine
	 */
	public String getCodeMachine() {
		return codeMachine;
	}
	/**
	 * @param codeMachine the codeMachine to set
	 */
	public void setCodeMachine(String codeMachine) {
		this.codeMachine = codeMachine;
	}
		
	/**
	 * @return the hauteurMax_po
	 */
	public int getHauteurMax_po() {
		return hauteurMax_po;
	}
	/**
	 * @param hauteurMax_po the hauteurMax_po to set
	 */
	public void setHauteurMax_po(int hauteurMax_po) {
		this.hauteurMax_po = hauteurMax_po;
	}
	/**
	 * @return the largeurMax_po
	 */
	public int getLargeurMax_po() {
		return largeurMax_po;
	}
	/**
	 * @param largeurMax_po the largeurMax_po to set
	 */
	public void setLargeurMax_po(int largeurMax_po) {
		this.largeurMax_po = largeurMax_po;
	}		
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	/**
	 * @return the tauxImpr
	 */
	public float getTauxImpr() {
		return tauxImpr;
	}
	/**
	 * @param tauxImpr the tauxImpr to set
	 */
	public void setTauxImpr(float tauxImpr) {
		this.tauxImpr = tauxImpr;
	}
	/**
	 * @return the tauxManip
	 */
	public float getTauxManip() {
		return tauxManip;
	}
	/**
	 * @param tauxManip the tauxManip to set
	 */
	public void setTauxManip(float tauxManip) {
		this.tauxManip = tauxManip;
	}
	
	@Override
	public boolean equals(Object obj) {
		return codeSelection.equals(((Imprimante)obj).getCodeSelection());
	}
			
	
}