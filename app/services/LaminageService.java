package services;

import models.Laminage;
import java.util.List;

public interface LaminageService {

	public List<Laminage> findAllForCategory(String productCategory);
	
	public Laminage findById(int id);
}
