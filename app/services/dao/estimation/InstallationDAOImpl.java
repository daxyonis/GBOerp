/**
 * 
 */
package services.dao.estimation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Eva
 *
 */
@Repository
public class InstallationDAOImpl extends AbstractEstimation {

	@Autowired
	private JdbcTemplate jdbcTemplateObject;
	
	/* (non-Javadoc)
	 * @see services.simple.estimation.AbstractEstimation#createCopyFromItem(int[])
	 */
	@Override
	protected void createCopyFromItem(int[] oldNoItemArray) {
		String SQL = "INSERT INTO SoumInstallation ( Copie, NoItem, Activite, TauxChoix, NbrHeure, Marge, " + 
					 "Description, Quantite, Annexer ) " +
					 "SELECT SoumInstallation.NoItem, SoumInstallation.NoItem, SoumInstallation.Activite, " + 
					 "SoumInstallation.TauxChoix, SoumInstallation.NbrHeure, SoumInstallation.Marge, " + 
					 "SoumInstallation.Description, SoumInstallation.Quantite, SoumInstallation.Annexer " +
					 "FROM SoumInstallation " + 
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
			
			String SQL = "UPDATE SoumInstallation "+
				 	  	 "SET SoumInstallation.NoItem = ?, SoumInstallation.Copie = NULL "+
				 	  	 "WHERE SoumInstallation.Copie = ?";			
			// Here we do a for loop
			for(int i=0; i<newNoItemArray.length; i++){				 				 
				 this.jdbcTemplateObject.update(SQL, newNoItemArray[i], oldNoItemArray[i]);
			}		
		}
		else{
			// log error TODO
		}
		
	}

}
