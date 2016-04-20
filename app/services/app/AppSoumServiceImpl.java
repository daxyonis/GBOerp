package services.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import models.Client;
import models.Rep;
import models.app.AppItem;
import models.app.AppSoumEntete;
import models.app.AppSoumission;
import play.Logger;
import services.ClientService;
import services.RepService;
import services.app.dao.AppSoumEnteteDAO;
import services.app.dao.AppSoumissionDAO;

@Service
@Transactional
public class AppSoumServiceImpl implements AppSoumService {
	
	private String[] statut = {"En soumission","Dessin nécessaire","Dessin terminé","Prêt pour production",
            				   "En production", "Production terminé", "Prêt pour facturation", "Facturation complétée",
            				   "Catalogue"};
	
	private AppSoumissionDAO appSoumDAO;
	private AppSoumEnteteDAO appSoumEnteteDAO;
	
	private ClientService clientService;
	private RepService repService;
	private AppItemService itemService;
	
	@Autowired
	public void setAppSoumEnteteDAO(AppSoumEnteteDAO appSoumEnteteDAO){
		this.appSoumEnteteDAO = appSoumEnteteDAO;
	}

	@Autowired
	public void setAppSoumDAO(AppSoumissionDAO appSoumDAO){
		this.appSoumDAO = appSoumDAO;
	}	
	@Autowired
	public void setClientService(ClientService cs){
		this.clientService = cs;
	}
	
	@Autowired
	public void setRepService(RepService rs){
		this.repService = rs;
	}
	
	@Autowired
	public void setItemService(AppItemService is){
		this.itemService = is;
	}
	
	
	
	@Override
	public String getStatut(int statusCode) {
		if(statusCode < 1 || statusCode > statut.length){
			throw new IllegalArgumentException();
		}
		return statut[statusCode-1];
	}

	private void setSoumTexteStatut(AppSoumission soum){
		if(soum.getStatut() > 0)
			soum.setTexteStatut(this.statut[soum.getStatut()-1]);
	}
	
