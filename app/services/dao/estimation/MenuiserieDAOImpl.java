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
public class MenuiserieDAOImpl extends AbstractEstimation {

	@Autowired
	private JdbcTemplate jdbcTemplateObject;

	/* (non-Javadoc)
	 * @see services.simple.estimation.AbstractEstimation#createCopyFromItem(int[])
	 */
	@Override
	protected void createCopyFromItem(int[] oldNoItemArray) {

		String SQL = "INSERT INTO SoumMenuiserie ( Copie, NoItem, Activite, TauxChoix, NbrHeure, Marge, " + 
					 "Description, Materiel, Dimension, Couleur, Epaisseur, DessinTechnique, PreparationPeinture, PreparationImpression ) " + 
					 "SELECT SoumMenuiserie.NoItem, SoumMenuiserie.NoItem, SoumMenuiserie.Activite, SoumMenuiserie.TauxChoix, " + 
					 "SoumMenuiserie.NbrHeure, SoumMenuiserie.Marge, SoumMenuiserie.Description, SoumMenuiserie.Materiel, SoumMenuiserie.Dimension, " + 
					 "SoumMenuiserie.Couleur, SoumMenuiserie.Epaisseur, SoumMenuiserie.DessinTechnique, SoumMenuiserie.PreparationPeinture, " + 
					 "SoumMenuiserie.PreparationImpression " +
					 "FROM SoumMenuiserie " +
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
			
			String SQL = "UPDATE SoumMenuiserie "+
				 	  	 "SET SoumMenuiserie.NoItem = ?, SoumMenuiserie.Copie = NULL "+
				 	  	 "WHERE SoumMenuiserie.Copie = ?";			
			
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
