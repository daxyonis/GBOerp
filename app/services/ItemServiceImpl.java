package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import services.dao.FinitionDAO;
import services.dao.FlexibleDTODAO;
import services.dao.ImprimanteDAO;
import services.dao.ItemDAO;
import services.dao.LaminageDAO;
import services.dao.estimation.*;
import models.Flexible;
import models.FlexibleDTO;
import models.Item;
import models.SoumFlex;
import models.Imprimante;
import models.Finition;
import models.Laminage;
import models.SoumFlexItem;

/**
 * @author Eva
 *
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class ItemServiceImpl implements ItemService {
	
	private ItemDAO itemDAO;
	private AssemblageDAOImpl assemblageDAO;
	private Estimation cncDAO;
	private Estimation courrierDAO;
	private Estimation decoupeDAO;
	private Estimation demasquageDAO;
	private Estimation depEmballageDAO;
	private EmballageDAOImpl emballageDAO;
	private SoumInfographieDAOImpl infographieDAO;
	private Estimation installationDAO;
	private MaterielDAOImpl materielDAO;
	private Estimation menuiserieDAO;
	private NumeriqueDAOImpl numeriqueDAO;
	private Estimation peintureDAO;
	private Estimation sousTraitantDAO;
	private Estimation ventesDAO;
	private FlexibleDTODAO flexDtoDAO;
	private ImprimanteDAO imprimanteDAO;
	private FinitionDAO finitionDAO;	
	private LaminageDAO lamDAO;
	
	@Autowired
	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}
	
	@Autowired
	public void setAssemblage(AssemblageDAOImpl assemblageDAOImpl) {
		this.assemblageDAO = assemblageDAOImpl;
	}
	
	@Autowired
	public void setCnc(CncDAOImpl cncDAOImpl) {
		this.cncDAO = cncDAOImpl;
	}
	
	@Autowired
	public void setCourrier(CourrierDAOImpl courrierDAOImpl) {
		this.courrierDAO = courrierDAOImpl;
	}
	
	@Autowired
	public void setDecoupe(DecoupeDAOImpl decoupeDAOImpl) {
		this.decoupeDAO = decoupeDAOImpl;
	}
	
	@Autowired
	public void setDemasquage(DemasquageDAOImpl demasquageDAOImpl) {
		this.demasquageDAO = demasquageDAOImpl;
	}
	
	@Autowired
	public void setDepEmballage(DepEmballageDAOImpl depEmballageDAOImpl) {
		this.depEmballageDAO = depEmballageDAOImpl;
	}
	
	
	@Autowired
	public void setEmballage(EmballageDAOImpl emballageDAOImpl) {
		this.emballageDAO = emballageDAOImpl;
	}
	
	@Autowired
	public void setInfographie(SoumInfographieDAOImpl infographieDAOImpl) {
		this.infographieDAO = infographieDAOImpl;
	}
	
	@Autowired
	public void setInstallation(InstallationDAOImpl installationDAOImpl) {
		this.installationDAO = installationDAOImpl;
	}
	
	@Autowired
	public void setMateriel(MaterielDAOImpl materielDAOImpl) {
		this.materielDAO = materielDAOImpl;
	}
	
	@Autowired
	public void setMenuiserie(MenuiserieDAOImpl menuiserieDAOImpl) {
		this.menuiserieDAO = menuiserieDAOImpl;
	}
	
	@Autowired
	public void setNumerique(NumeriqueDAOImpl numeriqueDAOImpl) {
		this.numeriqueDAO = numeriqueDAOImpl;
	}
	
	@Autowired
	public void setPeinture(PeintureDAOImpl peintureDAOImpl) {
		this.peintureDAO = peintureDAOImpl;
	}
	
	@Autowired
	public void setSousTraitant(SousTraitantDAOImpl sousTraitantDAOImpl) {
		this.sousTraitantDAO = sousTraitantDAOImpl;
	}
	
	@Autowired
	public void setVentes(VentesDAOImpl ventesDAOImpl) {
		this.ventesDAO = ventesDAOImpl;
	}
	@Autowired
	public void setFlexDAO(FlexibleDTODAO flexDTODAO){
		this.flexDtoDAO = flexDTODAO;
	}
	@Autowired
	public void setImprimanteDAO(ImprimanteDAO imprimanteDAO){
		this.imprimanteDAO = imprimanteDAO;
	}
	@Autowired
	public void setFinitionDAO(FinitionDAO finitionDAO){
		this.finitionDAO = finitionDAO;
	}	
	@Autowired
	public void setLaminageDAO(LaminageDAO lamDAO){
		this.lamDAO = lamDAO;
	}
		
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public int [] copyItemsToSoum(List<Item> items, int noSoumissionCible){

		int[] oldNoItemArray = new int[items.size()];		
		
		// fill the oldItemArray
		for(int i=0; i<oldNoItemArray.length; i++){
			oldNoItemArray[i] = items.get(i).getNoItem();
		}
				
		// first we call itemDAO to copy Item info
		int[] newNoItemArray = itemDAO.copyItemInfoToSoum(oldNoItemArray, noSoumissionCible);
				
		
		if(newNoItemArray.length == oldNoItemArray.length){
			
			// then we call all the SoumXXDAOs to propagate the estimation info
			this.assemblageDAO.copyEstimation(oldNoItemArray, newNoItemArray);
			this.cncDAO.copyEstimation(oldNoItemArray, newNoItemArray);			
			this.courrierDAO.copyEstimation(oldNoItemArray, newNoItemArray);
			this.decoupeDAO.copyEstimation(oldNoItemArray, newNoItemArray);
			this.demasquageDAO.copyEstimation(oldNoItemArray, newNoItemArray);
			this.depEmballageDAO.copyEstimation(oldNoItemArray, newNoItemArray);
			this.emballageDAO.copyEstimation(oldNoItemArray, newNoItemArray);
			this.infographieDAO.copyEstimation(oldNoItemArray, newNoItemArray);
			this.installationDAO.copyEstimation(oldNoItemArray, newNoItemArray);
			this.materielDAO.copyEstimation(oldNoItemArray, newNoItemArray);
			this.menuiserieDAO.copyEstimation(oldNoItemArray, newNoItemArray);
			this.numeriqueDAO.copyEstimation(oldNoItemArray, newNoItemArray);
			this.peintureDAO.copyEstimation(oldNoItemArray, newNoItemArray);
			this.sousTraitantDAO.copyEstimation(oldNoItemArray, newNoItemArray);
			this.ventesDAO.copyEstimation(oldNoItemArray, newNoItemArray);
			
		}else {
			throw new RuntimeException();
		}
		
		return newNoItemArray;

	}

	/* (non-Javadoc)
	 * @see services.ItemService#create(int, models.SoumFlex)
	 */
	@Transactional(propagation=Propagation.MANDATORY, readOnly=false)
	public boolean create(int noSoumission, SoumFlex soumflex, SoumFlexItem soumflexItem) {
		
		// Get the FlexibleDTO 
		FlexibleDTO flexDTO = flexDtoDAO.findById(soumflexItem.getMatProd());
		Imprimante imprimante = imprimanteDAO.findByCode(soumflexItem.getImpSelect());
		Finition finition = finitionDAO.getById(soumflexItem.getFiniSelectType());	
		Laminage laminage = lamDAO.findById(soumflexItem.getLamSelectType());
		
		if(null == flexDTO){
			return false;	
		}
		
		// Create item and get its number
		int noItem = itemDAO.create(flexDTO.getFlex().getProduit(), 
									noSoumission, 
									flexDTO.getFlex().getDescription(),
									soumflexItem.getQte(),
									"Bo-Concept",  
									0.0, 
									0.0, 
									soumflexItem.getMontantVente(), 
									Double.toString(soumflexItem.getHaut()), 
									Double.toString(soumflexItem.getLarg()),
									soumflexItem.getRv());

		boolean success = (noItem > 0);
		
		String addToDescription = "";
		
		// If item created, add the materials and labour
		if(success){
		
			Flexible flex = flexDTO.getFlex();
			success = success && materielDAO.addNew(noItem, 
													flex.getNoItemGenius(),
													flex.getProduit(), 
													flexDTO.getMaxCost(),
													flexDTO.getUnit(), 
													soumflexItem.getPerte(),
													soumflex.getMargeGlobale(), 
													soumflexItem.getItemLarg(flexDTO.getUnit()), 
													soumflexItem.getItemHaut(flexDTO.getUnit()) * soumflexItem.getQte());
				
			if(finition != null && (finition.getId() > 0)){
				addToDescription += "; avec Finition";
				// Add finition as a material				
				success = success && materielDAO.addNew(noItem, 
											 "", 
											 finition.getType() + " " + soumflexItem.getFiniSelectCote(), 
											 finition.getPrix().doubleValue(), 
											 finition.getUnite(),
											 soumflexItem.getPerte(),
											 soumflex.getMargeGlobale(),
											 soumflexItem.getItemLarg(finition.getUnite()),
											 soumflexItem.getItemHaut(finition.getUnite()) * soumflexItem.getQte());
				success = success && materielDAO.addNew(noItem, "", "Cout minimum finition", finition.getCoutFixe(), 
														"UN", 0.0, soumflex.getMargeGlobale(), 1.0, 1.0);
				
			}
			
			if(laminage != null && (laminage.getId() > 0)){
				addToDescription += "; avec Laminage";
				// Add laminage first as a material...
				success = success && materielDAO.addNew(noItem,
														"",
														laminage.getNom(),
														laminage.getCoutPiCar(),
														"PI2",
														soumflexItem.getPerte(),
														soumflex.getMargeGlobale(),
														soumflexItem.getItemLarg("PI"),
														soumflexItem.getItemHaut("PI") * soumflexItem.getQte());
			}
													
			
			success = success && emballageDAO.addNew(noItem, 
													 "", 
													 "Emballage", 
													 soumflexItem.getMoCoutSac(),
													 "UN", 
													 0.0, 
													 1, 
													 soumflexItem.getQte());
			
			// Copier les temps ssi montantVente < max sale
			boolean copierLesTemps = (soumflex.MAX_SALE_AMOUNT - soumflex.getTotalVente() >= 0.0);
			if(copierLesTemps){
				if(imprimante != null){
					success = success && numeriqueDAO.createFromSoumFlex(noItem, soumflex, soumflexItem, imprimante);
				}
				success = success && infographieDAO.createFromSoumFlex(noItem, soumflex, soumflexItem);			
				success = success && assemblageDAO.addNew(noItem, "Manipulation", soumflexItem.getMoTaux(), 
														  soumflexItem.getMoNbrHeure(),
														  soumflex.getMargeGlobale(), "");
				
				if(laminage != null && laminage.getId() > 0){
					// Add laminage time				
					success = success && assemblageDAO.addNew(noItem, "Laminage", soumflexItem.getLamTaux(), 
							  soumflexItem.getLamTempsHr(), soumflex.getMargeGlobale(), laminage.getNom());
					success = success && assemblageDAO.addNew(noItem, "Laminage", laminage.LAMINAGE_MIN_TARIF, 
							  1.0, soumflex.getMargeGlobale(), "Ajout du cout minimum");
				}
			}
			
		}
		if(success && !addToDescription.isEmpty()){
			// Mettre à jour la description de l'item
			Item newItem = itemDAO.getItem(noItem);
			newItem.setDescription(newItem.getDescription() + addToDescription);
			itemDAO.update(newItem);			
		}
		return success;
	}

	/* (non-Javadoc)
	 * @see services.ItemService#findForSoum(int)
	 */
	public List<Item> findForSoum(int noSoumission) {		
		return itemDAO.findAllItemsFromSoum(noSoumission);
	}

	
}
