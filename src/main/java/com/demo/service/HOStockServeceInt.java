package com.demo.service;


import java.util.List;

import com.demo.bean.SparePartsBean;
import com.demo.model.HOStock;

public interface HOStockServeceInt {
	public String saveSpareparts(HOStock spareParts,SparePartsBean sparePartsBean);
	public HOStock getSparePartBySerial(String serialNum);
	public List<HOStock> getAllSpareParts();
	public List<HOStock> getAllSparePartsWithoutZero();
	int countHeadOfficeStock();

}
