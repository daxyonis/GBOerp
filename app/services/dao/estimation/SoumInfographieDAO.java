/**
 * 
 */
package services.dao.estimation;

import models.SoumFlex;
import models.SoumFlexItem;

/**
 * @author Eva
 *
 */
public interface SoumInfographieDAO {
	
	/**
	 * Creates the estimation needed in SoumInfographie from
	 * @param noItem     the NoItem this Infographie belongs to 
	 * @param soumflex   the SoumFlex object (the submitted Soumission Rapide Flexible)
	 * @param item		 the item to which this estimation is linked
	 * @return true if the creation succeeded; false otherwise
	 */
	public boolean createFromSoumFlex(int noItem, SoumFlex soumflex, SoumFlexItem item);

}
