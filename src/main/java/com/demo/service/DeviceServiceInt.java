package com.demo.service;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;

import com.demo.bean.DeviceBean;
import com.demo.model.Accessories;
import com.demo.model.Device;

public interface DeviceServiceInt {
	
	String saveDevice(Device device);
	Device getDeviceBySerialNumber(String serialNumber);
	List<Device> getDeviceList();
	List<Device> getDeviceListByClientName(String clientName);
	List<Accessories> accessories(Device device);
	String updateDevice(Device device);
	String prepareDeviceData(DeviceBean deviceBean);
	DeviceBean getAccessoriesForUpdate(String serialNumber);
	Integer count();
	List<Device> getAllEmployees(Integer offset, Integer maxResults,String clientName);
	String[] getSerials();
	String replaceToner(String compitableSiteStock,String currentMonoReading,String currentColourReading,String firstName,String lastName,String loggedInUser,String contactEmail,String contactTelephoneNumber,String contactCellNumber,String description,String serialNumber
			);
	JRDataSource getDeviceListDataSource();
	
}
