package com.demo.dao;

import java.util.List;

import com.demo.model.TechnicianSite;


public interface TechnicianSiteDaoInt {
	
	String assingTechniciansToSite(List<TechnicianSite> technicians);
	String removeTechnicianOnSite(List<TechnicianSite> technicians);
	

}
