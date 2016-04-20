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
public class FlexibleDTOMapper implements RowMapper<FlexibleDTO>{
	
	private FlexibleMapper flexMapper;
	public FlexibleDTOMapper(){
		flexMapper = new FlexibleMapper();
	}
	
	public FlexibleDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
		
		FlexibleDTO flexDto = new FlexibleDTO();
		
		// Set flexible properties
		flexDto.setFlex(this.flexMapper.mapRow(rs, rowNum));
		// Set the cost
		flexDto.setMaxCost(rs.getDouble("Pi2Cost"));
		flexDto.setUnit(rs.getString("Unit"));
		
		return flexDto;
	}

}
