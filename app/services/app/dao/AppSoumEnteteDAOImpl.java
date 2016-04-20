package services.app.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import models.app.AppSoumEntete;
import models.app.AppSoumEnteteMapper;
import models.app.AppSoumissionMapper;

@Repository
public class AppSoumEnteteDAOImpl implements AppSoumEnteteDAO {

	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public AppSoumEnteteDAOImpl(JdbcTemplate jdbcTempl){
		this.jdbcTemplateObject = jdbcTempl;
	}
	
	@Override
	public Optional<AppSoumEntete> findByNo(int noSoum) {
		String SQL = "SELECT * FROM vboAppEntete WHERE NoSoumission = ?";		
		AppSoumEntete entete;		
		try{
			entete = jdbcTemplateObject.queryForObject(SQL, 
					new Object[]{noSoum}, 
					new AppSoumEnteteMapper(AppSoumissionMapper.FieldScopeEnum.ALL));
		}
		catch(EmptyResultDataAccessException e){
			// if not found, return null
			entete = null;
		}		
		return Optional.ofNullable(entete);
	}

	@Override
	public boolean update(AppSoumEntete entete) {
		if(entete.getNoSoumission() > 0){
					
			String SQL = "UPDATE vboAppEntete "
					+ "SET Critere=?, DateEntre=?, DateLivraison=?, Projet=?, ClientSoum=?, "
					+ "GeniusNoClient=?, BanniereClient=?, NoVendeur=?, NomVendeur=?, Description=?, Contact=?, "
					+ "ClientAdresse=?, ClientTelephone=?, ClientTelephoneContact=?, ClientFax=?, ClientVille=?, ClientFactureA=?, "
					+ "ClientEmail=?, ClientCP=?, ClientAPP=?, ClientProvince=?, ClientPays=? "
					+ "WHERE NoSoumission = ?";				
			
			// Here the fields that have a check constraint
			String critere = entete.getCritere();			
			String desc = entete.getDescription();
			String contact = entete.getContact();
			String nomVendeur = entete.getNomVendeur(); 
			
			int numrows = jdbcTemplateObject.update(SQL,
					(critere == null ? null : (critere.isEmpty() ? null : critere)), 
					entete.getDateEntre(), entete.getDateLivraison(),
					entete.getProjet(), entete.getClientSoum(), entete.getGeniusNoClient(), entete.getBanniereClient(),
					entete.getNoVendeur(), (nomVendeur == null? "Test" : (nomVendeur.isEmpty() ? "Test" : nomVendeur)),
					(desc == null ? null : (desc.isEmpty() ? null : desc)), 
					(contact == null ? null : (contact.isEmpty() ? null : contact)), 
					entete.getClientAdresse(),
					entete.getClientTelephone(), entete.getClientTelephoneContact(), entete.getClientFax(), entete.getClientVille(),
					entete.getClientFactureA(), entete.getClientEmail(), entete.getClientCP(), entete.getClientAPP(),
					entete.getClientProvince(), entete.getClientPays(),
					entete.getNoSoumission()
					);
			
			return(numrows == 1);
		}
		else{
			return false;
		}
	}

	
}
