import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import models.app.AppItem;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import services.app.AppItemService;

public class AppItemServiceTest extends AbstractIntegrationTest {
	
	private static AppItemService appItemService;
	
	@Autowired
	public void setAppItemService(AppItemService ais){
		this.appItemService = ais;
	}

	
	private void checkItem235184(AppItem item){
		assertEquals(235184, item.getNoItem());
		assertEquals(2, item.getNoOrdre());
		assertEquals("Livraison.", item.getNomItem());
		
		assertTrue(item.getDescription().indexOf("5640, rue Paré") > 0);
		assertTrue(item.getDescription().indexOf("Ville Mont-Royal") > 0);
		assertTrue(item.getDescription().indexOf("QC H4P 2M1") > 0);
		
		assertEquals(1, item.getQuantite());
		assertEquals(null, item.getRectoVerso());
		assertEquals("Bo-Concept", item.getSourceProd());
		assertEquals("02", item.getCodeCatalogue());
		assertEquals(false, item.isCatalogue());
		assertEquals("P:\\Soumission\\Formulaires\\AucuneImage.jpg", item.getFichierSource());
		assertTrue(item.getFraisVariables() <= Double.MIN_VALUE);
		assertTrue(item.getFraisInstallation() <= Double.MIN_VALUE);
		assertTrue(25.00 - item.getPrixFab() <= Double.MIN_VALUE);
		assertEquals("0", item.getLargeur());
		assertEquals("0", item.getHauteur());
		assertEquals(null, item.getNotes());
		assertEquals(null, item.getNotesInternes());
		assertEquals(null, item.getSousCategorie());
		assertEquals(0, item.getGeniusDetailID());
		assertEquals(1, item.getFlagDetail());								
		assertTrue(item.getFamille().indexOf("TRANSP") == 0);
		assertEquals(null, item.getFacturation());
		assertEquals(235184, item.getGeniusItem());	
	}
	
	private void checkItem235183(AppItem item){
		assertEquals(235183, item.getNoItem());
		assertEquals(1, item.getNoOrdre());
		assertEquals("Affiches déjà produit et en stock à livrer avec 2 S hooks .", item.getNomItem());
		assertEquals("Affiches  imprimés en carton 24 pts  .\r\n1 x 24'' x 32'' \r\n2 x 8 1/2'' x 11'' \r\n\r\nQuincailleries :2 S hooks , ceux qu’on utilise pour les Openings de magasin.\r\n\r\nEmballage: entre deux cartons corrugé.",
					 item.getDescription());
		assertEquals(3, item.getQuantite());
		assertEquals("R/V", item.getRectoVerso());
		assertEquals("Tempo", item.getSourceProd());
		assertEquals("01", item.getCodeCatalogue());
		assertEquals(false, item.isCatalogue());
		assertEquals("P:\\Soumission\\Formulaires\\AucuneImage.jpg", item.getFichierSource());
		assertTrue(item.getFraisVariables() <= Double.MIN_VALUE);
		assertTrue(item.getFraisInstallation() <= Double.MIN_VALUE);
		assertTrue(75.00 - item.getPrixFab() <= Double.MIN_VALUE);
		assertEquals("5", item.getLargeur());
		assertEquals("7", item.getHauteur());
		assertTrue(item.getNotes().indexOf("* Si question voir Marc pour plus d'infos.") >= 0);
		assertTrue(item.getNotes().indexOf("* Aucune identification du Groupe Bo Concept sur l'emballage.") >= 0);
		
		assertEquals(null, item.getNotesInternes());
		assertEquals(null, item.getSousCategorie());
		assertEquals(0, item.getGeniusDetailID());
		assertEquals(1, item.getFlagDetail());								
		
		assertTrue(item.getFamille().indexOf("TEMPO")==0);
		
		assertEquals(null, item.getFacturation());
		assertEquals(235183, item.getGeniusItem());	
	}
	
	// TEST is based on AppSoumission #37453 with items #235184 and #235183
	@Test
	public void findByNoTest(){
		Optional<AppItem> item = appItemService.findByNo(235184);		
		assertTrue(item.isPresent());
		
		checkItem235184(item.get());		
	}
	
	@Test
	public void findAllForSoumTest(){
		List<AppItem> items = appItemService.findAllForSoum(37453);
		
		assertTrue(items.size() == 2);
		
		Optional<AppItem> item235184 = items.stream()
									  		.filter(i -> i.getNoItem() == 235184)
									  		.findFirst();
		
		Optional<AppItem> item235183 = items.stream()
									  		.filter(i -> i.getNoItem() == 235183)
									  		.findFirst();
		assertTrue(item235184.isPresent());
		assertTrue(item235183.isPresent());
		
		checkItem235184(item235184.get());
		checkItem235183(item235183.get());		
	}
	
