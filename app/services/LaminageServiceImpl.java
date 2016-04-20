package services;

import java.util.List;

import models.Laminage;
import services.dao.LaminageDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LaminageServiceImpl implements LaminageService{
	
	private LaminageDAO lamDAO;
	
	@Autowired
	public LaminageServiceImpl(LaminageDAO lamDAO){
		this.lamDAO = lamDAO;
	}

	@Override
	public List<Laminage> findAllForCategory(String productCategory) {
		return lamDAO.findAllForCategory(productCategory);
	}

	@Override
	public Laminage findById(int id) {
		return lamDAO.findById(id);
	}
	
	

}
