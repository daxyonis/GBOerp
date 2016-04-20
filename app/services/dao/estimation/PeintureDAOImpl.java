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
public class PeintureDAOImpl extends AbstractEstimation {

	@Autowired
	private JdbcTemplate jdbcTemplateObject;
	
	/* (non-Javadoc)
	 * @see services.simple.estimation.AbstractEstimation#createCopyFromItem(int[])
	 */
	@Override
	protected void createCopyFromItem(int[] oldNoItemArray) {

		String SQL = "INSERT INTO SoumPeinture ( Copie, NoItem, Activite, TauxChoix, NbrHeure, Marge, " + 
					 "Materiel, Description, Dimension, Couleur, SimpleFace, DoubleFace, Procede, TypePeinture ) " + 
					 "SELECT SoumPeinture.NoItem, SoumPeinture.NoItem, SoumPeinture.Activite, SoumPeinture.TauxChoix, " + 
					 "SoumPeinture.NbrHeure, SoumPeinture.Marge, SoumPeinture.Materiel, SoumPeinture.Description, " + 
					 "SoumPeinture.Dimension, SoumPeinture.Couleur, SoumPeinture.SimpleFace, SoumPeinture.DoubleFace, " + 
					 "SoumPeinture.Procede, SoumPeinture.TypePeinture " + 
					 "FROM SoumPeinture "  +
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
			
			String SQL = "UPDATE SoumPeinture "+
				 	  	 "SET SoumPeinture.NoItem = ?, SoumPeinture.Copie = NULL "+
				 	  	 "WHERE SoumPeinture.Copie = ?";			
			
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
