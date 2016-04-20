import java.util.Arrays;
import java.util.List;

import models.MesItems;
import models.Item;
import services.dao.MesItemsDAOImpl;
import services.MesItemsServiceImpl;

import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Eva
 *
 */
public class MesItemsServiceTest {
		
	private static MesItemsDAOImpl mesItemsDAO_mock;
	private static MesItems mitems1, mitems2, mitems3;
	private static Item item1, item2;
	private static MesItemsServiceImpl mesItemsService;
	
	@BeforeClass
	public static void setup() throws Exception{
		
		// Init le(s) mock(s)
		mesItemsDAO_mock = mock(MesItemsDAOImpl.class);
		
		// Cree le service et assigne le mock DAO
		mesItemsService = new MesItemsServiceImpl();
		mesItemsService.setMesItemsDAO(mesItemsDAO_mock);
		
		// Cree les objets
		mitems1 = new MesItems();
		mitems2 = new MesItems();
		mitems3 = new MesItems();
		mitems1.setNoItem(1);
		mitems1.setUsager("eva");
		mitems2.setNoItem(2);
		mitems2.setUsager("eva");
		mitems3.setNoItem(3);
		mitems3.setUsager("joe");
		item1 = new Item();
		item2 = new Item();
		item1.setNoItem(1);
		item1.setNomItem("Item 1");
		item2.setNoItem(2);
		item2.setNomItem("Item 2");
		
		
		// Stubbing methods of mocked DAO with  mocked data
		when(mesItemsDAO_mock.add(mitems1)).thenReturn(1);
		when(mesItemsDAO_mock.add(mitems2)).thenReturn(1);
		when(mesItemsDAO_mock.add(mitems3)).thenReturn(1);
		when(mesItemsDAO_mock.create("joe", 4)).thenReturn(1);
		when(mesItemsDAO_mock.delete(mitems3)).thenReturn(1);
		when(mesItemsDAO_mock.delete("joe", 4)).thenReturn(1);
		when(mesItemsDAO_mock.deleteAll("eva")).thenReturn(2);
		when(mesItemsDAO_mock.getAll("eva")).thenReturn(Arrays.asList(mitems1, mitems2));
		when(mesItemsDAO_mock.getAllItemsByUser("eva")).thenReturn(Arrays.asList(item1, item2));		
	}
	
	@Test
	public void addTest(){
		int numAdded = mesItemsService.add(mitems1);
		assertEquals(1, numAdded);
		numAdded = mesItemsService.add(mitems2);
		assertEquals(1, numAdded);
		numAdded = mesItemsService.add(mitems3);
		assertEquals(1, numAdded);
	}
	
	@Test
	public void createTest(){
		int numCreated = mesItemsService.create("joe", 4);
		assertEquals(1, numCreated);
	}
	
	@Test
	public void deleteTest(){
		int numDeleted = mesItemsService.delete(mitems3);
		assertEquals(1, numDeleted);
		numDeleted = mesItemsService.delete("joe", 4);
		assertEquals(1, numDeleted);		
	}
	
	@Test
	public void deleteAllTest(){
		int numDeleted = mesItemsService.deleteAll("eva");
		assertEquals(2, numDeleted);
	}
	
	@Test
	public void getAllTest(){
		List<MesItems> mitems = mesItemsService.getAll("eva");
		assertEquals(2, mitems.size());
		assertEquals("eva", mitems.get(0).getUsager());
		assertEquals("eva", mitems.get(1).getUsager());
		assertEquals(1, mitems.get(0).getNoItem());
		assertEquals(2, mitems.get(1).getNoItem());
	}
	
	@Test
	public void getAllItemsTest(){
		List<Item> items = mesItemsService.getAllItemsByUser("eva");
		assertEquals(2, items.size());
		assertEquals(1, items.get(0).getNoItem());
		assertEquals(2, items.get(1).getNoItem());
		assertEquals("Item 1", items.get(0).getNomItem());
		assertEquals("Item 2", items.get(1).getNomItem());
	}

}
