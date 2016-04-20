/**
 * 
 */
package models;

/**
 * @author Eva
 *
 */
public class Client {
	
	private String noClient;	// not null
	private String nom;
	private String adresse;
	private String ville;
	private String telephone;
	private String fax;
	private String pays;
	private String province;
	private String provinceCode;
	private String codePostal;
	private String email;
	
	public Client(){
		
	}
	
	public Client(Client client){
		this.noClient = client.noClient;
		this.nom = client.nom;
		this.adresse = client.adresse;
		this.ville = client.ville;
		this.telephone = client.telephone;
		this.fax = client.fax;
		this.pays = client.pays;
		this.province = client.province;
		this.provinceCode = client.provinceCode;
		this.codePostal = client.codePostal;
		this.email = client.email;
	}
	
	/**
	 * @return the client
	 */
	public String getNoClient() {
		return noClient;
	}
	/**
	 * @param client the client to set
	 */
	public void setNoClient(String noClient) {
		this.noClient = noClient;
	}
	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * @return the adresse
	 */
	public String getAdresse() {
		return adresse;
	}
	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	/**
	 * @return the ville
	 */
	public String getVille() {
		return ville;
	}
	/**
	 * @param ville the ville to set
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	
}
