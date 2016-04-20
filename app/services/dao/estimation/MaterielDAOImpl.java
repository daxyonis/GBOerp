/**
 * 
 */
package services.dao.estimation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Eva
 *
 */
@Repository
public class MaterielDAOImpl extends AbstractEstimation implements MaterielDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplateObject;

	/* (non-Javadoc)
	 * @see services.simple.estimation.AbstractEstimation#createCopyFromItem(int[])
	 */
	@Override
	@Transactional
	protected void createCopyFromItem(int[] oldNoItemArray) {
		String SQL = "INSERT INTO SoumMateriel ( Copie, NoItem, Materiel, Cout, Unites, Dimension, " + 
					 "Quantite, Marge, NoFournisseur, CodeProduit, Perte ) " +
					 "SELECT SoumMateriel.NoItem, SoumMateriel.NoItem, SoumMateriel.Materiel, SoumMateriel.Cout, " + 
					 "SoumMateriel.Unites, SoumMateriel.Dimension, SoumMateriel.Quantite, SoumMateriel.Marge, " + 
					 "SoumMateriel.NoFournisseur, SoumMateriel.CodeProduit, SoumMateriel.Perte " +
					 "FROM SoumMateriel " + 
					 "WHERE NoItem = ?";
		
		for(int i=0; i<oldNoItemArray.length; i++)
			this.jdbcTemplateObject.update(SQL, oldNoItemArray[i]);

	}

	/* (non-Javadoc)
	 * @see services.simple.estimation.AbstractEstimation#updateNoItem(int[], int[])
	 */
	@Override
	@Transactional
	protected void updateNoItem(int[] oldNoItemArray, int[] newNoItemArray) {
		if(oldNoItemArray.length == newNoItemArray.length){
			
			String SQL = "UPDATE SoumMateriel "+
				 	  	 "SET SoumMateriel.NoItem = ?, SoumMateriel.Copie = NULL "+
				 	  	 "WHERE SoumMateriel.Copie = ?";			
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
	 * @see services.simple.estimation.MaterielDAO#addNew(int, java.lang.String, java.lang.String, 
	 * double, java.lang.String, double, double, double, int)
	 */
	@Override
	@Transactional
	public boolean addNew(int noItem, String codeProduit, String materiel, double cout,
			String unites, double perte, double marge, double dimension,
			double quantite) {
		
		String SQL;
		int numrows = 0;
		
		if(codeProduit.isEmpty()){
			SQL =  "INSERT INTO SoumMateriel " +
				  	 "(NoItem, Materiel, Cout, Unites, Perte, Marge, Dimension, Quantite) " +
				  	 "VALUES (?,?,?,?,?,?,?,?)";
			
			numrows = this.jdbcTemplateObject.update(SQL, noItem, materiel, cout, unites, 
					  perte, marge, dimension, quantite);
		}
		else{
			SQL = "INSERT INTO SoumMateriel " +
				  	 "(NoItem, CodeProduit, Materiel, Cout, Unites, Perte, Marge, Dimension, Quantite) " +
				  	 "VALUES (?,?,?,?,?,?,?,?,?)";
			
			numrows = this.jdbcTemplateObject.update(SQL, noItem, codeProduit, materiel, cout, unites, 
														  perte, marge, dimension, quantite);
		}
		
		return(numrows == 1);
		
	}

	
}
