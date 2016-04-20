/**
 * 
 */
package services;

import java.util.List;
import models.Accessoire;
import models.Type; 

/**
 * @author Eva
 * Service interface for the Accessoire object
 */
public interface AccessoireService {
	
	public List<Accessoire> findByType(String pType);
	
	public List<Type> findTypes();
	
	public Accessoire findById(int id);

}
