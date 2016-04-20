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
public class DemasquageDAOImpl extends AbstractEstimation {
	
	@Autowired
	private JdbcTemplate jdbcTemplateObject;

	/* (non-Javadoc)
	 * @see services.simple.estimation.AbstractEstimation#createCopyFromItem(int[])
	 */
	@Override
	protected void createCopyFromItem(int[] oldNoItemArray) {
		
		String SQL = "INSERT INTO SoumDemasquage ( Copie, NoItem, Activite, TauxChoix, NbrHeure, " + 
					 "Marge, Description, Crevé, Miroir ) " +
					 "SELECT SoumDemasquage.NoItem, SoumDemasquage.NoItem, SoumDemasquage.Activite, " + 
					 "SoumDemasquage.TauxChoix, SoumDemasquage.NbrHeure, SoumDemasquage.Marge, " + 
					 "SoumDemasquage.Description, SoumDemasquage.Crevé, SoumDemasquage.Miroir " + 
					 "FROM SoumDemasquage " +
					 "WHERE NoItem = ?";
		
		for(int i=0; i<oldNoItemArray.length; i++){
			this.jdbcTemplateObject.update(SQL, oldNoItemArray[i]);
		}

	}

	/* (non-Javadoc)
	 * @see services.simple.estimation.AbstractEstimation#updateNoItem(int[], int[])
	 */
	@Override
	protected void updateNoItem(int[] oldNoItemArray, int[] newNoItemArray) {
		
		if(oldNoItemArray.length == newNoItemArray.length){
			
			String SQL = "UPDATE SoumDemasquage "+
				 	  	 "SET SoumDemasquage.NoItem = ?, SoumDemasquage.Copie = NULL "+
				 	  	 "WHERE SoumDemasquage.Copie = ?";			
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
