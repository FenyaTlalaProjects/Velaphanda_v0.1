package com.demo.dao;

import java.util.List;
import java.util.Set;

import com.demo.model.Accessories;

public interface AccessoriesDaoInt {
	
	String saveAccessories(List<Accessories> accessories);
	String updateAccessories(List<Accessories> accessories);
	List<Accessories> getAccessoriesByDeviceSerial(String serialNumber);
	String removeAccessory(String[] strings);
	Accessories getAccessories(Long recordID);
	List<String> getAccessoriesList(String deviceSerialNumber);
}
