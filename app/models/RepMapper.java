/**
 * 
 */
package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author Eva
 * Converts JDBC result set to Rep object
 */
public class RepMapper implements RowMapper<Rep>{

	public Rep mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		Rep rep = new Rep();
		rep.setSalesmanCode(rs.getString("Salesman"));
		rep.setDescription1(rs.getString("Description1"));
		rep.setEmail(rs.getString("Email"));
		
		return rep;
	}
}
