/**
 * 
 */
package services.dao;

import models.ActiviteMO;

import java.util.List;

/**
 * @author Eva
 *
 */
public interface ActiviteMODAO {

	public String getMOcategory(String key);
	public ActiviteMO findByActivite(String category, String activite);
	public List<ActiviteMO> findAll(String category);
}
