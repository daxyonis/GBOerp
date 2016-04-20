/**
 * 
 */
package services.dao.estimation;

import models.Imprimante;
import models.SoumFlex;
import models.SoumFlexItem;

/**
 * @author Eva
 *
 */
public interface NumeriqueDAO {
	
	/**
	 * Creates the estimation needed in SoumNumerique from
	 * @param noItem     the NoItem this Numerique belongs to 
	 * @param soumflex   the SoumFlex object (the submitted Soumission Rapide Flexible)
	 * @param item		 the given item to which this estimation is linked
	 * @param imprimante the Imprimante object with info about chosen printer
	 * @return
	 */	
	public boolean createFromSoumFlex(int noItem, SoumFlex soumflex, SoumFlexItem item, Imprimante imprimante);

}
