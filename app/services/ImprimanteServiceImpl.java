/**
 * 
 */
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.dao.ImprimanteDAO;
import models.Imprimante;
/**
 * @author Eva
 *
 */
@Service
public class ImprimanteServiceImpl implements ImprimanteService {
	
	private ImprimanteDAO impDAO;
	
	@Autowired
	public ImprimanteServiceImpl(ImprimanteDAO impDAO){
		this.impDAO = impDAO;
	}

	/* (non-Javadoc)
	 * @see services.ImprimanteService#findForProduct(java.lang.String, int)
	 */
	public List<Imprimante> findForProduct(String type, int productId) {
		switch(type){
		case "Flexible" :
			return impDAO.findForFlexible(productId);		
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see services.ImprimanteService#findAll()
	 */
	public List<Imprimante> findAll() {
		return impDAO.findAll();
	}

	/* (non-Javadoc)
	 * @see services.ImprimanteService#save(models.Imprimante)
	 */
	public boolean save(Imprimante imp) {

		return impDAO.save(imp);
	}


	@Override
	public Imprimante findByCode(String codeSelection) {
		return impDAO.findByCode(codeSelection);
	}

	@Override
	public boolean add(Imprimante imp) {
		return impDAO.add(imp);
	}

	@Override
	public boolean remove(Imprimante imp) {
		return impDAO.remove(imp);
		
	}
	
	

}
