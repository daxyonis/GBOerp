package services;

import java.util.List;

import services.dao.StatutDAO;
import models.Statut;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatutServiceImpl implements StatutService {
	
	private StatutDAO statutDAO;
	
	@Autowired
	public StatutServiceImpl(StatutDAO sDAO){
		this.statutDAO = sDAO;
	}

	@Override
	public List<Statut> findAll() {
		return statutDAO.findAll();
	}

}
