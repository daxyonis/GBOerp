/**
 * 
 */
package models;

import java.math.BigDecimal;
/**
 * @author Eva
 *
 */
public class Finition {	
	
	private int id;				// smallint NOT NULL,	
	private String codeGBC;		// nvarchar(20) NULL,
	private String type;		// nvarchar(50) NOT NULL,
	private String taille;		// nvarchar(20) NULL,
	private String cotes1;		// nvarchar(20) NULL,
	private String cotes2;		// nvarchar(20) NULL,
	private BigDecimal prix;	// decimal(3, 2) NULL,
	private String unite;
	private boolean active;
	private double coutFixe;	// money = decimal(19,4)
		
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCodeGBC() {
		return codeGBC;
	}
	
	public void setCodeGBC(String codeGBC) {
		this.codeGBC = codeGBC;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getTaille() {
		return taille;
	}
	
	public void setTaille(String taille) {
		this.taille = taille;
	}
	
	public String getCotes1() {
		return cotes1;
	}
	
	public void setCotes1(String cotes1) {
		this.cotes1 = cotes1;
	}
	
	public String getCotes2() {
		return cotes2;
	}
	
	public void setCotes2(String cotes2) {
		this.cotes2 = cotes2;
	}
	
	public BigDecimal getPrix() {
		return prix;
	}
	
	public void setPrix(BigDecimal prix) {
		this.prix = prix;
	}
	
	public String getUnite() {
		return unite;
	}
	
	public void setUnite(String unite) {
		this.unite = unite;
	}
	public double getCoutFixe() {
		return coutFixe;
	}
	public void setCoutFixe(double coutFixe) {
		this.coutFixe = coutFixe;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
