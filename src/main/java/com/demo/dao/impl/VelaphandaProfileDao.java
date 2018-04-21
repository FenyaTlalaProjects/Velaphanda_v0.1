package com.demo.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.VelaphandaProfileDaoInt;
import com.demo.model.SiteStock;
import com.demo.model.VelaphandaProfile;


@Repository("velaphandaProfileDao")
@Transactional(propagation=Propagation.REQUIRED)
public class VelaphandaProfileDao implements VelaphandaProfileDaoInt{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public String saveVelaphandaAddress(VelaphandaProfile profile) {
		
		sessionFactory.getCurrentSession().save(profile);
		return profile.getCompanyName();
	}

	@Override
	public VelaphandaProfile getVelaphandaAddress(String companyName) {
	    return (VelaphandaProfile) sessionFactory.getCurrentSession().get(VelaphandaProfile.class, companyName);
	}

}
