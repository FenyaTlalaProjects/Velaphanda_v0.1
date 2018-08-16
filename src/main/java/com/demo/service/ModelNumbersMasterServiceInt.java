package com.demo.service;

import java.util.List;

import com.demo.model.ModelNumbers;
import com.demo.model.SpareMaster;

public interface ModelNumbersMasterServiceInt {

	String[] getModelNumbers();
	List<ModelNumbers> getModelNumbersFromMasterData();
	ModelNumbers getModelNumbersMaster(String modelNumber);
	List<String> getAllModelNumbers(String modelNumber);
	String saveModelNumberData(ModelNumbers modelNumber);
}
