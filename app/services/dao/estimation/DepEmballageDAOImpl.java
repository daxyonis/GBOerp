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
public class DepEmballageDAOImpl extends AbstractEstimation {

	@Autowired
	private JdbcTemplate jdbcTemplateObject;
	
	/* (non-Javadoc)
	 * @see services.simple.estimation.AbstractEstimation#createCopyFromItem(int[])
	 */
	@Override
	protected void createCopyFromItem(int[] oldNoItemArray) {
		
		String SQL = "INSERT INTO SoumDepEmballage ( Copie, NoItem, Activite, TauxChoix, NbrHeure, Marge, " + 
					 "Description, Type, Dimension, Materiel, Grosseur, Epaisseur ) " +
					 "SELECT SoumDepEmballage.NoItem, SoumDepEmballage.NoItem, SoumDepEmballage.Activite, " + 
					 "SoumDepEmballage.TauxChoix, SoumDepEmballage.NbrHeure, SoumDepEmballage.Marge, " + 
					 "SoumDepEmballage.Description, SoumDepEmballage.Type, SoumDepEmballage.Dimension, " + 
					 "SoumDepEmballage.Materiel, SoumDepEmballage.Grosseur, SoumDepEmballage.Epaisseur " +
					 "FROM SoumDepEmballage " + 
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
			
			String SQL = "UPDATE SoumDepEmballage "+
				 	  	 "SET SoumDepEmballage.NoItem = ?, SoumDepEmballage.Copie = NULL "+
				 	  	 "WHERE SoumDepEmballage.Copie = ?";			
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
