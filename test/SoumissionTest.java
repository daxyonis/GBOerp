import models.Soumission;

import org.junit.*;
import static org.junit.Assert.*;


/**
 * @author Eva
 *
 */
public class SoumissionTest {
	
	private static Soumission soum;
	
	@BeforeClass
	public static void onceExecuteBeforeAll(){
		soum = new Soumission();
	}
	
	@Test
	public void settersAndGettersTest(){
		soum.setClientSoum("Hydro");
		soum.setNoSoumission(7001);
		soum.setProjet("Affiches de rabais");
		soum.setSuite(23023);
		soum.setVersion("D");
		
		assertEquals(soum.getClientSoum(), "Hydro");
		assertEquals(soum.getNoSoumission(), 7001);
		assertEquals(soum.getProjet(), "Affiches de rabais");
		assertEquals(soum.getSuite(), 23023);
		assertEquals(soum.getVersion(), "D");		
	}

}
