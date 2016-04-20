import models.Item;

import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Eva
 *
 */
public class ItemTest {
	 
	private static Item item;
	
	@BeforeClass
	public static void onceExecuteBeforeAll(){
		item = new Item();
	}	
		
	@Test
	public void settersAndGettersTest(){
				
		item.setNoItem(999);
		item.setDescription("foo");
		item.setFraisInstallation(123.45);
		item.setFraisVariables(345.67);
		item.setHauteur("3.1");
		item.setFamille("TEMPO");
		item.setLargeur("2.54");
		item.setNomItem("Item bidon");
		item.setNoSoumission(34987);
		item.setPrixFab(2500.01);
		item.setQuantite(214);
		item.setSourceProd("BO");
				
		assertEquals(item.getDescription(), "foo");
		assertEquals(item.getFamille(), "TEMPO");
		assertTrue(Math.abs(item.getFraisInstallation() - 123.45) < Double.MIN_VALUE);
		assertTrue(Math.abs(item.getFraisVariables() - 345.67) < Double.MIN_VALUE);
		assertEquals(item.getHauteur(),"3.1");
		assertEquals(item.getLargeur(),"2.54");
		assertEquals(item.getNoItem(),999);
		assertEquals(item.getNomItem(),"Item bidon");
		assertEquals(item.getNoSoumission(), 34987);
		assertTrue(Math.abs(item.getPrixFab() - 2500.01) < Double.MIN_VALUE);
		assertEquals(item.getQuantite(), 214);
		assertEquals(item.getSourceProd(),"BO");
	}
}
