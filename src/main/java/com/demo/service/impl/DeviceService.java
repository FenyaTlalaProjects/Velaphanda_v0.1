package com.demo.service.impl;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bean.DeviceBean;
import com.demo.dao.DeviceDaoInt;
import com.demo.model.Accessories;
import com.demo.model.Device;
import com.demo.model.Employee;
import com.demo.service.DeviceServiceInt;


//Device
@Service("productService")
public class DeviceService implements DeviceServiceInt {
	
	@Autowired
	private DeviceDaoInt deviceDAO;
	private String retMessage = null;

	@Override
	public String saveDevice(Device device) {
		retMessage =deviceDAO.saveDevice(device);
		return retMessage;
		
	}

	@Override
	public Device getDeviceBySerialNumber(String serialNumber) {
		
		return deviceDAO.getDeviceBySerialNumbuer(serialNumber);
	}

	@Override
	public List<Device> getDeviceList() {
		return deviceDAO.getDeviceList();
	}

	@Override
	public List<Device> getDeviceListByClientName(String clientName) {
		return deviceDAO.getDeviceListByClientName(clientName);
	}

	@Override
	public List<Accessories> accessories(Device device) {
		// TODO Auto-generated method stub
		return deviceDAO.accessories(device);
	}

	@Override
	public String updateDevice(Device device) {
		retMessage = deviceDAO.updateDevice(device);
		return retMessage;
	}

	@Override
	public String prepareDeviceData(DeviceBean deviceBean) {
		retMessage = deviceDAO.prepareDeviceData(deviceBean);
		return retMessage;
	}

	@Override
	public DeviceBean getAccessoriesForUpdate(String serialNumber) {
		return null;
		//return deviceDAO.getAccessoriesForUpdate(serialNumber);
	}

	@Override
	public Integer count() {
		return deviceDAO.count();
	}

	@Override
	public List<Device> getAllEmployees(Integer offset, Integer maxResults,String clientName) {
		
		return deviceDAO.getDeviceList(offset, maxResults,clientName);
	}

	@Override
	public String[] getSerials() {
		return deviceDAO.getSerials();
	}

	@Override
	public String replaceToner(String compitableSiteStock,
			String currentMonoReading, String currentColourReading,
			String firstName, String lastName,String loggedInUser, String contactEmail,
			String contactTelephoneNumber,String contactCellNumber,String description, String serialNumber) {
		
		return deviceDAO.replaceToner(compitableSiteStock, currentMonoReading, currentColourReading, firstName, lastName,loggedInUser ,contactEmail, contactTelephoneNumber,contactCellNumber,description, serialNumber);
	}

	@Override
	public JRDataSource getDeviceListDataSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JRDataSource getDeviceDetailsDataSource(String serialNumber) {
		// TODO Auto-generated method stub
		return deviceDAO.getDeviceDetailsDataSource(serialNumber);
	}

}
