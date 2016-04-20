/**
 * 
 */
package services;
import models.ActiviteMO;

import java.util.List;

/**
 * @author Eva
 *
 */
public interface ActiviteMOService {
		
	public String getMOcategory(String key);	
	public ActiviteMO findByActivite(String category, String activite);
	public List<ActiviteMO> findAll(String category);

}
