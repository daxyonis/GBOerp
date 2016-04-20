/**
 * 
 */
package services.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;

import models.FlexibleDTO;
import models.FlexibleDTOMapper;

/**
 * @author Eva
 * Implementation of the FlexibleDTODAO interface (data access to FlexibleDTO objects)
 */
@Repository
@Transactional(readOnly=true)
public class FlexibleDTODAOImpl implements FlexibleDTODAO {

	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public FlexibleDTODAOImpl(JdbcTemplate jdbcTempl){
		this.jdbcTemplateObject = jdbcTempl;
	}
	
	/* (non-Javadoc)
	 * @see services.simple.FlexibleDTODAO#findAll()
	 */
	public List<FlexibleDTO> findAll() {
		// NOTE: On calcule le cout (en $/pi2) comme etant le cout 
		// converti maximal entre LastCost et AvgCost (de sorte qu'on n'est
		// jamais en-bas de la moyenne).
		String SQL = "SELECT * " + 
					 "FROM MaitreFlexibles "+
					 "JOIN (" +
						"SELECT	MF.NoItemGenius As ItemGenius," +
						"MAX((CASE "+ 
							"WHEN LastCost > AvgCost THEN LastCost "+
							"ELSE AvgCost "+ 
						"END)/(CrossCategoriesFactor * UnitConversionFactor)) As Pi2Cost, "+
						"MIN(UA.UnitConversionDestination) As Unit " +
						"FROM MaitreFlexibles MF LEFT JOIN (BODB.dbo.vgCORUnitAvailable UA INNER JOIN BODB.dbo.vgMfiItems I  " +
						"ON (UA.Item = I.Item)) "+
						"ON (I.Item LIKE MF.NoItemGenius+'%') "+
						"WHERE(I.Active=1)"+
						"and (UA.ItemDefaultUnit = I.Unit) "+
						"and (UA.UnitConversionDestination = 'PI2') "+
						"GROUP BY NoItemGenius "+
						")As ItemMaxCost ON (ItemMaxCost.ItemGenius = MaitreFlexibles.NoItemGenius) "+
						"WHERE Categorie <> 'LAMINATION' "+
						"ORDER BY Categorie, Produit ";
								
	    List <FlexibleDTO> flexDtos = jdbcTemplateObject.query(SQL, new FlexibleDTOMapper());
	    return flexDtos;
	}

	/* (non-Javadoc)
	 * @see services.simple.FlexibleDTODAO#findById(int)
	 */
	public FlexibleDTO findById(int flexId) {
		String SQL = "SELECT * " + 
				 "FROM MaitreFlexibles "+
				 "JOIN (" +
					"SELECT	MF.NoItemGenius As ItemGenius," +
					"MAX((CASE "+ 
						"WHEN LastCost > AvgCost THEN LastCost "+
						"ELSE AvgCost "+ 
					"END)/(CrossCategoriesFactor * UnitConversionFactor)) As Pi2Cost, "+
					"MIN(UA.UnitConversionDestination) As Unit " +
					"FROM MaitreFlexibles MF LEFT JOIN (BODB.dbo.vgCORUnitAvailable UA INNER JOIN BODB.dbo.vgMfiItems I  " +
					"ON (UA.Item = I.Item)) "+
					"ON (I.Item LIKE MF.NoItemGenius+'%') "+
					"WHERE(I.Active=1)"+
					"and (UA.ItemDefaultUnit = I.Unit) "+
					"and (UA.UnitConversionDestination = 'PI2') "+
					"GROUP BY NoItemGenius "+
					")As ItemMaxCost ON (ItemMaxCost.ItemGenius = MaitreFlexibles.NoItemGenius) "+
					"WHERE Id = ?";
		
		FlexibleDTO flexDTO;
		
		try{
			flexDTO = jdbcTemplateObject.queryForObject(SQL, new Object[]{flexId}, new FlexibleDTOMapper());
		}
		catch(EmptyResultDataAccessException e){
			flexDTO = null;
		}
		
		return flexDTO;
	}

}
