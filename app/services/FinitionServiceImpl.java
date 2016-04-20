/**
 * 
 */
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.dao.FinitionDAO;
import models.Finition;

/**
 * @author Eva
 * Implementation of the FinitionService interface
 */
@Service
public class FinitionServiceImpl implements FinitionService {		

	private FinitionDAO finitionDAO;
	
	@Autowired
	public FinitionServiceImpl(FinitionDAO finiDAO){
		this.finitionDAO = finiDAO;
	}

	/* (non-Javadoc)
	 * @see services.FinitionService#findAll()
	 */
	public List<Finition> findAll() {
		return finitionDAO.findAll();
	}
	
	public List<Finition> findAllActive() {
		return finitionDAO.findAllActive();
	}
	
	@Override
	public Finition getById(int id) {
		return finitionDAO.getById(id);
	}

	@Override
	public boolean save(Finition finition) {
		return finitionDAO.save(finition);
	}

	@Override
	public boolean add(Finition finition) {
		return finitionDAO.add(finition);
	}

	@Override
	public boolean remove(Finition finition) {
		return finitionDAO.remove(finition);
	}
	
	

}
