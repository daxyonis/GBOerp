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
public class SousTraitantDAOImpl extends AbstractEstimation {

	@Autowired
	private JdbcTemplate jdbcTemplateObject;
	
	/* (non-Javadoc)
	 * @see services.simple.estimation.AbstractEstimation#createCopyFromItem(int[])
	 */
	@Override
	protected void createCopyFromItem(int[] oldNoItemArray) {
		
		String SQL = "INSERT INTO SoumSousTraitant ( NoItem, Copie, SousTraitant, Cout, Quantite, " +
					 "Marge, Description, Dimension, DessinTechnique ) " + 
					 "SELECT SoumSousTraitant.NoItem, SoumSousTraitant.NoItem, SoumSousTraitant.SousTraitant, " + 
					 "SoumSousTraitant.Cout, SoumSousTraitant.Quantite, SoumSousTraitant.Marge, " + 
					 "SoumSousTraitant.Description, SoumSousTraitant.Dimension, SoumSousTraitant.DessinTechnique " +
					 "FROM SoumSousTraitant " +
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
			
			String SQL = "UPDATE SoumSousTraitant "+
				 	  	 "SET SoumSousTraitant.NoItem = ?, SoumSousTraitant.Copie = NULL "+
				 	  	 "WHERE SoumSousTraitant.Copie = ?";			
			
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
