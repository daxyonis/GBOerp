/**
 * 
 */
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import models.Client;
import models.SoumFlex;
import models.SoumFlexItem;
import models.Soumission;
import services.dao.SoumissionDAO;
/**
 * This class implements SoumService and delegates to DAO layers
 * @author Eva
 *
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class SoumServiceImpl implements SoumService {

	private String[] statut = {"En soumission","Dessin nécessaire","Dessin terminé","Prêt pour production",
	                         "En production", "Production terminé", "Prêt pour facturation", "Facturation complétée",
	                         "Catalogue"};
	private SoumissionDAO soumDAO;
	private ItemService itemService;
	private ClientService clientService;
	private RepService repService;
	
	@Autowired
	public void setSoumDAO(SoumissionDAO soumDAO){
		this.soumDAO = soumDAO;
	}		
	
	@Autowired
	public void setItemService(ItemService is){
		this.itemService = is;
	}
	
	@Autowired
	public void setClientService(ClientService cs){
		this.clientService = cs;
	}
	
	@Autowired
	public void setRepService(RepService rs){
		this.repService = rs;
	}
	
	private void setSoumTexteStatut(Soumission soum){
		if(soum.getStatut() > 0)
			soum.setTexteStatut(this.statut[soum.getStatut()-1]);
	}
	
	private void setSoumTexteStatut(List<Soumission> soums){
		for(Soumission s : soums){
			if(s.getStatut() > 0)
				s.setTexteStatut(this.statut[s.getStatut()-1]);
		}
	}
	/**************************************************
	 * Methods from SoumissionDAO that are delegated
	 */
	public List<Soumission>	findByKeyword(SoumissionDAO.KeyFindSoumEnum key, 
										  String word,
										  String minDate,
										  String maxDate,
										  String clientNo,
										  String repNo){
		List<Soumission> soums = soumDAO.findByKeyword(key, word, minDate, maxDate, clientNo, repNo);
		this.setSoumTexteStatut(soums);
		return soums;
	}
	
	public List<Soumission> findTop(int numrows, SoumissionDAO.KeyFindSoumEnum orderBy){
		List<Soumission> soums = soumDAO.findTop(numrows, orderBy);
		this.setSoumTexteStatut(soums);
		return soums;
	}
	
	public Soumission findByNo(int noSoum) {
		Soumission soum = soumDAO.findByNo(noSoum);
		if(soum != null){
			soum.items 	= itemService.findForSoum(noSoum);
			soum.client = clientService.findByNo(soum.getGeniusNoClient());
			soum.rep 	= repService.findByCode(soum.getNoVendeur());
			this.setSoumTexteStatut(soum);
		}
		return soum;
	}
	/**************************************************/

	/* (non-Javadoc)
	 * @see services.SoumService#create(models.SoumFlex)
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, isolation=Isolation.DEFAULT)
	public int create(SoumFlex soum) {
		
		Integer commission = new Integer(soum.getInputCommission());						
		
		int newNoSoumission = soumDAO.create(soum.getInputProjet(), soum.getSelectRepresentant(), soum.getSelectClient(), 
				soum.getInputContactNom(), soum.getInputContactTel(), soum.getInputContactEmail(),			    
				soum.getInputDateEstimation(), soum.getInputDate(), commission.floatValue(), soum.getTotalVente());
		
		if(newNoSoumission > 0){
			
			// Copy client info
			Client client = clientService.findByNo(soum.getSelectClient());
			soumDAO.copyClientInfo(newNoSoumission, client);
			
			// create the items
			for(SoumFlexItem item : soum.items){
				boolean itemCreated = itemService.create(newNoSoumission, soum, item);
				if(!itemCreated)
					return 0;
			}
		}
		
		return newNoSoumission;
	}


	@Override
	public List<Soumission> getPage(String search, String sort, String order, int pageSize, int pageNumber,
									String repNo, int statusCode, int numero,
									String minDate, String maxDate) {
		List<Soumission> soums = soumDAO.getPage(search, sort, order, pageSize, pageNumber, 
												 repNo, statusCode, numero, minDate, maxDate);
		setSoumTexteStatut(soums);		
		return soums;
	}

	@Override
	public int countAll(String search, String repNo, int statusCode, int numero, String minDate, String maxDate) {
		return soumDAO.countAll(search, repNo, statusCode, numero, minDate, maxDate);
	}
	
}
