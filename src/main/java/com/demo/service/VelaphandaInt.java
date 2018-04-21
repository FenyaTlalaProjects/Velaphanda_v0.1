package com.demo.service;

import com.demo.model.VelaphandaProfile;

public interface VelaphandaInt {

	String saveVelaphandaAddress(VelaphandaProfile profile);
	VelaphandaProfile getVelaphandaAddress(String companyName);
}
