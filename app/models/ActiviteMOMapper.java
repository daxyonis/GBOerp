package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ActiviteMOMapper implements RowMapper<ActiviteMO> {

	private String cat;
	public ActiviteMOMapper(String categorie){
		this.cat = categorie;
	}
	
	@Override
	public ActiviteMO mapRow(ResultSet rs, int row) throws SQLException {
		ActiviteMO mo = new ActiviteMO(cat);
		mo.setActivite(rs.getString("Activite"));
		mo.setCodeMachine(rs.getString("CodeMachine"));
		mo.setCodeOp(rs.getString("CodeOp"));
		mo.setTauxGlobal(rs.getFloat("TauxGlobal"));
		return mo;
	}

	
}
