package com.demo.dao;

import java.util.List;
import java.util.Set;

import net.sf.jasperreports.engine.JRDataSource;

import com.demo.model.Accessories;

public interface AccessoriesDaoInt {
	
	String saveAccessories(List<Accessories> accessories);
	String updateAccessories(List<Accessories> accessories);
	List<Accessories> getAccessoriesByDeviceSerial(String serialNumber);
	String removeAccessory(String[] strings);
	Accessories getAccessories(Long recordID);
	List<String> getAccessoriesList(String deviceSerialNumber);
	JRDataSource getAccessoriesByDeviceSerialDataSource(String serialNumber);
}
