import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;
import models.app.AppSoumEntete;
import models.app.AppSoumission;
import models.app.AppItem;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import services.app.AppItemService;
import services.app.AppSoumService;

public class AppSoumServiceTest extends AbstractIntegrationTest {

	private static AppSoumService appSoumService;
	private static AppItemService appItemService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	
	@Autowired
	public void setAppSoumService(AppSoumService ss){
		this.appSoumService = ss;
	}
	
	@Autowired
	public void setAppItemService(AppItemService is){
		this.appItemService = is;
	}
	
	@Test
	public void getStatutTest(){
		String[] statut = {"En soumission","Dessin nécessaire","Dessin terminé","Prêt pour production",
				   "En production", "Production terminé", "Prêt pour facturation", "Facturation complétée",
				   "Catalogue"};
		
		for(int code = 1; code <= statut.length; code ++){
			assertEquals(statut[code-1], appSoumService.getStatut(code));
		}
	}	
	
	@Test
	public void findByNoTest(){
		Optional<AppSoumission> soum = appSoumService.findByNo(42102, false);
		assertTrue(soum.isPresent());
		assertEquals(42102, soum.get().getNoSoumission());
		AppSoumEntete entete = soum.get().getEntete();
		assertTrue(entete != null);		
		assertEquals(42102, entete.getNoSoumission());
	}
	
	@Test
	public void createTest(){
		AppSoumission soum = new AppSoumission();	
//		"Projet","NoVendeur","NomVendeur",// champs obligatoires
//		  "GeniusNoClient","DateEntre",		// champs obligatoires
//	  	  "Commission"
		soum.entete.setProjet("Projet bidon 00050041");
		soum.entete.setNoVendeur("14");			
		soum.entete.setNomVendeur("Nadine Duhamel");
		Date today = new Date();		
		soum.entete.setDateEntre(today);
		soum.setCommission((float)2.5);
		
		int newNoSoum = appSoumService.create(soum);		
		assertTrue(newNoSoum > 0);
		
		Optional<AppSoumission> newSoumOpt = appSoumService.findByNo(newNoSoum, true);		
		assertTrue(newSoumOpt.isPresent());
		assertTrue(newSoumOpt.get().getNoSoumission() > 0);
		AppSoumission newSoum = newSoumOpt.get(); 
		assertEquals(soum.entete.getProjet() , newSoum.entete.getProjet());
		assertEquals(soum.entete.getNoVendeur(),newSoum.entete.getNoVendeur());
		assertEquals(soum.entete.getNomVendeur(),newSoum.entete.getNomVendeur());
		assertEquals(soum.entete.getGeniusNoClient(),newSoum.entete.getGeniusNoClient());
		assertEquals(sdf.format(today), sdf.format(newSoum.entete.getDateEntre()));
		assertTrue(Math.abs(soum.getCommission() - newSoum.getCommission()) < Float.MIN_VALUE);
		
	}		
	
	@Test
	public void updateTest(){
		Optional<AppSoumission> soumOpt = appSoumService.findByNo(42102, false);
		assertTrue(soumOpt.isPresent());
		AppSoumission soum = soumOpt.get();			
		
		Date today = new Date();
			
		soum.setCommission((float)1.2);
		soum.setDateProductionDebut(today);
		soum.setDateProductionNecessaire(today);
			
		soum.setFlagHeader(1);
		soum.setFlex(3);
		soum.setGeniusNoCom("00054041");
		soum.setNoDossier(54041);
		soum.setNoteInterne("Vade mecum.");
		soum.setStatut(3);	
		
		boolean success = appSoumService.update(soum);
		assertTrue(success);
		
		soumOpt = appSoumService.findByNo(42102, false);
		assertTrue(soumOpt.isPresent());
		AppSoumission soum2 = soumOpt.get();	
	
		assertTrue(Math.abs(soum.getCommission() - soum2.getCommission()) <= Float.MIN_VALUE);
		assertEquals(sdf.format(soum.getDateProductionDebut()), sdf.format(soum2.getDateProductionDebut()));
		assertEquals(sdf.format(soum.getDateProductionNecessaire()), sdf.format(soum2.getDateProductionNecessaire()));
		assertEquals(soum.getFlagHeader(), soum2.getFlagHeader());
		assertEquals(soum.getFlex(), soum2.getFlex());
		assertEquals(soum.getGeniusNoCom(), soum2.getGeniusNoCom());
		assertEquals(soum.getNoDossier(), soum2.getNoDossier());
		assertEquals(soum.getNoteInterne(), soum2.getNoteInterne());
		assertEquals(soum.getStatut(), soum2.getStatut());
		assertEquals(appSoumService.getStatut(3), soum2.getTexteStatut());
			
	}
	
