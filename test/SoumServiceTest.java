import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import models.Soumission;
import models.Item;
import models.Client;
import models.Rep;
import services.ItemService;
import services.ClientService;
import services.RepService;
import services.SoumServiceImpl;
import services.dao.SoumissionDAO;
import services.dao.SoumissionDAOImpl;

import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Eva
 * Classe de test unitaire de SoumServiceImpl
 */
public class SoumServiceTest {
			
	private static SoumissionDAOImpl soumDAO_mock;
	private static ItemService itemService_mock;
	private static ClientService clientService_mock;
	private static RepService repService_mock;
	private static SoumServiceImpl soumService;
	private static Soumission soum100, soum101, soum102;	
	
	@BeforeClass
	public static void setup() throws Exception{
		soumDAO_mock = mock(SoumissionDAOImpl.class);
		itemService_mock = mock(ItemService.class);
		clientService_mock = mock(ClientService.class);
		repService_mock = mock(RepService.class);
		
		soumService = new SoumServiceImpl();
		soumService.setSoumDAO(soumDAO_mock);
		soumService.setClientService(clientService_mock);
		soumService.setItemService(itemService_mock);
		soumService.setRepService(repService_mock);
		
		when(itemService_mock.findForSoum(100)).thenReturn(new ArrayList<Item>());
		when(itemService_mock.findForSoum(101)).thenReturn(new ArrayList<Item>());
		when(itemService_mock.findForSoum(102)).thenReturn(new ArrayList<Item>());
		when(clientService_mock.findByNo("")).thenReturn(new Client());
		when(repService_mock.findByCode("")).thenReturn(new Rep());
		
		soum100 = new Soumission();
		soum100.setNoSoumission(100);
		soum100.setClientSoum("Hydro");
		soum100.setProjet("Mon projet bidon");
		soum100.setSuite(23000);
		soum100.setVersion("B");
		soum101 = new Soumission();
		soum101.setNoSoumission(101);
		soum101.setClientSoum("Hydro");
		soum101.setProjet("Le projet de fou");
		soum101.setSuite(23000);
		soum101.setVersion("C");
		soum102 = new Soumission();
		soum102.setNoSoumission(102);
		soum102.setClientSoum("GBO");
		soum102.setProjet("Grande affiche");
		soum102.setSuite(24000);
		soum102.setVersion("A");
		
		when(soumDAO_mock.findByNo(100)).thenReturn(soum100);
		when(soumDAO_mock.findByNo(101)).thenReturn(soum101);
		when(soumDAO_mock.findByNo(102)).thenReturn(soum102);
		
		when(soumDAO_mock.findTop(2, SoumissionDAO.KeyFindSoumEnum.NO_SOUM)).thenReturn(Arrays.asList(soum102, soum101));
		when(soumDAO_mock.findTop(1, SoumissionDAO.KeyFindSoumEnum.PROJET)).thenReturn(Arrays.asList(soum102));
		when(soumDAO_mock.findTop(2, SoumissionDAO.KeyFindSoumEnum.CLIENT)).thenReturn(Arrays.asList(soum102, soum100));
		when(soumDAO_mock.findTop(2, SoumissionDAO.KeyFindSoumEnum.SUITE)).thenReturn(Arrays.asList(soum102, soum100));
		when(soumDAO_mock.findTop(2, SoumissionDAO.KeyFindSoumEnum.ALL)).thenReturn(Arrays.asList(soum102, soum100));
		
		when(soumDAO_mock.findByKeyword(SoumissionDAO.KeyFindSoumEnum.SUITE, "23", "","","","")).thenReturn(Arrays.asList(soum100, soum101));
		when(soumDAO_mock.findByKeyword(SoumissionDAO.KeyFindSoumEnum.SUITE, "2400", "","","","")).thenReturn(Arrays.asList(soum102));
		when(soumDAO_mock.findByKeyword(SoumissionDAO.KeyFindSoumEnum.PROJET, "projet", "","","","")).thenReturn(Arrays.asList(soum100, soum101));
		when(soumDAO_mock.findByKeyword(SoumissionDAO.KeyFindSoumEnum.CLIENT, "BO", "","","","")).thenReturn(Arrays.asList(soum102));
		when(soumDAO_mock.findByKeyword(SoumissionDAO.KeyFindSoumEnum.NO_SOUM, "10", "","","","")).thenReturn(Arrays.asList(soum100, soum101, soum102));
		when(soumDAO_mock.findByKeyword(SoumissionDAO.KeyFindSoumEnum.ALL, "aff", "","","","")).thenReturn(Arrays.asList(soum102));
		
		
	}
	
