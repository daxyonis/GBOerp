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
public class AccessoireMapper implements RowMapper<Accessoire>{
	
	public Accessoire mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Accessoire acc = new Accessoire();
		
		acc.setId(rs.getInt("Id"));
		acc.setCodeGBC(rs.getNString("CodeGBC"));
		acc.setType(rs.getNString("Type"));
		acc.setProduit(rs.getNString("Produit"));
		acc.setFormat(rs.getNString("Format"));
		acc.setPrixCoutant(rs.getBigDecimal("PrixCoutant"));
		acc.setDescription(rs.getNString("Description"));
		acc.setCaracteristiques(rs.getNString("Caracteristiques"));
		
		return acc;
	}

}
