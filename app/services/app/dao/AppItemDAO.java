package services.app.dao;

import java.util.List;
import java.util.Optional;

import models.app.AppItem;

public interface AppItemDAO {
	
	public Optional<AppItem> findByNo(int noItem);
	
	public List<AppItem> findAllForSoum(int noSoum);

	public boolean save(AppItem item);
	
	public int add(AppItem item);

	public boolean remove(AppItem item);
}
