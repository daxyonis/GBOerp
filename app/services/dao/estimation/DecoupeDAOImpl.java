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
public class DecoupeDAOImpl extends AbstractEstimation {
	
	@Autowired
	private JdbcTemplate jdbcTemplateObject;

	/* (non-Javadoc)
	 * @see services.simple.estimation.AbstractEstimation#createCopyFromItem(int[])
	 */
	@Override
	protected void createCopyFromItem(int[] oldNoItemArray) {
		
		String SQL = "INSERT INTO SoumDecoupe ( Copie, NoItem, Activite, TauxChoix, NbrHeure, Marge, " +
					 "Description, Dimension, Couleur, TypeVinyle, Epaisseur, Crevé, Miroir, SimpleFace, DoubleFace ) " +
					 "SELECT SoumDecoupe.NoItem, SoumDecoupe.NoItem, SoumDecoupe.Activite, SoumDecoupe.TauxChoix, " +
					 "SoumDecoupe.NbrHeure, SoumDecoupe.Marge, SoumDecoupe.Description, SoumDecoupe.Dimension, " +
					 "SoumDecoupe.Couleur, SoumDecoupe.TypeVinyle, SoumDecoupe.Epaisseur, SoumDecoupe.Crevé, " +
					 "SoumDecoupe.Miroir, SoumDecoupe.SimpleFace, SoumDecoupe.DoubleFace " + 
					 "FROM SoumDecoupe " + 
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
			
			String SQL = "UPDATE SoumDecoupe "+
				 	  	 "SET SoumDecoupe.NoItem = ?, SoumDecoupe.Copie = NULL "+
				 	  	 "WHERE SoumDecoupe.Copie = ?";			
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
