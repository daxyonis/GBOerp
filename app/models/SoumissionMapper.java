/**
 * 
 */
package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * SoumissionMapper		pour faire la correspondance entre un ResultSet
 * 			    (résultat d'une requête SQL) et l'objet Soumission
 * @author Eva
 *
 */
public class SoumissionMapper implements RowMapper<Soumission>{
	
	
	/**
	 * This enum describes the possible sets of field names that are returned from 
	 * a Soumission query 
	 * ALL : all field names are present
	 * OVERVIEW : only the field names for an overview of the Soumission are present
	 */
	public static enum FieldScopeEnum {ALL, OVERVIEW}
	
	private FieldScopeEnum fieldScope;
	public SoumissionMapper(FieldScopeEnum fs){
		this.fieldScope = fs;
	}
	
	public Soumission mapRow(ResultSet rs, int rowNum) throws SQLException {
		Soumission soum = new Soumission();
		
		// Set Soumission properties
		// This is the minimal set (overview set)
		soum.setNoSoumission(rs.getInt("NoSoumission"));
		soum.setSuite(rs.getInt("Suite"));
		soum.setVersion(rs.getNString("Version"));
		soum.setProjet(rs.getNString("Projet"));
		soum.setClientSoum(rs.getString("ClientSoum"));
		soum.setNoVendeur(rs.getNString("NoVendeur"));
		soum.setGeniusNoClient(rs.getNString("GeniusNoClient"));		
		soum.setStatut(rs.getInt("Statut"));
		soum.setNoDossier(rs.getInt("NoDossier"));
		soum.setBanniereClient(rs.getNString("BanniereClient"));
		soum.setCritere(rs.getNString("Critere"));
		soum.setDateEntre(rs.getDate("DateEntre"));
		
		switch(fieldScope){		
		case OVERVIEW:			
			break;
		case ALL:
			soum.setClientEmail(rs.getNString("ClientEmail"));
			soum.setClientTelephoneContact(rs.getNString("ClientTelephoneContact"));
			soum.setContact(rs.getNString("Contact"));
			soum.setCommission(rs.getDouble("Commission"));
			soum.setDateDebutEstimation(rs.getDate("DateDebutEstimation"));			
			soum.setDateLivraison(rs.getNString("DateLivraison"));
			soum.setNomVendeur(rs.getNString("NomVendeur"));
			soum.setPrix(rs.getBigDecimal("Prix"));
			break;
		}
		
		return soum;
	}
}
