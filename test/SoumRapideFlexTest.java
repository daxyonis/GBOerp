import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.Soumission;
import models.SoumFlex;
import models.SoumFlexItem;
import models.Flexible;
import models.Item;
import services.SoumService;
import services.ItemService;
import services.FlexibleService;

import org.junit.*;

import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Functional tests for the soumission rapide flexible 
 * Extends AbstractIntegrationTest which comprises a Spring test context and transaction management 
 * @author Eva
 *
 */
public class SoumRapideFlexTest extends AbstractIntegrationTest {
		
	private static SoumService soumService;		
	private static ItemService itemService;
	private static FlexibleService flexService;
	
	private static final String SOUM_PROJET = "Projet de test avec Play" ;
	private static final String SOUM_CLIENT_HYDRO = "01375";
	private static final String SOUM_REP_NADINE = "14";
	private static final String SOUM_REP = "Nadine Duhamel";
	private static final String SOUM_CONTACT = "Simone Dupré";
	private static final String SOUM_CONTACT_TEL = "514-123-4567";
	private static final String SOUM_CONTACT_EMAIL = "simone.dupre@hydro.qc.ca";
	private static final String SOUM_DATE_REQUISE = "2015-07-12";
	private static final int 	SOUM_ITEM_QTE = 2;
	private static final double SOUM_ITEM_HAUT = 48.5;
	private static final double SOUM_ITEM_LARG = 31.5;
	private static final int 	SOUM_ITEM_PERTE = 10;
	private static final String SOUM_MAT_CAT = "Flexible"; 
	private static final String SOUM_MAT_TYPE = "BANNIÈRE";
	private static final int 	SOUM_FLEX_TOILE_FLEXFACE = 1009;
	private static final String SOUM_IMPRIMANTE = "HPEx4coul";
	private static final int 	SOUM_IMP_TEMPS_MANIP = 5;
	private static final double SOUM_INFO_TAUX = 42.0;
	private static final double SOUM_INFO_NBR_HEURE = 0.5;
	private static final int 	SOUM_INFO_NBR_VISUELS = 2;
	private static final double SOUM_MO_TAUX = 34.25;
	private static final double SOUM_MO_NBR_HEURE = 1.5;
	private static final double SOUM_MO_COUT_SAC = 6.00;
	private static final int 	SOUM_FINI_TYPE = 2001;	// Id pour Coutures & Oeillets
	private static final double SOUM_FINI_PI_TRAITES = (SOUM_ITEM_HAUT + SOUM_ITEM_LARG) * 2 * SOUM_ITEM_QTE /12.0;	
	private static final int 	SOUM_COMMISSION = 2;
	private static final int 	SOUM_MARGE = 40;
	private static final double SOUM_VENTES = 300.0;
	
	private static SimpleDateFormat sdf;
	private static SoumFlex soumflex; 
	
	@Autowired
	public void setSoumService(SoumService soumService){
		this.soumService = soumService;		
	}
	
	@Autowired
	public void setItemService(ItemService itemService){
		this.itemService = itemService;
	}
	
	@Autowired
	public void setFlexibleService(FlexibleService fs){
		this.flexService = fs;
	}
	
