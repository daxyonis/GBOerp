/**
 * 
 */
package services.dao.estimation;

import models.Imprimante;
import models.SoumFlex;
import models.SoumFlexItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Eva
 *
 */
@Repository
public class NumeriqueDAOImpl extends AbstractEstimation implements NumeriqueDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplateObject;

	/* (non-Javadoc)
	 * @see services.simple.estimation.AbstractEstimation#createCopyFromItem(int[])
	 */
	@Override
	@Transactional
	protected void createCopyFromItem(int[] oldNoItemArray) {
		
		String SQL = "INSERT INTO SoumNumerique ( Copie, NoItem, Hauteur, Largeur, NbrCote, NbrItem, " + 
					 "FinitionCoutures, FinitionOeillet, FinitionSangle, FinitionAeration, PourcentageEncre, " + 
					 "PerteEncre, MargeEncre, TauxManipulation, TauxImpression, MargeManipulation, ManipulationItem, " + 
					 "MargeImpression, VitesseImpression, MontantCouture, MontantOeillet, MontantSangle, MontantAeration, " + 
					 "MontantAutre, PrixEncre, CodeSelection, MargeAutre, MargeFinition, MargeSupp, Materiel, CoutMat, " + 
					 "UnitesMat, PerteMat, MargeMat, DimensionMat, QuantiteMat, CodeProduitMat, MontantFinitionValidation, " + 
					 "FinitionValidation ) " +
					 "SELECT SoumNumerique.NoItem, SoumNumerique.NoItem, SoumNumerique.Hauteur, SoumNumerique.Largeur, " + 
					 "SoumNumerique.NbrCote, SoumNumerique.NbrItem, SoumNumerique.FinitionCoutures, SoumNumerique.FinitionOeillet, " +
					 "SoumNumerique.FinitionSangle, SoumNumerique.FinitionAeration, SoumNumerique.PourcentageEncre, " + 
					 "SoumNumerique.PerteEncre, SoumNumerique.MargeEncre, SoumNumerique.TauxManipulation, " + 
					 "SoumNumerique.TauxImpression, SoumNumerique.MargeManipulation, SoumNumerique.ManipulationItem, " + 
					 "SoumNumerique.MargeImpression, SoumNumerique.VitesseImpression, SoumNumerique.MontantCouture, " + 
					 "SoumNumerique.MontantOeillet, SoumNumerique.MontantSangle, SoumNumerique.MontantAeration, " + 
					 "SoumNumerique.MontantAutre, SoumNumerique.PrixEncre, SoumNumerique.CodeSelection, " + 
					 "SoumNumerique.MargeAutre, SoumNumerique.MargeFinition, SoumNumerique.MargeSupp, SoumNumerique.Materiel, " + 
					 "SoumNumerique.CoutMat, SoumNumerique.UnitesMat, SoumNumerique.PerteMat, SoumNumerique.MargeMat, " + 
					 "SoumNumerique.DimensionMat, SoumNumerique.QuantiteMat, SoumNumerique.CodeProduitMat, " + 
					 "SoumNumerique.MontantFinitionValidation, SoumNumerique.FinitionValidation " + 
					 "FROM SoumNumerique " + 
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
			
			String SQL = "UPDATE SoumNumerique "+
				 	  	 "SET SoumNumerique.NoItem = ?, SoumNumerique.Copie = NULL "+
				 	  	 "WHERE SoumNumerique.Copie = ?";			
			
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
	 * @see services.simple.estimation.NumeriqueDAO#createFromSoumFlex(int, models.SoumFlex, models.Imprimante)
	 */
	@Override
	@Transactional
	public boolean createFromSoumFlex(int noItem, SoumFlex soumflex, SoumFlexItem item, Imprimante imprimante) {
		
		String SQL = "INSERT INTO SoumNumerique " +
				     "(NoItem, Hauteur, Largeur, NbrCote, NbrItem, CodeSelection," +
				     "PourcentageEncre, PerteEncre, MargeEncre, TauxManipulation," +
				     "TauxImpression, MargeManipulation, MargeImpression, ManipulationItem," +
				     "VitesseImpression, PrixEncre, " +
				     "MontantCouture, MontantOeillet, MontantSangle, MontantAeration) " +
				     "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
				     "?,?,?,?)";
		
		int NbrCote = 1;
		String rv = item.getRv();
		if((rv != null) && !rv.isEmpty()){
			NbrCote = rv.equals("R/V") ? 2 : 1;
		}
 
		
		int numrows = this.jdbcTemplateObject.update(SQL, noItem, item.getHaut(), //getItemHaut0(),
				item.getLarg(), NbrCote, item.getQte(), item.getImpSelect(),
				imprimante.getPourcentEncre(), imprimante.getPerteEncre(), imprimante.getMargeEncre(),
				imprimante.getTauxManip(), imprimante.getTauxImpr(), soumflex.getMargeGlobale(),
				soumflex.getMargeGlobale(), item.getImpTempsManip(), imprimante.getVitesse(),
				imprimante.getPrixEncre(),
				0,0,0,0);
	
		return(numrows==1);
	}

}
