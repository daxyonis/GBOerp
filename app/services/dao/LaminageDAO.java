package services.dao;

import models.Laminage;
import java.util.List;

public interface LaminageDAO {	

	public List<Laminage> findAllForCategory(String productCategory);
	
	public Laminage findById(int id);
}