	private void setSoumTexteStatut(List<AppSoumission> soums){
		for(AppSoumission s : soums){
			if(s.getStatut() > 0)
				s.setTexteStatut(this.statut[s.getStatut()-1]);
		}
	}
	/**************************************************
	 * Methods from AppSoumService
	 */
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, isolation=Isolation.DEFAULT)
	public Optional<AppSoumission> findByNo(int noSoum, boolean withItems) {
		
		Optional<AppSoumission> appSoum = appSoumDAO.findByNo(noSoum);
		
		if(appSoum.isPresent()){
			AppSoumission soum = appSoum.get();
			if(withItems){
				// Get the items
				soum.items = itemService.findAllForSoum(noSoum);
			}			
			soum.client = clientService.findByNo(soum.getEntete().getGeniusNoClient());
			soum.rep    = repService.findByCode(soum.getEntete().getNoVendeur());
			this.setSoumTexteStatut(soum);
			soum.entete.setClientSoum(soum.client.getNom());
			soum.entete.setNomVendeur(soum.rep.getDescription1());
		}
		
		return appSoum;
	}
	
		
	@Override
	public List<AppSoumission> findBySuite(int suite){
	    List<AppSoumission> soums = appSoumDAO.findBySuite(suite);
	    for(AppSoumission s : soums){
		s.items = itemService.findAllForSoum(s.getNoSoumission());
		s.client = clientService.findByNo(s.getEntete().getGeniusNoClient());
		s.rep = repService.findByCode(s.getEntete().getNoVendeur());
		this.setSoumTexteStatut(s);
		s.entete.setClientSoum(s.client.getNom());
		s.entete.setNomVendeur(s.rep.getDescription1());
	    }
	    return soums;
	}

	@Override
	public int create(AppSoumission soum){
		return appSoumDAO.create(soum);
	}
	
	@Override
	public boolean update(AppSoumission soum) {			
		return appSoumDAO.update(soum);
	}		

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, isolation=Isolation.DEFAULT)
	public boolean delete(int noSoum) {				
		
		// first delete the items
		Optional<AppSoumission> soumOpt = this.findByNo(noSoum, true);
		
		if(soumOpt.isPresent()){
		
			boolean success = true;
			if(soumOpt.get().items != null){
				for(AppItem item : soumOpt.get().items){
					success = success && itemService.remove(item);
				}
			}
			
			success = success && appSoumDAO.delete(noSoum);
			return success;
		}
		else{
			return false;
		}		
	}		

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, isolation=Isolation.DEFAULT)
	public int createCopy(AppSoumission soum, boolean copyItems) {				

		// First create new in repository
		int newNo = appSoumDAO.create(soum);
		
		// Query new soum
		Optional<AppSoumission> newSoumOpt = this.findByNo(newNo, false);
		if(newSoumOpt.isPresent()){
			
			AppSoumission newSoum = newSoumOpt.get();
			
			// Copy relevant data
			newSoum.setCommission(soum.getCommission());
			newSoum.rep = new Rep(soum.rep);
			newSoum.client = new Client(soum.client);
			newSoum.entete = new AppSoumEntete(soum.entete);
			newSoum.entete.setNoSoumission(newNo);
			
			// Update soum and entete
			boolean success = this.update(newSoum);
			success = success && this.updateEntete(newSoum.entete);
			
			// Handle the items
			if(success){
				if(copyItems && soum.items != null){
					newSoum.items = new ArrayList<AppItem>();
					for(AppItem item : soum.items){
						AppItem newItem = new AppItem(item);
						newItem.setNoSoumission(newNo);						
						int newItemNo = itemService.add(newItem);
						newItem.setNoItem(newItemNo);
						itemService.save(newItem);
						newSoum.items.add(newItem);
					}
				}
				else{
					// empty the items
					soum.items = null;
				}	
			}
			else{
				// Better delete it and return 0
				this.delete(newNo);
				return 0;
			}
						
		}	
		return newNo;
	}
		

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, isolation=Isolation.DEFAULT)
	public int createCopyVersion(int suite, String version) {
		
		List<AppSoumission> soums = this.findBySuite(suite);
		
		// Find the right AppSoumission to copy
		Optional<AppSoumission> soum2copy = Optional.ofNullable( soums.size() > 0 ? soums.get(0) : null );
		if(!version.isEmpty()){
		    soum2copy = soums.stream()
			    	     .filter(s -> s.getVersion().equalsIgnoreCase(version))
			    	     .findAny();
		}
		
		// Create a copy -- but with same suite
		if(soum2copy.isPresent()){		    
		    int newNo = appSoumDAO.createNewVersion(soum2copy.get());
		    
		    // Query new soum
		    Optional<AppSoumission> newSoumOpt = this.findByNo(newNo, false);
		    if(newSoumOpt.isPresent()){
			AppSoumission newSoum = newSoumOpt.get();
			AppSoumission soum = soum2copy.get();
			
			// Set a few things
			soum.setCommission(soum.getCommission());			
			newSoum.rep = new Rep(soum.rep);
			newSoum.client = new Client(soum.client);
			newSoum.entete = new AppSoumEntete(soum.entete);
			newSoum.entete.setNoSoumission(newNo);
			
			// Update soum and entete
			boolean success = this.update(newSoum);
			success = success && this.updateEntete(newSoum.entete);
			
			// Handle the items
			if(success){
			    if(!version.isEmpty() && soum.items != null){
				newSoum.items = new ArrayList<AppItem>();
				for(AppItem item : soum.items){
				    AppItem newItem = new AppItem(item);
				    newItem.setNoSoumission(newNo);						
				    int newItemNo = itemService.add(newItem);
				    newItem.setNoItem(newItemNo);
				    success = success && itemService.save(newItem);
				    newSoum.items.add(newItem);
				}
			    }
			    else{
				// empty the items
				soum.items = null;
			    }	
			}
			if(success){
			    return newNo;			    
			}
			else{
			    // Better delete it and return 0
			    this.delete(newNo);
			    return 0;
			}
			
		    } 
		    else{
			Logger.debug(String.format("New soumission cannot be found, noSoum = %d",newNo));
			return 0;
		    }		    
		}
		else{
		    Logger.debug(String.format("No soumission to copy, suite = %d version = %s", suite, version));
		    return 0;
		}
					
	}

	@Override
	public Optional<AppSoumEntete> findEnteteByNo(int noSoum) {
		return appSoumEnteteDAO.findByNo(noSoum);
	}

	@Override	
	public boolean updateEntete(AppSoumEntete entete) {
		// Update client and rep names
		Client client = clientService.findByNo(entete.getGeniusNoClient());
		Rep rep 	  = repService.findByCode(entete.getNoVendeur());
		entete.setClientSoum(client.getNom());
		entete.setNomVendeur(rep.getDescription1());
		return appSoumEnteteDAO.update(entete);
	}
	
	
	@Override
	public List<AppSoumission> getPage(String search, String sort, String order, int pageSize, int pageNumber,
									String repNo, int statusCode, int numero,
									String minDate, String maxDate) {
		List<AppSoumission> soums = appSoumDAO.getPage(search, sort, order, pageSize, pageNumber, 
												 repNo, statusCode, numero, minDate, maxDate);
		setSoumTexteStatut(soums);		
		return soums;
	}

	@Override
	public int countAll(String search, String repNo, int statusCode, int numero, String minDate, String maxDate) {
		return appSoumDAO.countAll(search, repNo, statusCode, numero, minDate, maxDate);
	}	
	

}
