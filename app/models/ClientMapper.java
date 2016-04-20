/**
 * 
 */
package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author Eva
 * Maps a JDBC result Set to a Client object
 */
public class ClientMapper implements RowMapper<Client>{

	public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		Client client = new Client();
				
		client.setNoClient(rs.getString("NoClient"));
		client.setNom(rs.getString("Nom"));
		client.setAdresse(rs.getString("Adresse"));
		client.setVille(rs.getString("Ville"));
		client.setTelephone(rs.getString("Phone"));
		client.setFax(rs.getString("Fax"));
		client.setEmail(rs.getString("Email"));
		client.setCodePostal(rs.getString("ZipCode"));
		client.setProvince(rs.getString("Province"));
		client.setProvinceCode(rs.getString("ProvinceCode"));
		client.setPays(rs.getString("Country"));			
		
		return client;
	}
}
