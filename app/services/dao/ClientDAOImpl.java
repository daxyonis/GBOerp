/**
 * 
 */
package services.dao;

import java.util.List;
import models.Client;
import models.ClientMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author Eva
 * This class does only READ operations on the Genius (BODB) database
 * to get a list of clients
 */
@Repository
@Transactional(readOnly=true)
public class ClientDAOImpl implements ClientDAO {
	
	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public ClientDAOImpl(JdbcTemplate jdbcTempl){
		this.jdbcTemplateObject = jdbcTempl;
	}

	/* (non-Javadoc)
	 * @see services.simple.ClientDAO#findAll()
	 */
	public List<Client> findAll() {				
		String SQL = "SELECT C.Customer As NoClient, C.Name As Nom, " + 
					 "CASE WHEN C.Address2 Is Not Null THEN(C.Address1 + ', ' + C.Address2) " +
					 "ELSE C.Address1 END AS Adresse, " + 
					 "C.City As Ville, C.Phone, "+
					 "C.Fax, C.Email, C.ZipCode, C.Country, P.Description2 As Province, P.Code As ProvinceCode " +
					 "FROM BODB.dbo.vgMfiCustomers C " +
					 "LEFT JOIN BODB.dbo.vgGMFProvince P ON C.Province = P.Code and C.Country = P.Value " +
					 "GROUP BY C.CustomerID, C.Customer, C.Name, C.Address1, C.Address2, C.City, C.Phone, C.Fax, " +
					 "C.Email, C.ZipCode, P.Description2, C.Country, C.Province, P.Code " +
					 "HAVING (((C.Name) Is Not Null)) " +
					 "ORDER BY C.Name; ";
		List <Client> clients = jdbcTemplateObject.query(SQL, new ClientMapper());
		return clients;
	}

	/* (non-Javadoc)
	 * @see services.simple.ClientDAO#findByNo(int)
	 */
	public Client findByNo(String noClient) {
		String SQL = "SELECT C.Customer As NoClient, C.Name As Nom, " + 
				 "CASE WHEN C.Address2 Is Not Null THEN(C.Address1 + ', ' + C.Address2) " +
				 "ELSE C.Address1 END AS Adresse, " + 
				 "C.City As Ville, C.Phone, "+
				 "C.Fax, C.Email, C.ZipCode, C.Country, P.Description2 As Province, P.Code As ProvinceCode " +
				 "FROM BODB.dbo.vgMfiCustomers C LEFT JOIN BODB.dbo.vgGMFProvince P ON C.Province = P.Code " +				 
				 "WHERE C.Customer = ?";
		
		Client client;
		
		try{
			client = jdbcTemplateObject.queryForObject(SQL, new Object[]{noClient}, new ClientMapper());
		}
		catch(EmptyResultDataAccessException e){
			// if not found, return null
			client = null;
		}
		
		return client;
	}

}
