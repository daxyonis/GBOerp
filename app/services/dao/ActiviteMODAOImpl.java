/**
 * 
 */
package services.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.ActiviteMO;
import models.ActiviteMOMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
/**
 * @author Eva
 * DAO implementation for accessing infographie activities
 */
@Repository
public class ActiviteMODAOImpl implements ActiviteMODAO {
	
	/**
	 * Internal map that binds a simple 
	 * string label to the true category name  
	 */
	private static final Map<String, String> MOcategoryMap = createMap();
	
	private static Map<String, String> createMap() { 
        Map<String, String> aMap = new HashMap<String, String>();
        aMap.put("infographie", "Infographie");
        aMap.put("assemblage",	"Montage / Assemblage");
        aMap.put("cnc", 		"Coupe Matériel / CNC");
        aMap.put("decoupe",		"Découpe Vinyle");
        aMap.put("demasquage",	"Démasquage");
        aMap.put("emballage",	"Emballage");
        aMap.put("menuiserie",	"Menuiserie");
        aMap.put("peinture",	"Peinture");
        aMap.put("ventes",		"Ventes");
        return Collections.unmodifiableMap(aMap);
    }
	
	public String getMOcategory(String key){
		return MOcategoryMap.get(key);
	}
	
	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public ActiviteMODAOImpl(JdbcTemplate jdbcTempl){
		this.jdbcTemplateObject = jdbcTempl;
	}

	/* (non-Javadoc)
	 * @see services.simple.ActiviteMODAO#findByActivite(java.lang.String)
	 */
	public ActiviteMO findByActivite(String category, String activite) {
		String SQL = "SELECT MR.Description1 As Activite, MR.Machine As CodeMachine, " +
				"OP.OperationCode As CodeOp,(MR.MachineRateCost+MR.AverageWorkerRate) AS TauxGlobal " +
				"FROM BODB.dbo.vgMfiOperations AS OP LEFT JOIN BODB.dbo.vgMfiMachineAndResource AS MR ON OP.OperationCode = MR.OperationLink " +
				"WHERE ((OP.Description2)=?) AND (MR.Description1 = ?)";
		
		ActiviteMO mo = jdbcTemplateObject.queryForObject(SQL, new Object[]{category, activite}, new ActiviteMOMapper(category));
		return mo;
	}

	/* (non-Javadoc)
	 * @see services.simple.ActiviteMODAO#findAll()
	 */
	public List<ActiviteMO> findAll(String category) {
		String SQL ="SELECT MR.Description1 As Activite, MR.Machine As CodeMachine, " +
				"OP.OperationCode As CodeOp,(MR.MachineRateCost+MR.AverageWorkerRate) AS TauxGlobal " +
				"FROM BODB.dbo.vgMfiOperations AS OP LEFT JOIN BODB.dbo.vgMfiMachineAndResource AS MR ON OP.OperationCode = MR.OperationLink " +
				"WHERE (((MR.Machine) Is Not Null) AND ((OP.Description2)=?) " +
				"AND ((MR.Active)=1) AND ((OP.Active)=1)) " +
				"ORDER BY MR.Description1";
		
		List <ActiviteMO> mo = jdbcTemplateObject.query(SQL, new Object[]{category}, new ActiviteMOMapper(category));
	    return mo;
	}

}
