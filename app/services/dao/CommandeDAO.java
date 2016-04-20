package services.dao;

import models.Commande;

public interface CommandeDAO {

	public Commande findByCustomerOrder(int order);
}