	@Test
	public void findByNoTest(){
		Soumission foundSoum = soumService.findByNo(100);
		assertEquals(100, foundSoum.getNoSoumission());
		assertEquals(soum100.getClientSoum(), foundSoum.getClientSoum());
		assertEquals(soum100.getProjet(), foundSoum.getProjet());
		assertEquals(soum100.getSuite(), foundSoum.getSuite());
		assertEquals(soum100.getVersion(), foundSoum.getVersion());
		
		foundSoum = soumService.findByNo(101);
		assertEquals(101, foundSoum.getNoSoumission());
		assertEquals(soum101.getClientSoum(), foundSoum.getClientSoum());
		assertEquals(soum101.getProjet(), foundSoum.getProjet());
		assertEquals(soum101.getSuite(), foundSoum.getSuite());
		assertEquals(soum101.getVersion(), foundSoum.getVersion());
		
		foundSoum = soumService.findByNo(102);
		assertEquals(102, foundSoum.getNoSoumission());
		assertEquals(soum102.getClientSoum(), foundSoum.getClientSoum());
		assertEquals(soum102.getProjet(), foundSoum.getProjet());
		assertEquals(soum102.getSuite(), foundSoum.getSuite());
		assertEquals(soum102.getVersion(), foundSoum.getVersion());
	}
	
	@Test
	public void findTopTest(){
		List<Soumission> soums = soumService.findTop(2, SoumissionDAO.KeyFindSoumEnum.NO_SOUM);
		assertEquals(2, soums.size());
		assertEquals(soum102.getNoSoumission(), soums.get(0).getNoSoumission());
		assertEquals(soum101.getNoSoumission(), soums.get(1).getNoSoumission());
		
		soums = soumService.findTop(1, SoumissionDAO.KeyFindSoumEnum.PROJET);
		assertEquals(1, soums.size());
		assertEquals(soum102.getNoSoumission(), soums.get(0).getNoSoumission());
		
		soums = soumService.findTop(2, SoumissionDAO.KeyFindSoumEnum.CLIENT);
		assertEquals(2, soums.size());
		assertEquals(soum102.getNoSoumission(), soums.get(0).getNoSoumission());
		assertEquals(soum100.getNoSoumission(), soums.get(1).getNoSoumission());
		
		soums = soumService.findTop(2, SoumissionDAO.KeyFindSoumEnum.SUITE);
		assertEquals(2, soums.size());
		assertEquals(soum102.getNoSoumission(), soums.get(0).getNoSoumission());
		assertEquals(soum100.getNoSoumission(), soums.get(1).getNoSoumission());
		
		soums = soumService.findTop(2, SoumissionDAO.KeyFindSoumEnum.ALL);
		assertEquals(2, soums.size());
		assertEquals(soum102.getNoSoumission(), soums.get(0).getNoSoumission());
		assertEquals(soum100.getNoSoumission(), soums.get(1).getNoSoumission());
		
	}
	
	@Test
	public void findByKeywordTest(){
		List<Soumission> soums = soumService.findByKeyword(SoumissionDAO.KeyFindSoumEnum.SUITE, "23","","","","");
		assertEquals(2, soums.size());
		assertEquals(soum100.getNoSoumission(), soums.get(0).getNoSoumission());
		assertEquals(soum101.getNoSoumission(), soums.get(1).getNoSoumission());
		
		soums = soumService.findByKeyword(SoumissionDAO.KeyFindSoumEnum.SUITE, "2400","","","","");
		assertEquals(1, soums.size());
		assertEquals(soum102.getNoSoumission(), soums.get(0).getNoSoumission());
		
		soums = soumService.findByKeyword(SoumissionDAO.KeyFindSoumEnum.PROJET, "projet","","","","");
		assertEquals(2, soums.size());
		assertEquals(soum100.getNoSoumission(), soums.get(0).getNoSoumission());
		assertEquals(soum101.getNoSoumission(), soums.get(1).getNoSoumission());
		
		soums = soumService.findByKeyword(SoumissionDAO.KeyFindSoumEnum.CLIENT, "BO","","","","");
		assertEquals(1, soums.size());
		assertEquals(soum102.getNoSoumission(), soums.get(0).getNoSoumission());
		
		soums = soumService.findByKeyword(SoumissionDAO.KeyFindSoumEnum.NO_SOUM, "10","","","","");
		assertEquals(3, soums.size());
		assertEquals(soum100.getNoSoumission(), soums.get(0).getNoSoumission());
		assertEquals(soum101.getNoSoumission(), soums.get(1).getNoSoumission());
		assertEquals(soum102.getNoSoumission(), soums.get(2).getNoSoumission());
		
		soums = soumService.findByKeyword(SoumissionDAO.KeyFindSoumEnum.ALL, "aff","","","","");
		assertEquals(1, soums.size());
		assertEquals(soum102.getNoSoumission(), soums.get(0).getNoSoumission());
	}
				
	
	

}
