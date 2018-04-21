package com.demo.dao;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.demo.bean.DeviceBean;
import com.demo.model.Accessories;
import com.demo.model.Device;

public interface DeviceDaoInt {
	
	String saveDevice(Device device);
	Device getDeviceBySerialNumbuer(String serialNumber);
	List<Device> getDeviceList();
	List<Device> getDeviceList(Integer offset, Integer maxResults,String clientName);
	List<Device> getDeviceListByClientName(String clientName);
	List<Accessories> accessories(Device device);
	String updateDevice(Device device);
	String prepareDeviceData(DeviceBean deviceBean);
	DeviceBean getAccessoriesForUpdate(Long recordID);
	Integer count();
	String[] getSerials();
	String replaceToner(String compitableSiteStock,String currentMonoReading,String currentColourReading,String firstName,String lastName,String loggedInUser,String contactEmail,String contactTelephoneNumber,String contactCellNumber,String description,String serialNumber
			);
}