	@Test
	public void addTest(){
		AppItem newItem = new AppItem();
		newItem.setNoSoumission(37453);
		newItem.setNomItem("Item de test par Eva Maciejko -- 26022016");
		newItem.setDescription("Cet item à des fins de test seulement!");
		newItem.setHauteur("44");
		newItem.setLargeur("22");		
		newItem.setQuantite(2);
		newItem.setPrixFab(100.375);
		
		int newNoItem = appItemService.add(newItem);
		
		assertTrue(newNoItem > 0);
		
		// Check the items
		List<AppItem> items = appItemService.findAllForSoum(37453);		
		assertTrue(items.size() == 3);				
		
		Optional<AppItem> thirdItem = items.stream()
									  		.filter(i -> i.getNoItem() == newNoItem)
									  		.findFirst();
		
		assertTrue(thirdItem.isPresent());
				
		AppItem itemAdded =  thirdItem.get();
		assertEquals(itemAdded.getNoSoumission(), newItem.getNoSoumission());
		assertEquals(itemAdded.getNomItem(), newItem.getNomItem());
		assertEquals(itemAdded.getDescription(), newItem.getDescription());
		assertEquals(itemAdded.getHauteur(), newItem.getHauteur());
		assertEquals(itemAdded.getLargeur(), newItem.getLargeur());
		assertEquals(itemAdded.getQuantite(), newItem.getQuantite());
		assertTrue(Math.abs(itemAdded.getPrixFab() - newItem.getPrixFab()) <= Double.MIN_VALUE);
		assertEquals(itemAdded.getFichierSource(), newItem.getFichierSource());
		assertEquals(itemAdded.getSourceProd(), newItem.getSourceProd());
		
		
	}
	
	@Test
	public void saveTest(){
		List<AppItem> items = appItemService.findAllForSoum(37453);
		AppItem itemModified = items.get(0);
		itemModified.setDescription("bidon");
		itemModified.setFamille("TRANSP    ");
		itemModified.setFacturation("description pour bidon facturation");
		itemModified.setFraisInstallation(110.50);
		itemModified.setFraisVariables(307.91);
		itemModified.setPrixFab(1804.17);
		itemModified.setQuantite(12);
		itemModified.setRectoVerso("R/V");
		
		boolean success = appItemService.save(itemModified);		
		assertTrue(success);
		
		Optional<AppItem> item = appItemService.findByNo(itemModified.getNoItem());
		assertTrue(item.isPresent());
		AppItem itemToCheck = item.get();
		
		// Check fields that were modified		
		assertEquals(itemModified.getDescription(), itemToCheck.getDescription());
		assertEquals(itemModified.getFamille(), itemToCheck.getFamille());
		assertEquals(itemModified.getFacturation(), itemToCheck.getFacturation());
		assertTrue(Math.abs(itemModified.getFraisVariables() - itemToCheck.getFraisVariables()) < Double.MIN_VALUE);
		assertTrue(Math.abs(itemModified.getFraisVariables() - itemToCheck.getFraisVariables()) < Double.MIN_VALUE);
		assertTrue(Math.abs(itemModified.getPrixFab() - itemToCheck.getPrixFab()) < Double.MIN_VALUE);
		assertEquals(itemModified.getQuantite(), itemToCheck.getQuantite());
		assertEquals(itemModified.getRectoVerso(), itemToCheck.getRectoVerso());
		
		// Also check fields that were not modified
		assertEquals(itemModified.getNoSoumission(), itemToCheck.getNoSoumission());
		assertEquals(itemModified.getFichierSource(), itemToCheck.getFichierSource());
		assertEquals(itemModified.getHauteur(), itemToCheck.getHauteur());
		assertEquals(itemModified.getLargeur(), itemToCheck.getLargeur());
		assertEquals(itemModified.getNomItem(), itemToCheck.getNomItem());
		assertEquals(itemModified.getNotes(), itemToCheck.getNotes());
		assertEquals(itemModified.getSourceProd(), itemToCheck.getSourceProd());
		assertEquals(itemModified.getCodeCatalogue(), itemToCheck.getCodeCatalogue());
		assertEquals(itemModified.getFlagDetail(), itemToCheck.getFlagDetail());
		assertEquals(itemModified.getGeniusDetailID(),  itemToCheck.getGeniusDetailID());
		assertEquals(itemModified.getGeniusItem(), itemToCheck.getGeniusItem());
		assertEquals(itemModified.getNoOrdre(), itemToCheck.getNoOrdre());
		assertEquals(itemModified.getNotesInternes(), itemToCheck.getNotesInternes());
		
	}
	
	@Test
	public void removeTest(){
		List<AppItem> items = appItemService.findAllForSoum(37453);
		AppItem item = items.get(0);
		
		boolean success = appItemService.remove(item);
		assertTrue(success);
		
		items = appItemService.findAllForSoum(37453);
		assertTrue(items.size() == 1);
		assertTrue(items.get(0).getNoItem() != item.getNoItem());
		
		Optional<AppItem> removedItem = appItemService.findByNo(item.getNoItem());
		assertTrue(!removedItem.isPresent());
	}
}