	@Test
	public void deleteTest(){
		boolean success = appSoumService.delete(43710);
		assertTrue(success);
		Optional<AppSoumission> soumOpt = appSoumService.findByNo(43710, false);
		assertTrue(!soumOpt.isPresent());
		
		// La soum 43710 a les items suivants : 242626, 242627, 242628
		// on s'assure qu'ils ont bien ete supprimes eux aussi
		List<AppItem> items = appItemService.findAllForSoum(43710);
		assertTrue(items == null || items.isEmpty());
		
		int[] itemNos = {242626, 242627, 242628};
		for(int i = 0; i<itemNos.length; i++){
			Optional<AppItem> item = appItemService.findByNo(itemNos[i]);
			assertTrue(!item.isPresent());
		}
		
		
	}
	
	@Test
	public void createCopyTest(){
		Optional<AppSoumission> soumOpt = appSoumService.findByNo(43710, true);
		assertTrue(soumOpt.isPresent());
		
		int newNo = appSoumService.createCopy(soumOpt.get(), true);
		assertTrue(newNo > 0);
		
		Optional<AppSoumission> soum2Opt = appSoumService.findByNo(newNo, true);
		assertTrue(soum2Opt.isPresent());
		
		AppSoumission soum = soumOpt.get();
		AppSoumission soum2 = soum2Opt.get();
		
		// Some fields should be different
		assertNotEquals(soum.getNoSoumission(), soum2.getNoSoumission());
		assertNotEquals(soum.entete.getNoSoumission(), soum2.entete.getNoSoumission());
		assertNotEquals(soum.getSuite(), soum2.getSuite());
		
		// Some fields should be the same
		assertTrue(Math.abs(soum.getCommission() - soum2.getCommission()) <= Float.MIN_VALUE);				
		assertEquals(soum.client.getNoClient(), soum2.client.getNoClient());
		assertEquals(soum.rep.getSalesmanCode(), soum2.rep.getSalesmanCode());
		
		// Check the entete
		AppSoumEntete entete = soum.entete;
		AppSoumEntete entete2 = soum2.entete;
		
		assertEquals(entete.getBanniereClient(), entete2.getBanniereClient());
		assertEquals(entete.getClientAdresse(), entete2.getClientAdresse());
		assertEquals(entete.getClientAPP(), entete2.getClientAPP());
		assertEquals(entete.getClientCP(), entete2.getClientCP());
		assertEquals(entete.getClientEmail(), entete2.getClientEmail());
		assertEquals(entete.getClientFactureA(), entete2.getClientFactureA());
		assertEquals(entete.getClientFax(), entete2.getClientFax());
		assertEquals(entete.getClientPays(), entete2.getClientPays());
		assertEquals(entete.getClientProvince(), entete2.getClientProvince());
		assertEquals(entete.getClientSoum(), entete2.getClientSoum());
		assertEquals(entete.getClientTelephone(), entete2.getClientTelephone());
		assertEquals(entete.getClientTelephoneContact(), entete2.getClientTelephoneContact());
		assertEquals(entete.getClientVille(), entete2.getClientVille());
		assertEquals(entete.getContact(), entete2.getContact());
		assertEquals(entete.getCritere(), entete2.getCritere());
		assertNotEquals(sdf.format(entete.getDateEntre()), sdf.format(entete2.getDateEntre()));
		assertNotEquals(entete.getDateLivraison(), entete2.getDateLivraison());
		assertEquals(entete.getDescription(), entete2.getDescription());
		assertEquals(entete.getGeniusNoClient(), entete2.getGeniusNoClient());
		assertNotEquals(entete.getNoSoumission(), entete2.getNoSoumission());
		assertEquals(entete.getNoVendeur(), entete2.getNoVendeur());
		assertEquals(entete.getNomVendeur(), entete2.getNomVendeur());
		assertEquals(entete.getProjet(), entete2.getProjet());
	
		// items should be the same except for id
		assertTrue(soum.items != null);
		assertTrue(soum2.items != null);		
		assertEquals(soum.items.size(), soum2.items.size());
		
		// Get list of items and compare them
		List<AppItem> items = soum.items;
		List<AppItem> items2 = soum2.items;
		items.sort(comparing(AppItem::getNomItem));
		items2.sort(comparing(AppItem::getNomItem));
		
		for(int i=0; i<items.size(); i++){
			AppItem item = items.get(i);
			AppItem item2 = items2.get(i);
			
			assertNotEquals(item.getNoItem(), item2.getNoItem());
			assertEquals(item.getNomItem(), item2.getNomItem());
			assertEquals(item.getDescription(), item2.getDescription());
			assertEquals(item.getFacturation(), item2.getFacturation());
			assertEquals(item.getFamille(), item2.getFamille());
			assertEquals(item.getFichierSource(), item2.getFichierSource());
			assertEquals(item.getFlagDetail(), item2.getFlagDetail());
			assertEquals(item.getFormattedPrixFab(), item2.getFormattedPrixFab());
			assertTrue(Math.abs(item.getFraisInstallation()- item2.getFraisInstallation()) < Double.MIN_VALUE);
			assertTrue(Math.abs(item.getFraisVariables()- item2.getFraisVariables()) < Double.MIN_VALUE);
			assertEquals(item.getGeniusDetailID(), item2.getGeniusDetailID());
			assertEquals(item.getGeniusItem(), item2.getGeniusItem());
			assertEquals(item.getHauteur(), item2.getHauteur());
			assertEquals(item.getLargeur(), item2.getLargeur());
			assertEquals(item.getNoOrdre(), item2.getNoOrdre());
			assertEquals(item.getNotes(), item2.getNotes());
			assertEquals(item.getNotesInternes(), item2.getNotesInternes());
			assertTrue(Math.abs(item.getPrixFab() - item2.getPrixFab()) < Double.MIN_VALUE);
			assertEquals(item.getQuantite(), item2.getQuantite());
			assertEquals(item.getRectoVerso(), item2.getRectoVerso());
			assertEquals(item.getSourceProd(), item2.getSourceProd());
			assertEquals(item.getSousCategorie(), item2.getSousCategorie());			
			assertEquals(item.getCodeCatalogue(), item2.getCodeCatalogue());			
		}
	}
	
