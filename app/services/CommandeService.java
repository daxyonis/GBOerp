package services;

import models.Commande;

public interface CommandeService {

	public Commande findByCustomerOrder(int order);
}
