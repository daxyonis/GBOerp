package services.dao.estimation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * AssemblageDAOImpl : implements the AssemblageDAO
 * for operations on the SoumAssemblage table
 * @author Eva
 *
 */
@Repository
public class AssemblageDAOImpl extends AbstractEstimation implements AssemblageDAO {

	@Autowired
	private JdbcTemplate jdbcTemplateObject;		
	
	/*
	 * (non-Javadoc)
	 * @see services.simple.estimation.AbstractEstimation#createCopyFromItem(int[])
	 */
	@Override
	protected void createCopyFromItem(int[] oldNoItemArray) {	
		String SQL = "INSERT INTO SoumAssemblage ( Copie, NoItem, Activite, TauxChoix, NbrHeure, Marge, Description ) "+
				     "SELECT SoumAssemblage.NoItem, SoumAssemblage.NoItem, SoumAssemblage.Activite, SoumAssemblage.TauxChoix, " +
				     "SoumAssemblage.NbrHeure, SoumAssemblage.Marge, SoumAssemblage.Description "+
				     "FROM SoumAssemblage "+
				     "WHERE NoItem = ?";
				
		for(int i=0; i<oldNoItemArray.length; i++)
			this.jdbcTemplateObject.update(SQL, oldNoItemArray[i]);
		
	}

		
	/*
	 * (non-Javadoc)
	 * @see services.simple.estimation.AbstractEstimation#updateNoItem(int[], int[])
	 */
	@Override
	protected void updateNoItem(int[] oldNoItemArray, int[] newNoItemArray) {
		if(oldNoItemArray.length == newNoItemArray.length){
			
			String SQL = "UPDATE SoumAssemblage "+
				 	  	 "SET SoumAssemblage.NoItem = ?, SoumAssemblage.Copie = NULL "+
				 	  	 "WHERE SoumAssemblage.Copie = ?";			
			// Here we do a for loop
			for(int i=0; i<newNoItemArray.length; i++){				 				 
				 this.jdbcTemplateObject.update(SQL, newNoItemArray[i], oldNoItemArray[i]);
			}		
		}
		else{
			// log error TODO
		}
	}


	@Override
	public boolean addNew(int noItem, String activite, double tauxChoix, 
			  double nbrHeure, double marge, String description) {
		String SQL;
		int numrows;
		if(!description.isEmpty()){
			SQL = "INSERT INTO SoumAssemblage " +
					 "(NoItem, Activite, TauxChoix, NbrHeure, Marge, Description) " +
					 "VALUES (?,?,?,?,?,?)";
		
			numrows = this.jdbcTemplateObject.update(SQL, noItem, activite, tauxChoix, nbrHeure, marge, description);
		}
		else{
			SQL = "INSERT INTO SoumAssemblage " +
				  "(NoItem, Activite, TauxChoix, NbrHeure, Marge) " +
				  "VALUES (?,?,?,?,?)";
		
			numrows = this.jdbcTemplateObject.update(SQL, noItem, activite, tauxChoix, nbrHeure, marge);			
		}
		return(numrows == 1);
	}
	
	

}
