package services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.dao.ProdFamilyDAO;
import models.ProdFamily;

@Service
public class ProdFamilyServiceImpl implements ProdFamilyService {
	
	private ProdFamilyDAO prodFamDAO;
	
	@Autowired
	public ProdFamilyServiceImpl(ProdFamilyDAO pfdao){
		this.prodFamDAO = pfdao;
	}

	@Override
	public List<ProdFamily> findAll() {
		return prodFamDAO.findAll();
	}

}
