package com.demo.dao;

import java.util.List;

import com.demo.model.SpareMaster;



public interface SpareMasterDaoInt {
	
	List<SpareMaster> getSparesFromMastaData();
	SpareMaster getSpareMaster(String partNumber);
	String[] getSerials();
	List<String> getModelDevice(String partNumber);
	String saveSpareMasterData(SpareMaster spareMaster );

}
