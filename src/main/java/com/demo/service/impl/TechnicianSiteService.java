package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.model.TechnicianSite;
import com.demo.service.TechnicianSiteDaoInt;


/*@Service("technicianService")
@Transactional*/
public class TechnicianSiteService implements TechnicianSiteDaoInt{
	@Autowired
	private TechnicianSiteDaoInt technicianSiteDao;

	@Override
	public String assingTechniciansToSite(List<TechnicianSite> technicians) {
		return technicianSiteDao.assingTechniciansToSite(technicians);
	}

	@Override
	public String removeTechnicianOnSite(List<TechnicianSite> technicians) {
		
		return technicianSiteDao.removeTechnicianOnSite(technicians);
	}

}
