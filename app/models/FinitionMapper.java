/**
 * 
 */
package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author Eva
 *
 */
public class FinitionMapper implements RowMapper<Finition> {
	
	public Finition mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		Finition fini  = new Finition();
		
		fini.setId(rs.getInt("Id"));
		fini.setCodeGBC(rs.getNString("CodeGBC"));
		fini.setType(rs.getNString("Type"));
		fini.setTaille(rs.getNString("Taille"));
		fini.setCotes1(rs.getNString("Cotes1"));
		fini.setCotes2(rs.getNString("Cotes2"));
		fini.setPrix(rs.getBigDecimal("Prix"));
		fini.setUnite(rs.getString("Unite"));
		fini.setActive(rs.getBoolean("Active"));
		fini.setCoutFixe(rs.getDouble("CoutFixe"));
		
		return fini;
	}

}
