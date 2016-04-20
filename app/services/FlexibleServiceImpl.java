/**
 * 
 */
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import services.dao.FlexibleDAO;
import services.dao.FlexibleDTODAO;
import models.Flexible;
import models.FlexibleDTO;

/**
 * @author Eva
 *
 */
@Service
public class FlexibleServiceImpl implements FlexibleService {
	
	private FlexibleDAO flexDAO;
	private FlexibleDTODAO flexDTODAO;
	private ImprimanteService printerService;
		
	@Autowired
	public FlexibleServiceImpl(FlexibleDAO flexDAO, FlexibleDTODAO flexDTODAO, ImprimanteService ps){
		this.flexDAO = flexDAO; 
		this.flexDTODAO = flexDTODAO;
		this.printerService = ps;
	}
		

	/* (non-Javadoc)
	 * @see services.FlexibleService#findAll()
	 */
	public List<Flexible> findAll() {
		List<Flexible> flexs = flexDAO.findAll();
		// Now populate the list of printers for each
		for (Flexible flex : flexs){
			flex.setImprimantes(printerService.findForProduct("Flexible", flex.getId()));
		}
		return flexs; 
	}

	/* (non-Javadoc)
	 * @see services.FlexibleService#getById(int)
	 */
	public Flexible getById(int id) {
		Flexible flex = flexDAO.getById(id);
		// Get the printers
		flex.setImprimantes(printerService.findForProduct("Flexible", flex.getId()));
		return flex;
	}

	/* (non-Javadoc)
	 * @see services.FlexibleService#update(models.Flexible)
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public boolean update(Flexible flex) {

		return flexDAO.update(flex);
	}

	/* (non-Javadoc)
	 * @see services.FlexibleService#add(models.Flexible)
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public boolean add(Flexible flex) {
		
		return flexDAO.add(flex);
	}

	/* (non-Javadoc)
	 * @see services.FlexibleService#remove(int)
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public boolean remove(int id) {

		return flexDAO.remove(id);
	}

	/* (non-Javadoc)
	 * @see services.FlexibleService#remove(models.Flexible)
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public boolean remove(Flexible flex) {

		return flexDAO.remove(flex);
	}


	/* (non-Javadoc)
	 * @see services.FlexibleService#findAllDTO()
	 */
	public List<FlexibleDTO> findAllDTO() {
		return flexDTODAO.findAll();
	}


	@Override
	public List<String> getAllCategories() {
		return flexDAO.getAllCategories();
	}
	

}