	/**
	 * setup()
	 * crée et remplit un objet SoumFlex : le contenu d'un formulaire de soumission rapide flexible
	 * @throws Exception
	 */
	@BeforeClass
	public static void setup() throws Exception{		
		
		soumflex = new SoumFlex();
		soumflex.items = new ArrayList<SoumFlexItem>();
		soumflex.items.add(new SoumFlexItem());
		sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		
		soumflex.setInputProjet(SOUM_PROJET);
		soumflex.setSelectRepresentant(SOUM_REP_NADINE);
		soumflex.setSelectClient(SOUM_CLIENT_HYDRO);
		soumflex.setInputContactNom(SOUM_CONTACT);
		soumflex.setInputContactTel(SOUM_CONTACT_TEL);
		soumflex.setInputContactEmail(SOUM_CONTACT_EMAIL);				 
		soumflex.setInputDate(sdf.parse(SOUM_DATE_REQUISE));
		soumflex.setInputDateEstimation(new Date());		
		soumflex.items.get(0).setQte(SOUM_ITEM_QTE);
		soumflex.items.get(0).setHaut(SOUM_ITEM_HAUT);
		soumflex.items.get(0).setLarg(SOUM_ITEM_LARG);
		soumflex.items.get(0).setPerte(SOUM_ITEM_PERTE);		
		soumflex.items.get(0).setMatCat(SOUM_MAT_CAT);
		soumflex.items.get(0).setMatType(SOUM_MAT_TYPE);
		soumflex.items.get(0).setMatProd(SOUM_FLEX_TOILE_FLEXFACE);		
		soumflex.items.get(0).setImpSelect(SOUM_IMPRIMANTE);
		soumflex.items.get(0).setImpTempsManip(SOUM_IMP_TEMPS_MANIP);		
		soumflex.items.get(0).setInfoTaux(SOUM_INFO_TAUX);
		soumflex.items.get(0).setInfoNbrHeure(SOUM_INFO_NBR_HEURE);
		soumflex.items.get(0).setInfoNbVisuels(SOUM_INFO_NBR_VISUELS);		
		soumflex.items.get(0).setMoCoutSac(SOUM_MO_COUT_SAC);
		soumflex.items.get(0).setMoTaux(SOUM_MO_TAUX);
		soumflex.items.get(0).setMoNbrHeure(SOUM_MO_NBR_HEURE);		
		soumflex.items.get(0).setFiniSelectType(SOUM_FINI_TYPE);
		soumflex.items.get(0).setFiniPiTraites(SOUM_FINI_PI_TRAITES);
		soumflex.items.get(0).setMontantVente(SOUM_VENTES);
		soumflex.setInputCommission(SOUM_COMMISSION);
		soumflex.setMargeGlobale(SOUM_MARGE);		
	}
	
	
	private void checkSoum(int noSoumission, Soumission soum){
		assertEquals(soum.getClientEmail(), SOUM_CONTACT_EMAIL);
		assertEquals(soum.getClientTelephoneContact(), SOUM_CONTACT_TEL);
		assertEquals(soum.getCommission(), SOUM_COMMISSION, Double.MIN_VALUE);
		assertEquals(soum.getContact(), SOUM_CONTACT);
		String DateEstimation = sdf.format(soumflex.getInputDateEstimation());
		assertEquals(sdf.format(soum.getDateDebutEstimation()), DateEstimation);
		assertEquals(sdf.format(soum.getDateEntre()), DateEstimation);
		assertEquals(soum.getDateLivraison(), SOUM_DATE_REQUISE);
		assertEquals(soum.getGeniusNoClient(), SOUM_CLIENT_HYDRO);
		assertEquals(soum.getNomVendeur(), SOUM_REP);
		assertEquals(soum.getNoSoumission(), noSoumission);
		assertEquals(soum.getNoVendeur(), SOUM_REP_NADINE);
		assertEquals(soum.getPrix().doubleValue(), SOUM_VENTES, Double.MIN_VALUE);
		assertEquals(soum.getProjet(), SOUM_PROJET);
		assertEquals(soum.getVersion(),"A");
	}
	/**
	 * createTest()
	 * Teste la méthode create() de soumService
	 * On crée une soumission à partir d'un objet SoumFlex
	 * puis on relit cette soumission et on vérifie ses champs
	 * ainsi que ceux de l'item sous-jacent
	 */
	@Test
	public void createTest(){
		int noSoumission = soumService.create(soumflex);
		//System.out.println("Soumission creee : " + noSoumission);
		assertTrue(noSoumission > 0);
		
		// Check the contents of this soumission
		Soumission soum = soumService.findByNo(noSoumission);
		checkSoum(noSoumission, soum);		
	
		// Check if the items were created ok
		List<Item> items = itemService.findForSoum(noSoumission);				
		assertEquals(items.size(), 1);
		
		Item item = items.get(0);
		Flexible flex = flexService.getById(SOUM_FLEX_TOILE_FLEXFACE);
		
		assertEquals(item.getNoSoumission(), noSoumission);
		assertEquals(item.getQuantite(), SOUM_ITEM_QTE);
		Double haut = new Double(SOUM_ITEM_HAUT);
		Double larg = new Double(SOUM_ITEM_LARG);
		assertEquals(item.getHauteur(), haut.toString());
		assertEquals(item.getLargeur(), larg.toString());
		assertEquals(item.getFraisInstallation(), 0.0, Double.MIN_VALUE);
		assertEquals(item.getFraisVariables(), 0.0, Double.MIN_VALUE);
		assertEquals(item.getPrixFab(), SOUM_VENTES, Double.MIN_VALUE);
		assertEquals(item.getNomItem(), flex.getProduit());
		assertEquals(item.getDescription(), flex.getDescription() + "; avec Finition");		
		assertEquals(item.getSourceProd(), "Bo-Concept");
		
		//System.out.println("Item cree : " + item.getNoItem());
		
		// To BE continued : when Estimation DAOs are 
		// completed, test for SoumMaterial, SoumEmballage etc
	}

