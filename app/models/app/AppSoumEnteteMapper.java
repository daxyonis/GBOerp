package models.app;

import java.sql.ResultSet;
import java.sql.SQLException;

import models.app.AppSoumissionMapper.FieldScopeEnum;

import org.springframework.jdbc.core.RowMapper;

public class AppSoumEnteteMapper implements RowMapper<AppSoumEntete> {
	
	private FieldScopeEnum fieldScope;
	
	public AppSoumEnteteMapper(FieldScopeEnum fs){
		this.fieldScope = fs;
	}

	@Override
	public AppSoumEntete mapRow(ResultSet rs, int row) throws SQLException {

		AppSoumEntete entete = new AppSoumEntete();
		entete.setNoSoumission(rs.getInt("NoSoumission"));
		entete.setProjet(rs.getNString("Projet"));
		entete.setClientSoum(rs.getString("ClientSoum"));
		entete.setGeniusNoClient(rs.getNString("GeniusNoClient"));
		entete.setNoVendeur(rs.getNString("NoVendeur"));		
		entete.setBanniereClient(rs.getString("BanniereClient"));
		entete.setCritere(rs.getNString("Critere"));
		entete.setDateEntre(rs.getDate("DateEntre"));
			
		switch(fieldScope){		
		case OVERVIEW:			
			break;
		case ALL:
			entete.setClientAdresse(rs.getNString("ClientAdresse"));
			entete.setClientAPP(rs.getNString("clientAPP"));
			entete.setClientCP(rs.getNString("clientCP"));
			entete.setClientEmail(rs.getNString("clientEmail"));
			entete.setClientFactureA(rs.getNString("clientFactureA"));
			entete.setClientFax(rs.getNString("clientFax"));
			entete.setClientPays(rs.getString("ClientPays"));
			entete.setClientProvince(rs.getString("ClientProvince"));		
			entete.setClientTelephone(rs.getNString("ClientTelephone"));
			entete.setClientTelephoneContact(rs.getNString("ClientTelephoneContact"));
			entete.setClientVille(rs.getNString("ClientVille"));
			entete.setContact(rs.getNString("Contact"));		
			entete.setDateLivraison(rs.getNString("DateLivraison"));
			entete.setDescription(rs.getNString("Description"));
			break;
		}
		
		return entete;
	}
	
	

}
