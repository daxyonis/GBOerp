/**
 * 
 */
package services;

import models.Item;
import models.MesItems;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.dao.MesItemsDAO;
import services.dao.MesItemsDAOImpl;

/**
 * @author Eva
 *
 */
@Service
public class MesItemsServiceImpl implements MesItemsService {		
	
	private MesItemsDAO mesItemsDAO;
	
	@Autowired
	public void setMesItemsDAO(MesItemsDAOImpl mesItemsDAOImpl) {
		this.mesItemsDAO = mesItemsDAOImpl;
	}
	
	/************************************************
	 * Methods from MesItemsDAO
	 */
	@Override
	public int create(String usager, int noItem){
		return mesItemsDAO.create(usager, noItem);
	}
	
	@Override
	public int add(MesItems mesItems){
		return mesItemsDAO.add(mesItems);
	}
	
	@Override
	public List<MesItems> getAll(String usager){
		return mesItemsDAO.getAll(usager);
	}
		
	@Override
	public int delete(String usager, int noItem){
		return mesItemsDAO.delete(usager, noItem);
	}
		
	@Override
	public int delete(MesItems mesItems){
		return mesItemsDAO.delete(mesItems);
	}
		
	@Override
	public int deleteAll(String usager){
		return mesItemsDAO.deleteAll(usager);
	}
	
	@Override
	public List<Item> getAllItemsByUser(String usager){
		return mesItemsDAO.getAllItemsByUser(usager);
	}
	/***********************************************/
}
