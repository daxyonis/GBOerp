package services;

import java.util.List;

import models.Imprimante;

/**
 * @author Eva
 *
 */
public interface ImprimanteService {
	
	public List<Imprimante> findForProduct(String type, int productId);

	public Imprimante findByCode(String codeSelection);
	
	public List<Imprimante> findAll();
	
	public boolean save(Imprimante imp);
	
	public boolean add(Imprimante imp);

	public boolean remove(Imprimante imp);
}
