import models.MesItems;

import org.junit.*;
import static org.junit.Assert.*;


/**
 * Tests of model MesItems
 * @author Eva
 *
 */
public class MesItemsTest {
	
	private static MesItems mitems;
	
	@BeforeClass
	public static void onceExecuteBeforeAll(){
		mitems = new MesItems();
	}
	
	@Test
	public void settersAndGettersTest(){		
		mitems.setUsager("bobino");
		mitems.setNoItem(14400);
		
		assertEquals(mitems.getUsager(), "bobino");
		assertEquals(mitems.getNoItem(), 14400);
	}

}
