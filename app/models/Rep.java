/**
 * 
 */
package models;

/**
 * @author Eva
 * Modélise un représentant
 */
public class Rep {
	
	private String salesmanCode;
	private String description1;
	private String email;
	
	public Rep(){
		
	}
	
	public Rep(Rep rep){
		this.salesmanCode = rep.salesmanCode;
		this.description1 = rep.description1;
		this.email = rep.email;
	}
	
	/**
	 * @return the salesmanCode
	 */
	public String getSalesmanCode() {
		return salesmanCode;
	}
	/**
	 * @param salesmanCode the salesmanCode to set
	 */
	public void setSalesmanCode(String salesmanCode) {
		this.salesmanCode = salesmanCode;
	}
	/**
	 * @return the description1
	 */
	public String getDescription1() {
		return description1;
	}
	/**
	 * @param description1 the description1 to set
	 */
	public void setDescription1(String description1) {
		this.description1 = description1;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