	/**
	 * createWith2ItemsTest
	 * On reproduit le test manuel fait dans le browser (Test bbone multi-items III -- soum #44101)
	 * avec une soumflex à 2 items, lue dans le fichier soumflex.json.
	 * 
	 * Par la suite on utilise create pour créer la soumission et les items
	 * dans la BD, puis on vérifie que les objets Soumission, Items correspondent
	 * bien aux données entrées. 
	 * 
	 */
	@Test
	public void createWith2ItemsTest(){
		ObjectMapper mapper = new ObjectMapper();		
		try {
			SoumFlex soumflexFromJson = mapper.readValue(new File("C:\\Users\\Eva\\GBOerp_br\\tests\\backbone\\test\\soumflex.json"), SoumFlex.class);
			
			int noSoumission = soumService.create(soumflexFromJson);
			assertTrue(noSoumission > 0);
			
			// Check the contents of this soumission
			Soumission soum = soumService.findByNo(noSoumission);			
			assertEquals(soumflexFromJson.getInputProjet(), soum.getProjet());
			assertEquals(soumflexFromJson.getSelectRepresentant(), soum.getNoVendeur());
			assertEquals(soumflexFromJson.getSelectClient(), soum.getGeniusNoClient());
			assertEquals(soumflexFromJson.getInputContactNom(), soum.getContact());
			assertEquals(sdf.format(soumflexFromJson.getInputDate()), soum.getDateLivraison());
			assertEquals(sdf.format(soumflexFromJson.getInputDateEstimation()), sdf.format(soum.getDateDebutEstimation()));
			assertEquals(soumflexFromJson.getInputCommission(), soum.getCommission(), Double.MIN_VALUE);
			assertEquals(soumflexFromJson.getTotalVente(), soum.getPrix().doubleValue(), Double.MIN_VALUE);			
 
			// Check if the items were created ok
			List<Item> items = itemService.findForSoum(noSoumission);				
			assertEquals(items.size(), 2);
									
			for(int i=0; i<items.size(); i++){
				Item item = items.get(i);
				SoumFlexItem sfitem = soumflexFromJson.items.get(i);
				Flexible flex = flexService.getById(sfitem.getMatProd());
				
				assertEquals(noSoumission,item.getNoSoumission());
				assertEquals(sfitem.getQte(), item.getQuantite());
				Double haut = new Double(sfitem.getHaut());
				Double larg = new Double(sfitem.getLarg());
				assertEquals(haut.toString(), item.getHauteur());
				assertEquals(larg.toString(), item.getLargeur());
				assertEquals(0.0, item.getFraisInstallation(), Double.MIN_VALUE);
				assertEquals(0.0, item.getFraisVariables(), Double.MIN_VALUE);
				assertEquals(sfitem.getMontantVente(), item.getPrixFab(), Double.MIN_VALUE);				
				assertEquals(flex.getProduit(), item.getNomItem());
				String desc = flex.getDescription();
				if(sfitem.getFiniSelectType() > 0){
					desc += "; avec Finition";
				}
				if(sfitem.getLamSelectType() > 0){
					desc += "; avec Laminage";
				}
				assertEquals(desc, item.getDescription());		
				assertEquals("Bo-Concept", item.getSourceProd());
				assertEquals(sfitem.getImpTempsManip(), 5);		// constant = the nb of minutes per item for print manipulation
								
				// To BE continued : test for SoumMaterial, SoumEmballage, SoumAssemblage etc
			}		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
}
