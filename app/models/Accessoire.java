/**
 * 
 */
package models;

import java.math.BigDecimal;
/**
 * @author Eva
 *
 */
public class Accessoire {
	
	private int id;				// smallint NOT NULL,
	private String codeGBC;		// nvarchar(20) NULL,
	private String type;		// nvarchar(50) NULL,
	private String produit;		// nvarchar(50) NOT NULL,
	private String format;		// nvarchar(50) NULL,
	private BigDecimal prixCoutant;	// decimal(5, 2) NULL,
	private String description;	// nvarchar(max) NULL,
	private String caracteristiques;	// nvarchar(80) NULL,
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the codeGBC
	 */
	public String getCodeGBC() {
		return codeGBC;
	}
	/**
	 * @param codeGBC the codeGBC to set
	 */
	public void setCodeGBC(String codeGBC) {
		this.codeGBC = codeGBC;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the produit
	 */
	public String getProduit() {
		return produit;
	}
	/**
	 * @param produit the produit to set
	 */
	public void setProduit(String produit) {
		this.produit = produit;
	}
	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	/**
	 * @return the prixCoutant
	 */
	public BigDecimal getPrixCoutant() {
		return prixCoutant;
	}
	/**
	 * @param prixCoutant the prixCoutant to set
	 */
	public void setPrixCoutant(BigDecimal prixCoutant) {
		this.prixCoutant = prixCoutant;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the caracteristiques
	 */
	public String getCaracteristiques() {
		return caracteristiques;
	}
	/**
	 * @param caracteristiques the caracteristiques to set
	 */
	public void setCaracteristiques(String caracteristiques) {
		this.caracteristiques = caracteristiques;
	}
	
	

}
