package com.demo.dao;

import java.util.List;

import com.demo.bean.SpareQuantity;

public interface SpareQuantityDaoInt {

	List<SpareQuantity> spareQuantity();
	List<SpareQuantity> spareQuantityForTechnicians();
	List<SpareQuantity> spareQuantityForTechnician(String technicianName);
	List<SpareQuantity> spareQuantityForTechnicianSiteStock();
}