	@Test
	public void findEnteteByNoTest(){
		Optional<AppSoumEntete> entete = appSoumService.findEnteteByNo(42102);
		assertTrue(entete.isPresent());		
		assertEquals(42102, entete.get().getNoSoumission());
	}
	
	@Test
	public void updateEnteteTest(){
		Optional<AppSoumEntete> appEntete = appSoumService.findEnteteByNo(42102);
		
		assertTrue(appEntete.isPresent());
		
		AppSoumEntete entete = appEntete.get();
		Date today = new Date();
		
		entete.setProjet("TEST " + today.toString());
		entete.setDateLivraison("2016-02-15");
		entete.setDateEntre(today);
		entete.setCritere("TEST-EVA-PLAY-JAVA " + today.toString());
		entete.setBanniereClient("banniereClient");
		entete.setClientAdresse("clientAdresse");
		entete.setClientAPP("clientAPP");
		entete.setClientCP("clientCP");
		entete.setClientEmail("clientEmail");
		entete.setClientFactureA("clientFactureA");
		entete.setClientFax("clientFax");
		entete.setClientPays("clientPays");
		entete.setClientProvince("clientProvince");
		entete.setClientSoum("clientSoum");
		entete.setClientTelephone("clientTelephone");
		entete.setClientTelephoneContact("clientTelephoneContact");
		entete.setClientVille("clientVille");
		entete.setContact("contact");
		entete.setDescription("description");
		entete.setGeniusNoClient("TEST");
		entete.setNoVendeur("99");		
		
		boolean success = appSoumService.updateEntete(entete);
		assertTrue(success);
		
		// reload and check the values
		Optional<AppSoumEntete> appEntete2 = appSoumService.findEnteteByNo(42102);
		
		assertTrue(appEntete2.isPresent());
		AppSoumEntete entete2 = appEntete2.get();
		
		assertEquals(entete.getBanniereClient(), entete2.getBanniereClient());
		assertEquals(entete.getClientAdresse(), entete2.getClientAdresse());
		assertEquals(entete.getClientAPP(), entete2.getClientAPP());
		assertEquals(entete.getClientCP(), entete2.getClientCP());
		assertEquals(entete.getClientEmail(), entete2.getClientEmail());
		assertEquals(entete.getClientFactureA(), entete2.getClientFactureA());
		assertEquals(entete.getClientFax(), entete2.getClientFax());
		assertEquals(entete.getClientPays(), entete2.getClientPays());
		assertEquals(entete.getClientProvince(), entete2.getClientProvince());
		assertEquals(entete.getClientSoum(), entete2.getClientSoum());
		assertEquals(entete.getClientTelephone(), entete2.getClientTelephone());
		assertEquals(entete.getClientTelephoneContact(), entete2.getClientTelephoneContact());
		assertEquals(entete.getClientVille(), entete2.getClientVille());
		assertEquals(entete.getContact(), entete2.getContact());
		assertEquals(entete.getCritere(), entete2.getCritere());
		assertEquals(sdf.format(entete.getDateEntre()), sdf.format(entete2.getDateEntre()));
		assertEquals(entete.getDateLivraison(), entete2.getDateLivraison());
		assertEquals(entete.getDescription(), entete2.getDescription());
		assertEquals(entete.getGeniusNoClient(), entete2.getGeniusNoClient());
		assertEquals(entete.getNoSoumission(), entete2.getNoSoumission());
		assertEquals(entete.getNoVendeur(), entete2.getNoVendeur());
		assertEquals(entete.getProjet(), entete2.getProjet());

	}
	
	@Test
	public void getPageTest(){
		
	}
	
	@Test
	public void countAllTest(){
		
	}
	
	
}
