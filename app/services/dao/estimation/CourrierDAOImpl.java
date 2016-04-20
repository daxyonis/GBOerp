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
public class CourrierDAOImpl extends AbstractEstimation {
	
	@Autowired
	private JdbcTemplate jdbcTemplateObject;

	/* (non-Javadoc)
	 * @see services.simple.estimation.AbstractEstimation#createCopyFromItem(int[])
	 */
	@Override
	protected void createCopyFromItem(int[] oldNoItemArray) {
		
		String SQL = "INSERT INTO SoumCourrier ( NoItem, Copie, Courrier, Cout, Quantite, Marge ) " + 
					 "SELECT SoumCourrier.NoItem, SoumCourrier.NoItem, SoumCourrier.Courrier, " + 
					 "SoumCourrier.Cout, SoumCourrier.Quantite, SoumCourrier.Marge " + 
					 "FROM SoumCourrier " + 
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
			
			String SQL = "UPDATE SoumCourrier "+
				 	  	 "SET SoumCourrier.NoItem = ?, SoumCourrier.Copie = NULL "+
				 	  	 "WHERE SoumCourrier.Copie = ?";			
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
