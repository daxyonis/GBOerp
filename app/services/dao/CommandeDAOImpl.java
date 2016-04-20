package services.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import models.Commande;
import models.CommandeMapper;

//import play.Logger;

/**
 * @author Eva
 * This class does only READ operations on the Genius (BODB) database
 * to get customer order(s)
 */
@Repository
@Transactional(readOnly=true)
public class CommandeDAOImpl implements CommandeDAO {
	
	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public CommandeDAOImpl(JdbcTemplate jdbc){
		this.jdbcTemplateObject = jdbc;
	}

	@Override
	public Commande findByCustomerOrder(int order) {		
		try{
			String SQL = "SELECT * FROM BODB.dbo.vgCorHeader WHERE CustomerOrder = ?";
			String cusOrder = String.format("%08d", order); 
			//Logger.debug("Formatted Customer Order : " + cusOrder);
			Commande comm = jdbcTemplateObject.queryForObject(SQL, new Object[]{cusOrder}, new CommandeMapper());
			// Translate the status to a word
			comm.setStatusText(convertOrderStatusToText(comm.getStatus()));
			return comm;
		}
		catch(EmptyResultDataAccessException e){
			return null;
		}
	}
		
	private String convertOrderStatusToText(String status){
		try{
			String SQL = "SELECT Value FROM BODB.dbo.vgGMFCustomerOrderStatus WHERE Code = ?";
			String text = jdbcTemplateObject.queryForObject(SQL, String.class, status);
			return text;
		}
		catch(EmptyResultDataAccessException e){
			return new String("");
		}
	}

}
