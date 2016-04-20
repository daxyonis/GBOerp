/**
 * 
 */
package services.dao.estimation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * CncDAOImpl : implements the CncDAO
 * for operations on the SoumCNC table
 * 
 * @author Eva
 *
 */
@Repository
public class CncDAOImpl extends AbstractEstimation {
	
	@Autowired
	private JdbcTemplate jdbcTemplateObject;

	/* (non-Javadoc)
	 * @see services.simple.estimation.AbstractEstimation#createCopyFromItem(int[])
	 */
	@Override
	protected void createCopyFromItem(int[] oldNoItemArray) {
		String SQL = "INSERT INTO SoumCNC ( Copie, NoItem, Activite, TauxChoix, NbrHeure, " +
					 "Marge, Description, Materiel, Dimension, Couleur, Epaisseur, " + 
					 "SimpleFace, DoubleFace, PreparationPeinture, PreparationImpression )" +
					 "SELECT SoumCNC.NoItem, SoumCNC.NoItem, SoumCNC.Activite, SoumCNC.TauxChoix, " +
					 "SoumCNC.NbrHeure, SoumCNC.Marge, SoumCNC.Description, SoumCNC.Materiel, " + 
					 "SoumCNC.Dimension, SoumCNC.Couleur, SoumCNC.Epaisseur, " + 
					 "SoumCNC.SimpleFace, SoumCNC.DoubleFace, SoumCNC.PreparationPeinture, SoumCNC.PreparationImpression " +
					 "FROM SoumCNC " + 
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
			
			String SQL = "UPDATE SoumCNC "+
				 	  	 "SET SoumCNC.NoItem = ?, SoumCNC.Copie = NULL "+
				 	  	 "WHERE SoumCNC.Copie = ?";			
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
