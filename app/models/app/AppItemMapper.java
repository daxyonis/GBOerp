package models.app;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AppItemMapper implements RowMapper<AppItem> {

	@Override
	public AppItem mapRow(ResultSet rs, int row) throws SQLException {
		AppItem item = new AppItem();
		
		item.setCatalogue(rs.getBoolean("Catalogue"));
		item.setCodeCatalogue(rs.getNString("CodeCatalogue"));
		item.setDescription(rs.getNString("Description"));
		item.setFacturation(rs.getString("Facturation"));
		item.setFamille(rs.getNString("Famille"));
		item.setFichierSource(rs.getNString("FichierSource"));
		item.setFlagDetail(rs.getInt("FlagDetail"));
		item.setFraisInstallation(rs.getDouble("FraisInstallation"));
		item.setFraisVariables(rs.getDouble("FraisVariables"));
		item.setGeniusDetailID(rs.getInt("GeniusDetailID"));
		item.setGeniusItem(rs.getInt("GeniusItem"));
		item.setHauteur(rs.getNString("Hauteur"));
		item.setLargeur(rs.getNString("Largeur"));
		item.setNoItem(rs.getInt("NoItem"));
		item.setNoSoumission(rs.getInt("NoSoumission"));
		item.setNomItem(rs.getNString("NomItem"));
		item.setNoOrdre(rs.getInt("NoOrdre"));
		item.setNotes(rs.getNString("Notes"));
		item.setNotesInternes(rs.getNString("NotesInternes"));
		item.setPrixFab(rs.getDouble("PrixFab"));
		item.setQuantite(rs.getInt("Quantité"));
		item.setRectoVerso(rs.getNString("RectoVerso"));
		item.setSourceProd(rs.getNString("SourceProd"));
		item.setSousCategorie(rs.getNString("SousCategorie"));		
		
		return item;
	}
	
	

}
