import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import services.SoumService;
import services.dao.SoumissionDAO;
import models.Soumission;

import org.junit.*;

import static org.junit.Assert.*;
/**
 * Tests unitaires de la classe SoumService
 * @author Eva
 *
 */
public class SoumServiceIntegrationTest extends AbstractIntegrationTest {
	
	private static SoumService soumService;
	
	@Autowired
	public void setSoumService(SoumService ss){
		this.soumService = ss;
	}
	
	/*********************************************************
	 * TESTS the following query:
	 * ------ SQL SERVER QUERY ------------------
	 * SELECT NoSoumission, Projet, DateEntre, NoVendeur, GeniusNoClient
	 * from Soumission
	 * WHERE Projet LIKE '%banniere%' COLLATE Latin1_General_CI_AI
	 * AND NoVendeur = 14 
	 * AND DateEntre BETWEEN '2014-01-01' AND '2014-12-31'
	 * AND GeniusNoClient = '01760'
	 * ------ SQL SERVER QUERY ------------------
	 * 
	 * ==> should return 6 results, NoSoumission=
	 * 	38417, 39046, 39420, 35131, 35122, 35132
	 **********************************************************/
	@Test
	public void findByKeywordTest(){
		int noSoumArray [] = {38417, 39046, 39420, 35131, 35122, 35132};
		List<Soumission> soums = soumService.findByKeyword(SoumissionDAO.KeyFindSoumEnum.PROJET, 
				"banniere", "2014-01-01", "2014-12-31", "01760", "14");
		
		assertEquals(soums.size(), 6);
		
		int i=0;
		for(Soumission s : soums){
			assertEquals(s.getNoSoumission(), noSoumArray[i++]);
		}
	}
	
	@Test
	public void findTopTest(){
		List<Soumission> soums = soumService.findTop(10, SoumissionDAO.KeyFindSoumEnum.NO_SOUM);
		
		assertEquals(soums.size(), 10);
		
		// verify the results are in desc noSoum order
		int prevNoSoum = Integer.MAX_VALUE;
		for(Soumission s : soums){
			assertTrue(s.getNoSoumission() < prevNoSoum);
			prevNoSoum = s.getNoSoumission();
		}
	}
	
	@Test
	public void findByNoTest(){
		// Test the nonexistent case
		Soumission soum = soumService.findByNo(0);		
		assertTrue(null == soum);
		
		soum = soumService.findByNo(38417);		
		assertEquals(38417, soum.getNoSoumission());
	}
	
	// NB: SoumService.create() method already tested in SoumRapideFlexTest
	//	@Test
	//	public void createTest()
	
	
	/**
	* Search for the 2nd page of all soums anterior to 2015-07-31 
	* that have the 'metro' keyword and 14 as repNo. Page is 10 soums long.
	* Expected result by noSoumission:
	* 38907, 38823, 38741, 38526, 38410, 37244, 36724, 33935, 33529, 33341
	* The following query was used for expected results:
	*------ SQL SERVER QUERY ------------------
	*  DECLARE @keyword1 As NVARCHAR(25)
 	* DECLARE @keyword2 As NVARCHAR(25)
	* SET @keyword1 = 'metro%';
	* SET @keyword2 = '%metro%';
	* 
	* DECLARE @startRow As INT
	* DECLARE @endRow As INT
	* DECLARE @pageNumber As INT
	* DECLARE @pageSize As INT
	* SET @pageNumber = 2;
	* SET @pageSize = 10;
	* SET @startRow = (@pageNumber -1) * @pageSize + 1;
	* SET @endRow = @pageNumber * @pageSize; 
	*
	* WITH OrderedOrders AS (
	* SELECT NoSoumission, Suite, Version, Projet, Statut, NoDossier, 
	* BanniereClient, Critere, ClientSoum, DateEntre, NoVendeur, GeniusNoClient, 
	* ROW_NUMBER() OVER (order by Suite desc, Version desc) AS 'RowNumber' 
	* FROM Soumission
	* WHERE (Suite LIKE @keyword1 
	* OR ClientSoum LIKE @keyword2 COLLATE Latin1_General_CI_AI
	* OR BanniereClient LIKE @keyword2 COLLATE Latin1_General_CI_AI
	* OR Projet LIKE @keyword2 COLLATE Latin1_General_CI_AI
	* OR Critere LIKE @keyword2 COLLATE Latin1_General_CI_AI
	* OR NoDossier LIKE @keyword1)
	* AND NoVendeur = '14'
	* AND DateEntre < '2015-07-31'
	* )
	* SELECT * FROM OrderedOrders
	* WHERE RowNumber between @startRow and @endRow
	* ORDER BY RowNumber
	*------ SQL SERVER QUERY ------------------
	*/
	@Test
	public void getPageTest(){
		int resNoSoum [] = {38907, 38823, 38741, 38526, 38410, 37244, 36724, 33935, 33529, 33341};
		List<Soumission> soums = soumService.getPage("metro", "suite", "desc", 10, 2, "14", 0, 0, "", "2015-07-31");
		
		assertEquals(soums.size(), 10);
		
		int i=0;
		for(Soumission s : soums){
			assertEquals(resNoSoum[i++], s.getNoSoumission());
		}
	}
	
	/**
	 * SEE getPageTest()
	 * runs the same query without pagination (comment out the 'WHERE RowNumber between @startRow and @endRow'), 
	 * only counts the total number of soums that fill up all criteria
	 * Expected num rows = 1670
	 */
	@Test
	public void countAllTest(){
		int numSoums = soumService.countAll("metro", "14", 0, 0, "", "2015-07-31");
		
		assertEquals(numSoums, 1670);
	}

}
