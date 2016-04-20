/**
 * 
 */
package services.dao;

import java.util.List;

import models.Item;
import models.ItemMapper;
import models.MesItems;
import models.MesItemsMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Eva
 *
 */
@Repository
public class MesItemsDAOImpl implements MesItemsDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplateObject;

	/* (non-Javadoc)
	 * @see services.simple.MesItemsDAO#create(java.lang.String, int)
	 */
	public int create(String usager, int noItem) {
		String SQL = "INSERT INTO tmpMesItems (Usager, NoItem) " + 
					 "VALUES (?, ?)";
		
		int numrows = jdbcTemplateObject.update(SQL, usager, noItem);		
		
		return numrows;
	}

	/* (non-Javadoc)
	 * @see services.simple.MesItemsDAO#add(models.MesItems)
	 */
	public int add(MesItems mesItems) {		
		return create(mesItems.getUsager(), mesItems.getNoItem());		
	}

	/* (non-Javadoc)
	 * @see services.simple.MesItemsDAO#getAll(java.lang.String)
	 */
	public List<MesItems> getAll(String usager) {
		String SQL = "SELECT * FROM tmpMesItems WHERE Usager = ?";
		List <MesItems> mesItems = jdbcTemplateObject.query(SQL, new Object[]{usager}, new MesItemsMapper());
	    return mesItems;		
	}
	
	public int delete(String usager, int noItem){
		String SQL = "DELETE FROM tmpMesItems WHERE Usager = ? AND NoItem = ?";
		int numrows = jdbcTemplateObject.update(SQL, usager, noItem);
        return numrows;	
	}

	/* (non-Javadoc)
	 * @see services.simple.MesItemsDAO#delete(models.MesItems)
	 */
	public int delete(MesItems mesItems) {
		return delete(mesItems.getUsager(), mesItems.getNoItem());		
	}

	/* (non-Javadoc)
	 * @see services.simple.MesItemsDAO#deleteAll(java.lang.String)
	 */
	public int deleteAll(String usager) {
		String SQL = "DELETE FROM tmpMesItems WHERE Usager = ?";
		int numrows = jdbcTemplateObject.update(SQL, usager);
        return numrows;
	}
	
	/*
	 * (non-Javadoc)
	 * @see services.simple.MesItemsDAO#getAllItemsByUser(java.lang.String)
	 */
	public List<Item> getAllItemsByUser(String usager) {
		
		String SQL = "SELECT I.* FROM tmpMesItems M LEFT JOIN Items I ON I.NoItem = M.NoItem WHERE M.Usager = ?";
		List<Item> items = jdbcTemplateObject.query(SQL,  new Object[]{usager}, new ItemMapper());
	    return items;
	}
	

}
