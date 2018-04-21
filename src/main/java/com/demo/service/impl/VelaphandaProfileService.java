package com.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.VelaphandaProfileDaoInt;
import com.demo.model.VelaphandaProfile;
import com.demo.service.VelaphandaInt;

@Service("velaphandaProfileService")
@Transactional
public class VelaphandaProfileService implements VelaphandaInt{

	@Autowired
	private VelaphandaProfileDaoInt velas;
	@Override
	public String saveVelaphandaAddress(VelaphandaProfile profile) {
		
		return velas.saveVelaphandaAddress(profile);
	}

	@Override
	public VelaphandaProfile getVelaphandaAddress(String companyName) {
		return velas.getVelaphandaAddress(companyName);
	}

}
