import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import services.FlexibleService;
import services.ImprimanteService;
import models.Flexible;
import models.Imprimante;

import org.junit.*;

import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * Tests unitaires de la classe FlexibleService
 * @author Eva
 *
 */
public class FlexibleServiceTest extends AbstractIntegrationTest {
	
	private static FlexibleService flexService;
	private static ImprimanteService imprimService;
	private static Flexible flex;
	private static List<Imprimante> imprimantes;
	
	
	@Autowired
	public void setFlexibleService(FlexibleService fs){
		this.flexService = fs;
	}
	
	@Autowired
	public void setImprimanteService(ImprimanteService is){
		this.imprimService = is;
	}		
	
	@Before
	public void setup(){
		flex = new Flexible();
		flex.setProduit("Produit Bidon");
		imprimantes = new ArrayList<Imprimante>();
		imprimantes.add(imprimService.findByCode("Latex"));
		imprimantes.add(imprimService.findByCode("Roland720"));
		imprimantes.add(imprimService.findByCode("HPEx4coul"));
		imprimantes.add(imprimService.findByCode("CoreJet"));
		flex.setImprimantes(imprimantes);		
	}
	
	@Test
	public void addTest(){
		boolean success = flexService.add(flex);
		assertEquals(true, success);
		assertEquals(true, (flex.getId() > 0));
	}
	
	@Test
	public void getByIdTest(){
		boolean success = flexService.add(flex);
		assertEquals(true, success);
		
		int id = flex.getId();
		Flexible dbFlex = flexService.getById(id);
		assertEquals(flex.getProduit(), dbFlex.getProduit());
		assertEquals(id, (int)(dbFlex.getId()));
		
		List<Imprimante> listFlex = flex.getImprimantes();
		List<Imprimante> listDbFlex = dbFlex.getImprimantes();
		assertEquals(listFlex.size(), listDbFlex.size());
		
		int i = 0;
		for(Imprimante imp : listFlex){
			assertEquals(imp, listDbFlex.get(i++));
		}
	}
	
	
	@Test
	public void updateTest(){
		// First add the product to make it exist
		boolean success = flexService.add(flex);
		assertEquals(true, success);
		
		flex.setDescription("Nous avons changé la description");
		flex.setEnStock(true);
		flex.setEpaisseur(7.3);
		flex.setImprimantes(new ArrayList<Imprimante>());		
		flexService.update(flex);
		
		Flexible dbFlex = flexService.getById(flex.getId());
		assertEquals("Nous avons changé la description", dbFlex.getDescription());
		assertEquals(true, dbFlex.isEnStock());
		assertTrue(Math.abs(7.3 - dbFlex.getEpaisseur()) <= Double.MIN_VALUE);
		
		List<Imprimante> listDbFlex = dbFlex.getImprimantes();
		assertEquals(0, listDbFlex.size());
	}
	
	@Ignore
	public void findAllTest(){
		
	}
	
	@Ignore
	public void removeWithIdTest(){
		
	}
	
	@Ignore
	public void removeWithFlexTest(){
		
	}
	
	
	

}
