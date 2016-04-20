package services.app;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import services.app.dao.AppItemDAO;
import models.app.AppItem;

@Service
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class AppItemServiceImpl implements AppItemService {
	
	private AppItemDAO appItemDAO;
	
	@Autowired
	public void setAppItemDAO(AppItemDAO appItemDAO) {
		this.appItemDAO = appItemDAO;
	}
	

	@Override
	public Optional<AppItem> findByNo(int noItem) {
		return appItemDAO.findByNo(noItem);
	}

	@Override
	public List<AppItem> findAllForSoum(int noSoum) {
		return appItemDAO.findAllForSoum(noSoum);
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public boolean save(AppItem item) {
		return appItemDAO.save(item);
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public int add(AppItem item) {
		return appItemDAO.add(item);
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public boolean remove(AppItem item) {
		return appItemDAO.remove(item);
	}
	
	

}
