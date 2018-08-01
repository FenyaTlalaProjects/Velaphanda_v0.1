package com.demo.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bean.SparePartsBean;
import com.demo.dao.HOStockDaoInt;
import com.demo.model.HOStock;
import com.demo.service.HOStockServeceInt;

@Service("HOStockService")
public class HOStockService implements HOStockServeceInt{

	@Autowired
	private HOStockDaoInt sparePartsDAO;
	
	private String retMessage = null;
	
	
	@Override
	public String saveSpareparts(HOStock spareParts,SparePartsBean sparePartsBean) {
		
		retMessage =sparePartsDAO.saveSpareparts(spareParts,sparePartsBean);
	    return retMessage;
	}


	@Override
	public HOStock getSparePartBySerial(String serialNum) {
		
		return sparePartsDAO.getSparePartBySerial(serialNum);
	}


	@Override
	public List<HOStock> getAllSpareParts() {
		return sparePartsDAO.getAllSpareParts();
	}


	@Override
	public List<HOStock> getAllSparePartsWithoutZero() {
		// TODO Auto-generated method stub
		return sparePartsDAO.getAllSparePartsWithoutZero();
	}


	@Override
	public int countHeadOfficeStock() {
		return sparePartsDAO.countHeadOfficeStock();
	}

}
