package com.demo.dao;


import java.util.List;

import com.demo.bean.SparePartsBean;
import com.demo.model.HOStock;

public interface HOStockDaoInt {
	
	public String saveSpareparts(HOStock spareParts,SparePartsBean sparePartsBean);
	public HOStock getSparePartBySerial(String serialNum);
	public String updateSpareParts(HOStock spareParts);
	public List<HOStock> getAllSpareParts();
	public List<HOStock> getAllSparePartsWithoutZero();
	int countHeadOfficeStock();
}
