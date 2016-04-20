/**
 * 
 */
package services.dao;

import models.Client;
import java.util.List;

/**
 * @author Eva
 *
 */
public interface ClientDAO {

	public List<Client> findAll();	
	
	public Client findByNo(String noClient);
}
