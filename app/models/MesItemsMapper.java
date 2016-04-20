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
public class MesItemsMapper implements RowMapper<MesItems>{

	public MesItems mapRow(ResultSet rs, int rowNum) throws SQLException {
		MesItems mesItems = new MesItems();
		
		// Set MesItems properties
		mesItems.setUsager(rs.getNString("Usager"));
		mesItems.setNoItem(rs.getInt("NoItem"));
		
		return mesItems;
	}
}
