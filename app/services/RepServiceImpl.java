/**
 * 
 */
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.dao.RepDAO;
import models.Rep;

/**
 * @author Eva
 *
 */
@Service
public class RepServiceImpl implements RepService {
	
	private RepDAO repDAO;
	
	@Autowired
	public RepServiceImpl(RepDAO repDAO){
		this.repDAO = repDAO;
	}

	/* (non-Javadoc)
	 * @see services.RepService#findAllActive()
	 */
	public List<Rep> findAllActive() {
		return repDAO.findAllActive();
	}

	/* (non-Javadoc)
	 * @see services.RepService#findByCode(java.lang.String)
	 */
	public Rep findByCode(String code) {
		return repDAO.findByCode(code);
	}

	@Override
	public List<Rep> findByEmail(String email) {
		return repDAO.findByEmail(email);
	}

	
	
}
