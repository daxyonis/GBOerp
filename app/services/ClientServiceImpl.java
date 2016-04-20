/**
 * 
 */
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.dao.ClientDAO;
import models.Client;

/**
 * @author Eva
 *
 */
@Service
public class ClientServiceImpl implements ClientService {
	
	private ClientDAO clientDAO;
	
	@Autowired
	public ClientServiceImpl(ClientDAO cliDAO){
		this.clientDAO = cliDAO;
	}

	/* (non-Javadoc)
	 * @see services.ClientService#findAll()
	 */
	public List<Client> findAll() {
		return clientDAO.findAll();
	}

	/* (non-Javadoc)
	 * @see services.ClientService#findByNo(java.lang.String)
	 */
	public Client findByNo(String noClient) {
		return clientDAO.findByNo(noClient);
	}

}
