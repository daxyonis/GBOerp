/**
 * 
 */
package services;

import java.util.List;

import models.ActiviteMO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.dao.ActiviteMODAO;
/**
 * @author Eva
 *
 */
@Service
public class ActiviteMOServiceImpl implements ActiviteMOService {
	
	
	private ActiviteMODAO moDAO;
	
	@Autowired
	public ActiviteMOServiceImpl(ActiviteMODAO moDAO){
		this.moDAO = moDAO;
	}

	/* (non-Javadoc)
	 * @see services.ActiviteMOService#findByActivite(java.lang.String)
	 */
	public ActiviteMO findByActivite(String category, String activite) {
		
		return moDAO.findByActivite(category, activite);
	}

	/* (non-Javadoc)
	 * @see services.ActiviteMOService#findAll()
	 */
	public List<ActiviteMO> findAll(String category) {
		
		return moDAO.findAll(category);
	}

	@Override
	public String getMOcategory(String key) {
		return moDAO.getMOcategory(key);
	}
	
	

}
