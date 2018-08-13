package com.demo.service;

import java.util.List;
import java.util.Set;

import net.sf.jasperreports.engine.JRDataSource;

import com.demo.bean.DeviceBean;
import com.demo.model.Accessories;
import com.demo.model.Device;

public interface AccessoriesInt {
	String saveAccessories(List<Accessories> accessories);
	String updateAccessories(List<Accessories> accessories);
	List<Accessories> getAccessoriesByDeviceSerial(String serialNumber);
	Accessories getAccessories(Long recordID);
	List<String> getAccessoriesList(String deviceSerialNumber);
	String removeAccessory(String []serialNumbers,DeviceBean deviceBean);
	JRDataSource getAccessoriesByDeviceSerialDataSource(String serialNumber);

}
