/**
 * 
 */
package services;

import java.util.List;
import models.Finition;

/**
 * @author Eva
 * Service Interface for Finition objects
 */
public interface FinitionService {
	
	public List<Finition> findAllActive();
	
	public List<Finition> findAll();
	
	public Finition getById(int id);
	
	public boolean save(Finition finition);
	
	public boolean add(Finition finition);
	
	public boolean remove(Finition finition);

}
