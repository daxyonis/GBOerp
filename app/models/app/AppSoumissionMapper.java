package models.app;

import java.sql.ResultSet;
import java.sql.SQLException;

import models.SoumissionMapper.FieldScopeEnum;

import org.springframework.jdbc.core.RowMapper;

public class AppSoumissionMapper implements RowMapper<AppSoumission> {
	
	/**
	 * This enum describes the possible sets of field names that are returned from 
	 * an AppSoumission query 
	 * ALL : all field names are present
	 * OVERVIEW : only the field names for an overview of the AppSoumission are present
	 */
	public static enum FieldScopeEnum {ALL, OVERVIEW}
	
	private FieldScopeEnum fieldScope;
	private AppSoumEnteteMapper enteteMapper;
	
	public AppSoumissionMapper(FieldScopeEnum fs){
		this.fieldScope = fs;
		this.enteteMapper = new AppSoumEnteteMapper(fs);
	}
		

	@Override
	public AppSoumission mapRow(ResultSet rs, int row) throws SQLException {
		AppSoumission soum = new AppSoumission();
		// Here do the direct mapping
		soum.setSuite(rs.getInt("Suite"));
		soum.setVersion(rs.getNString("Version"));
		soum.setNoSoumission(rs.getInt("NoSoumission"));
		soum.setNoDossier(rs.getInt("NoDossier"));
		soum.setStatut(rs.getInt("Statut"));				
						
		switch(fieldScope){		
		case OVERVIEW:			
			break;
		case ALL:
			soum.setCommission(rs.getFloat("Commission"));		
			soum.setNoteInterne(rs.getNString("NoteInterne"));
			soum.setDateProductionDebut(rs.getDate("DateProductionDebut"));
			soum.setDateProductionNecessaire(rs.getDate("DateProductionNecessaire"));
			soum.setFlagHeader(rs.getInt("FlagHeader"));
			soum.setFlex(rs.getInt("Flex"));
			soum.setGeniusNoCom(rs.getNString("GeniusNoCom"));
			break;
		}
		
		// Pass to the sub-objects mappers
		soum.setEntete(enteteMapper.mapRow(rs, row));
		
		// TODO
		//soum.expedition = expeditionMapper.mapRow(rs,row);
		//soum.suivi = suiviMapper.mapRow(rs, row);
		
		return soum;
	}
	

}
