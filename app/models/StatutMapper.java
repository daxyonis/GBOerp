package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class StatutMapper implements RowMapper<Statut>{
	
	public Statut mapRow(ResultSet rs, int rowNum) throws SQLException {
		Statut statut = new Statut();
		
		statut.setCode(rs.getInt("Code"));
		statut.setStatut(rs.getNString("Statut"));
		
		return statut;
	}

}
