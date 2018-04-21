package com.demo.service;


import java.util.List;

import com.demo.model.HOStock;

public interface HOStockServeceInt {
	public String saveSpareparts(HOStock spareParts);
	public HOStock getSparePartBySerial(String serialNum);
	public List<HOStock> getAllSpareParts();
	public List<HOStock> getAllSparePartsWithoutZero();
	int countHeadOfficeStock();

}
