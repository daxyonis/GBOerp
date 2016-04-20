import services.ActiviteMOService;
import models.ActiviteMO;

import org.junit.*;
import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * Tests unitaires de la classe ActiviteMOService
 * @author Eva
 *
 */
public class ActiviteMOServiceTest extends AbstractIntegrationTest {
	
	private ActiviteMOService moService;
	
	@Autowired
	public void setActiviteMOService(ActiviteMOService amos){
		this.moService = amos;
	}
	
	@Test
	public void findByActiviteInfographieTest(){
				
		String categorie = "infographie";
		String categorieInterne = moService.getMOcategory(categorie);
		String activite = "Infographie";
		String codeMachine = "12";
		String codeOp = "12";
		ActiviteMO mo = moService.findByActivite(categorieInterne, activite);
		
		assertEquals(mo.getCategorie(), categorieInterne);
		assertEquals(mo.getActivite(), activite);
		assertEquals(mo.getCodeMachine(), codeMachine);
		assertEquals(mo.getCodeOp(), codeOp);
	}
	
	@Test
	public void findByActiviteAssemblageTest(){
	
		String categorie = "assemblage";
		String categorieInterne = moService.getMOcategory(categorie);
		String activite = "Manipulation/Finition";
		String codeMachine = "46";
		String codeOp = "46";
		ActiviteMO mo = moService.findByActivite(categorieInterne, activite);
		
		assertEquals(mo.getCategorie(), categorieInterne);
		assertEquals(mo.getActivite(), activite);
		assertEquals(mo.getCodeMachine(), codeMachine);
		assertEquals(mo.getCodeOp(), codeOp);
	}

}
