package com.demo.dao.impl;

import java.util.List;




import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.TechnicianSiteDaoInt;
import com.demo.model.TechnicianSite;

/*@Repository("technicianSiteDAO")
@Transactional(propagation=Propagation.REQUIRED)*/
public class TechnicianSiteDao implements TechnicianSiteDaoInt{

	@Override
	public String assingTechniciansToSite(List<TechnicianSite> technicians) {
		
		return null;
	}

	@Override
	public String removeTechnicianOnSite(List<TechnicianSite> technicians) {
		
		return null;
	}

}
