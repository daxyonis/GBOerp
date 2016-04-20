package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LaminageMapper implements RowMapper<Laminage> {

	@Override
	public Laminage mapRow(ResultSet rs, int row) throws SQLException {
		Laminage lam = new Laminage();
		lam.setId(rs.getInt("Id"));
		lam.setNom(rs.getNString("Nom"));
		lam.setCaracteristique(rs.getNString("Caracteristique"));
		lam.setCoutPiCar(rs.getFloat("CoutPiCar"));
		return lam;
	}

	
}
