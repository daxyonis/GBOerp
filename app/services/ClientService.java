/**
 * 
 */
package services;

import models.Client;
import java.util.List;

/**
 * @author Eva
 *
 */
public interface ClientService {
	
	/**
	 * Find all the Client objects that exist
	 * @return  java.util.List of all the Client objects
	 */
	public List<Client> findAll();
	
	public Client findByNo(String noClient);

}
