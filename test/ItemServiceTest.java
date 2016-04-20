import java.util.Arrays;
import java.util.List;

import models.Item;
import services.ItemServiceImpl;
import services.dao.ItemDAOImpl;
import services.dao.estimation.*;

import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Eva
 * Tests unitaires de la classe ItemService
 */
public class ItemServiceTest {
	
	private static ItemServiceImpl itemService;
	private static ItemDAOImpl itemDAO_mock;	
	private static Item item1, item2;
	private static int [] itemsToCopy = {1, 2};
	private static int [] itemsCopied = {3, 4};
	
	@BeforeClass
	public static void setup() throws Exception {
		itemDAO_mock = mock(ItemDAOImpl.class);
		
		itemService = new ItemServiceImpl();
		itemService.setItemDAO(itemDAO_mock);
		
		item1 = new Item();
		item2 = new Item();
		item1.setNoItem(1);
		item2.setNoItem(2);
		item1.setNomItem("Item1");
		item2.setNomItem("Item2");				
		
		when(itemDAO_mock.copyItemInfoToSoum(itemsToCopy, 101)).thenReturn(itemsCopied);
	}
	
	@Test
	public void copyItemToSoumTest(){
		
		// Need to pass Estimation DAOs
		AssemblageDAOImpl 	assemblage = mock(AssemblageDAOImpl.class);
		CncDAOImpl 			cnc = mock(CncDAOImpl.class);
		CourrierDAOImpl 	courrier = mock(CourrierDAOImpl.class);
		DecoupeDAOImpl 		decoupe = mock(DecoupeDAOImpl.class);
		DemasquageDAOImpl 	demasq = mock(DemasquageDAOImpl.class);
		DepEmballageDAOImpl depemball = mock(DepEmballageDAOImpl.class);
		EmballageDAOImpl 	emball = mock(EmballageDAOImpl.class);
		SoumInfographieDAOImpl 	infog = mock(SoumInfographieDAOImpl.class);
		InstallationDAOImpl install = mock(InstallationDAOImpl.class);
		MaterielDAOImpl 	mat = mock(MaterielDAOImpl.class);
		MenuiserieDAOImpl 	menuis = mock(MenuiserieDAOImpl.class);
		NumeriqueDAOImpl 	num = mock(NumeriqueDAOImpl.class);
		PeintureDAOImpl 	peint = mock(PeintureDAOImpl.class);
		SousTraitantDAOImpl st = mock(SousTraitantDAOImpl.class);
		VentesDAOImpl 		ventes = mock(VentesDAOImpl.class);
		
		itemService.setAssemblage(assemblage);
		itemService.setCnc(cnc);
		itemService.setCourrier(courrier);
		itemService.setDecoupe(decoupe);
		itemService.setDemasquage(demasq);
		itemService.setDepEmballage(depemball);
		itemService.setEmballage(emball);
		itemService.setInfographie(infog);
		itemService.setInstallation(install);
		itemService.setMateriel(mat);
		itemService.setMenuiserie(menuis);
		itemService.setNumerique(num);
		itemService.setPeinture(peint);
		itemService.setSousTraitant(st);
		itemService.setVentes(ventes);
		
		List<Item> items = Arrays.asList(item1, item2);
		int[] newItemNos = itemService.copyItemsToSoum(items, 101);
		
		// Check result
		assertEquals(2, newItemNos.length);
		assertEquals(3, newItemNos[0]);
		assertEquals(4, newItemNos[1]);
		
		// Check the DAO copy where all called once
		verify(itemDAO_mock).copyItemInfoToSoum(itemsToCopy, 101);
		verify(assemblage).copyEstimation(itemsToCopy, itemsCopied);
		verify(cnc).copyEstimation(itemsToCopy, itemsCopied);
		verify(courrier).copyEstimation(itemsToCopy, itemsCopied);
		verify(decoupe).copyEstimation(itemsToCopy, itemsCopied);
		verify(demasq).copyEstimation(itemsToCopy, itemsCopied);
		verify(depemball).copyEstimation(itemsToCopy, itemsCopied);
		verify(emball).copyEstimation(itemsToCopy, itemsCopied);
		verify(infog).copyEstimation(itemsToCopy, itemsCopied);
		verify(install).copyEstimation(itemsToCopy, itemsCopied);
		verify(mat).copyEstimation(itemsToCopy, itemsCopied);
		verify(menuis).copyEstimation(itemsToCopy, itemsCopied);
		verify(num).copyEstimation(itemsToCopy, itemsCopied);
		verify(peint).copyEstimation(itemsToCopy, itemsCopied);
		verify(st).copyEstimation(itemsToCopy, itemsCopied);
		verify(ventes).copyEstimation(itemsToCopy, itemsCopied);
				
		// Check integrity of items
		assertEquals(item1, items.get(0));
		assertEquals(item2, items.get(1));
	}

}
