package com.demo.service;

import java.util.List;

import com.demo.bean.SpareQuantity;

public interface SpareQuantityInt {

	List<SpareQuantity> spareQuantity();
	List<SpareQuantity> spareQuantityForTechnicians();
	List<SpareQuantity> spareQuantityForTechnician(String technicianName);
	List<SpareQuantity> spareQuantityForTechnicianSiteStock();
}
