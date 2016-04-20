/**
 * 
 */
package services.dao;

import java.util.List;
import models.Accessoire;
import models.Type;

/**
 * @author Eva
 *
 */
public interface AccessoireDAO {
	
	public List<Accessoire> findByType(String pType);
	
	public List<Type> findTypes();
	
	public Accessoire findById(int id);

}
