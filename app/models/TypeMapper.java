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
public class TypeMapper implements RowMapper<Type>{
	
	public Type mapRow(ResultSet rs, int rowNum) throws SQLException {		
		Type type = new Type();
		type.setType(rs.getNString("Type"));
		return type;
	}

}
