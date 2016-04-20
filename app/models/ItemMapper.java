/**
 * 
 */
package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * ItemMapper	pour faire la correspondance entre un ResultSet
 * 			    (résultat d'une requête SQL) et l'objet Item
 * @author Eva
 *
 */
public class ItemMapper implements RowMapper<Item>{
	
	public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
		Item item = new Item();
		
		// Set Item properties		
		item.setNoItem(rs.getInt("NoItem"));
		item.setNoSoumission(rs.getInt("NoSoumission"));
		item.setNomItem(rs.getNString("NomItem"));
		item.setDescription(rs.getNString("Description"));
		item.setQuantite(rs.getInt("Quantité"));
		item.setHauteur(rs.getNString("Hauteur"));
		item.setLargeur(rs.getNString("Largeur"));
		item.setFraisInstallation(rs.getDouble("FraisInstallation"));
		item.setFraisVariables(rs.getDouble("FraisVariables"));
		item.setPrixFab(rs.getDouble("PrixFab"));
		item.setSourceProd(rs.getNString("SourceProd"));
		item.setFamille(rs.getNString("Famille"));
		item.setRectoVerso(rs.getNString("RectoVerso"));
		
		// non-mandatory
//		item.setAvis(rs.getInt("Avis"));
//		item.setCatalogue(rs.getBoolean("Catalogue"));
//		item.setCodeCatalogue(rs.getNString("CodeCatalogue"));
//		item.setCopie(rs.getInt("Copie"));		
//		item.setDossier(rs.getInt("Dossier"));		
//		item.setFichierSource(rs.getNString("FichierSource"));		
//		item.setGeniusDetailID(rs.getInt("GeniusDetailsID"));
//		item.setGeniusItem(rs.getInt("GeniusItem"));		
//		item.setNoOrdre(rs.getInt("NoOrdre"));
//		item.setNotes(rs.getNString("Notes"));
//		item.setNotesInternes(rs.getNString("NotesInternes"));
//		item.setNouvelItem(rs.getInt("NouvelItem"));		
//		item.setQteEnv(rs.getInt("QteEnv"));
//		item.setQteRest(rs.getInt("QteRest"));		
//		item.setRaisonModif(rs.getNString("RaisonModif"));
//		item.setRectoVerso(rs.getNString("RectoVerso"));
//		item.setRevision(rs.getInt("Revision"));		
//		item.setSousCategorie(rs.getNString("SousCategorie"));
		
		
		return item;
   }

}
