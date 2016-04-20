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
public class EmballageDAOImpl extends AbstractEstimation implements EmballageDAO {

	@Autowired
	private JdbcTemplate jdbcTemplateObject;
	
	/* (non-Javadoc)
	 * @see services.simple.estimation.AbstractEstimation#createCopyFromItem(int[])
	 */
	@Override
	protected void createCopyFromItem(int[] oldNoItemArray) {

		String SQL =  "INSERT INTO SoumEmballage ( NoItem, Copie, Emballage, Cout, Unites, Dimension, " + 
					  "Quantite, Marge, NoFournisseur, CodeProduit ) " +
					  "SELECT SoumEmballage.NoItem, SoumEmballage.NoItem, SoumEmballage.Emballage, " + 
					  "SoumEmballage.Cout, SoumEmballage.Unites, SoumEmballage.Dimension, SoumEmballage.Quantite, " + 
					  "SoumEmballage.Marge, SoumEmballage.NoFournisseur, SoumEmballage.CodeProduit " + 
					  "FROM SoumEmballage " + 
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
			
			String SQL = "UPDATE SoumEmballage "+
				 	  	 "SET SoumEmballage.NoItem = ?, SoumEmballage.Copie = NULL "+
				 	  	 "WHERE SoumEmballage.Copie = ?";			
			// Here we do a for loop
			for(int i=0; i<newNoItemArray.length; i++){				 				 
				 this.jdbcTemplateObject.update(SQL, newNoItemArray[i], oldNoItemArray[i]);
			}		
		}
		else{
			// log error TODO
		}

	}

	/* (non-Javadoc)
	 * @see services.simple.estimation.EmballageDAO#addNew(int, java.lang.String, java.lang.String, double, java.lang.String, double, double, double)
	 */
	public boolean addNew(int noItem, String codeProduit, String emballage,
			double cout, String unites, double marge, double dimension,
			double quantite) {

		String SQL;
		int numrows = 0;
		
		if(codeProduit.isEmpty()){
			SQL = "INSERT INTO SoumEmballage " +
					 "(NoItem, Emballage, Cout, Unites, Dimension, Quantite, Marge) " +
					 "VALUES (?,?,?,?,?,?,?)";
		
			numrows = this.jdbcTemplateObject.update(SQL, noItem,emballage, cout, unites, dimension, quantite, marge);
		}
		else{
			SQL = "INSERT INTO SoumEmballage " +
					 "(NoItem, CodeProduit, Emballage, Cout, Unites, Dimension, Quantite, Marge) " +
					 "VALUES (?,?,?,?,?,?,?)";
		
			numrows = this.jdbcTemplateObject.update(SQL, noItem, codeProduit, emballage, cout, unites, dimension, quantite, marge);
		}
		
		return(numrows == 1);
	}

}
