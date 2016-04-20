package services.dao;

import models.Flexible;

public interface LienProduitImprimanteDAO {
	
	public boolean updateLien(Flexible flex);
	public boolean removeLien(Flexible flex);
	public boolean removeLienForProduct(String type, int id);	
}
