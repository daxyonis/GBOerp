/**
 * 
 */
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.dao.AccessoireDAO;
import models.Accessoire;
import models.Type;

/**
 * @author Eva
 * Implementation of the AccessoireService interface
 */
@Service
public class AccessoireServiceImpl implements AccessoireService {
	
	private AccessoireDAO accessoireDAO;
	
	@Autowired
	public AccessoireServiceImpl(AccessoireDAO accDAO){
		this.accessoireDAO = accDAO;
	}

	/* (non-Javadoc)
	 * @see services.AccessoireService#findAll()
	 */
	public List<Accessoire> findByType(String pType) {		
		return accessoireDAO.findByType(pType);
	}

	public List<Type> findTypes(){
		return accessoireDAO.findTypes();
	}

	@Override
	public Accessoire findById(int id) {
		return accessoireDAO.findById(id);
	}
	
	
}
