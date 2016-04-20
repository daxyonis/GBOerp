/**
 * 
 */
package services.dao.estimation;

import models.SoumFlex;
import models.SoumFlexItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Eva
 *
 */
@Repository
public class SoumInfographieDAOImpl extends AbstractEstimation implements SoumInfographieDAO {

	@Autowired
	private JdbcTemplate jdbcTemplateObject;
	
	/* (non-Javadoc)
	 * @see services.simple.estimation.AbstractEstimation#createCopyFromItem(int[])
	 */
	@Override
	protected void createCopyFromItem(int[] oldNoItemArray) {
		String SQL = "INSERT INTO SoumInfographie ( Copie, NoItem, Activite, TauxChoix, NbrHeure, " + 
					 "Marge, Description, Prepresse, VisuelGraphique, DessinTechnique, FichierRecevoir ) " +
					 "SELECT SoumInfographie.NoItem, SoumInfographie.NoItem, SoumInfographie.Activite, " + 
					 "SoumInfographie.TauxChoix, SoumInfographie.NbrHeure, SoumInfographie.Marge, " + 
					 "SoumInfographie.Description, SoumInfographie.Prepresse, SoumInfographie.VisuelGraphique, " +
					 "SoumInfographie.DessinTechnique, SoumInfographie.FichierRecevoir " +
					 "FROM SoumInfographie  "+
					 "WHERE NoItem = ?";
		
		for(int i=0; i<oldNoItemArray.length; i++)
			this.jdbcTemplateObject.update(SQL, oldNoItemArray[i]);
	}

	/* (non-Javadoc)
	 * @see services.simple.estimation.AbstractEstimation#updateNoItem(int[], int[])
	 */
	@Override
	protected void updateNoItem(int[] oldNoItemArray, int[] newNoItemArray) {
		if(oldNoItemArray.length == newNoItemArray.length){
			
			String SQL = "UPDATE SoumInfographie "+
				 	  	 "SET SoumInfographie.NoItem = ?, SoumInfographie.Copie = NULL "+
				 	  	 "WHERE SoumInfographie.Copie = ?";			
			// Here we do a for loop
			for(int i=0; i<newNoItemArray.length; i++){				 				 
				 this.jdbcTemplateObject.update(SQL, newNoItemArray[i], oldNoItemArray[i]);
			}		
		}
		else{
			// log error TODO
		}

	}

	/* (non-Javadoc)
	 * @see services.simple.estimation.SoumInfographieDAO#createFromSoumFlex(int, models.SoumFlex)
	 */
	public boolean createFromSoumFlex(int noItem, SoumFlex soumflex, SoumFlexItem item) {

		String SQL = "INSERT INTO SoumInfographie " +
					 "(NoItem, Activite, TauxChoix, NbrHeure, Marge) " +
					 "VALUES (?, ?, ?, ?, ?)";
		
		int numrows = this.jdbcTemplateObject.update(SQL, noItem, "Infographie", 
				item.getInfoTaux(), item.getInfoNbrHeure(),
				soumflex.getMargeGlobale());
		
		return (numrows == 1);
	}

}
