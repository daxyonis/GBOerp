package services;

import models.Commande;
import models.Rep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.dao.CommandeDAO;
import services.dao.RepDAO;


@Service
public class CommandeServiceImpl implements CommandeService {
	
	private CommandeDAO commDAO;
	private RepDAO	repDAO;
	
	@Autowired
	public CommandeServiceImpl(CommandeDAO cd, RepDAO rd){
		this.commDAO = cd;
		this.repDAO = rd;
	}		

	@Override
	public Commande findByCustomerOrder(int order) {
		
		Commande comm = commDAO.findByCustomerOrder(order);
		Rep rep = repDAO.findByCode(comm.getSalesman());
		if(rep != null){
			comm.setSalesmanFullName(rep.getDescription1());
		}
		
		return comm;
	}

}
