package com.demo.dao;

import com.demo.model.VelaphandaProfile;

public interface VelaphandaProfileDaoInt {

	String saveVelaphandaAddress(VelaphandaProfile profile);
	VelaphandaProfile getVelaphandaAddress(String companyName);
}
