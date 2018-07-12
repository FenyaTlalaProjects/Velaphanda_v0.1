package com.demo.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.AccessoriesDaoInt;
import com.demo.dao.DeviceDaoInt;
import com.demo.dao.EmployeeDaoInt;
import com.demo.model.Accessories;
import com.demo.model.Device;
import com.demo.reports.initializer.DeviceReportBean;

@Repository("accessoriesDAO")
@Transactional(propagation=Propagation.REQUIRED)
public class AccessoriesDao implements AccessoriesDaoInt {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	private DeviceDaoInt deviceDaoInt;
	
	@Autowired
	private EmployeeDaoInt employeeDaoInt;

	List<Accessories> accessoriesList = null;
	List<Accessories> aList = null;
	Accessories acc = null;
	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date now = new Date();
	String timeDeviceAccessAdded = sdfDate.format(now);
	private String retMessage = null;

	
	@Override
	public String saveAccessories(List<Accessories> accessories) {

		try {

			if (accessories.isEmpty() == false) {
				for (Accessories access : accessories) {
					if (access.getSerial().length()>3) {
						access.setDateTime(timeDeviceAccessAdded);
						sessionFactory.getCurrentSession().save(access);
						retMessage = "OK";
					}

				}
			} else {
				retMessage = "OK";
			}

		} catch (Exception e) {
			retMessage = e.getMessage();
		}
		return retMessage;
	}

	@Override
	public String updateAccessories(List<Accessories> accessories) {

		try {
			for (Accessories access : accessories) {
				access.setDateTime(timeDeviceAccessAdded);
				sessionFactory.getCurrentSession().update(access);
				retMessage = "OK";
			}
		} catch (Exception e) {
			retMessage = "Error";
		}
		return retMessage;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Accessories> getAccessoriesByDeviceSerial(String serialNumber) {
		
		accessoriesList = new ArrayList<Accessories>();
		try {
			aList = getAccessoriesByDeviceSerial();
			  for (Accessories access : aList) {
				if(access.getDevice().getSerialNumber().equalsIgnoreCase(serialNumber) && access.getDevice().getSerialNumber()!=null){
					accessoriesList.add(access);
				}
			}
		} catch (Exception ex) {
			ex.getMessage();
		}
		return accessoriesList;
	}
	@SuppressWarnings("unchecked")
	private List<Accessories> getAccessoriesByDeviceSerial(){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Accessories.class);
		return (List<Accessories>) criteria.list();
	}

	@Override
	public String removeAccessory(String []serialNumbers) {
		String serialNo = null;
		try {
			
			for(int i=0;i < serialNumbers.length; i++){
				serialNo = serialNumbers[i];
				Long serialNUmber = Long.parseLong(serialNo);
				Accessories accessories = getAccessories(serialNUmber);
				sessionFactory.getCurrentSession().delete(accessories);
			}
			retMessage = "Accessories removed,";
		} catch (Exception e) {
			e.getMessage();
		}
		return retMessage;
	}

	@Override
	public Accessories getAccessories(Long recordID) {

		return (Accessories) sessionFactory.getCurrentSession().get(
				Accessories.class, recordID);
	}

	@Override
	public List<String> getAccessoriesList(String deviceSerialNumber) {
		List<Accessories> getAccessories = getAccessoriesByDeviceSerial(deviceSerialNumber);
		List<String> current = new ArrayList<String>();
		current.add("Additional Paper Trays");current.add("Bridge Unit");current.add("Credenza");current.add("Fax Unit");current.add("Finisher");current.add("LCT"); current.add("One Bin Tray");
		try{
			for(Accessories access: getAccessories){
				for(int i=0;i< current.size();i++){
					if(access.getAccessotyType().equals(current.get(i))){
						current.remove(current.get(i));
					}
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		return current;
	}

	@Override
	public JRDataSource getAccessoriesByDeviceSerialDataSource(
			String serialNumber) {
		JRDataSource ds = null;
		Device device = null;
		try{
			
			List<DeviceReportBean> result = new ArrayList<DeviceReportBean>();
			List<Accessories> tempAccessory = getAccessoriesByDeviceSerial(serialNumber);
			
			if(tempAccessory.size()==0|| tempAccessory.isEmpty()== true){
				
				DeviceReportBean deviceBean = new DeviceReportBean();
				
				
				device = deviceDaoInt.getDeviceBySerialNumbuer(serialNumber);
				
				deviceBean.setCustomerName(device.getCustomerDevice().getCustomerName());
				deviceBean.setSerialNumber(device.getSerialNumber());
				deviceBean.setModelNumber(device.getModelNumber());
				deviceBean.setModelBrand(device.getModelBrand());
				
				deviceBean.setStartDate(device.getStartDate());
				deviceBean.setInstallationDate(device.getInstallationDate());
				deviceBean.setEndDate(device.getEndDate());
				
				deviceBean.setMonoReading(device.getMonoReading());
				deviceBean.setColourReading(device.getColourReading());
				deviceBean.setMonoCopyCost(device.getMonoCopyCost());
				deviceBean.setColourCopyCost(device.getColourCopyCost());
								
				deviceBean.setAddress(device.getStreetNumber()+" "+device.getStreetName()+" "+"\n"+ device.getCity_town()+", "+"\n"+device.getProvince()+" "+"\n"+device.getAreaCode());
				
				deviceBean.setContactPersonEmail(device.getContactPerson().getEmail());
				deviceBean.setContactPersonFirstAndLastName(device.getContactPerson().getFirstName()+" " +device.getContactPerson().getLastName());
				deviceBean.setContactPersonCellphone(device.getContactPerson().getCellphone());
				deviceBean.setContactPersonTellphone(device.getContactPerson().getTelephone());
				
				deviceBean.setSerial("");
				deviceBean.setAccessoryType("");		
				
				result.add(deviceBean);
				ds = new JRBeanCollectionDataSource(result);
			}else{
				
				
				device = deviceDaoInt.getDeviceBySerialNumbuer(serialNumber);
                    for(Accessories acc:tempAccessory){
					
					DeviceReportBean deviceBean = new DeviceReportBean();
					
					deviceBean.setCustomerName(acc.getDevice().getCustomerDevice().getCustomerName());
					deviceBean.setSerialNumber(acc.getDevice().getSerialNumber());
					deviceBean.setModelNumber(acc.getDevice().getModelNumber());
					deviceBean.setModelBrand(acc.getDevice().getModelBrand());
					
					deviceBean.setStartDate(acc.getDevice().getStartDate());
					deviceBean.setInstallationDate(acc.getDevice().getInstallationDate());
					deviceBean.setEndDate(acc.getDevice().getEndDate());
					
					deviceBean.setMonoReading(acc.getDevice().getMonoReading());
					deviceBean.setColourReading(acc.getDevice().getColourReading());
					deviceBean.setMonoCopyCost(acc.getDevice().getMonoCopyCost());
					deviceBean.setColourCopyCost(acc.getDevice().getColourCopyCost());
					
					deviceBean.setAddress(acc.getDevice().getStreetNumber()+" "+acc.getDevice().getStreetName()+" "+"\n"+acc.getDevice().getCity_town()+", "+"\n"+acc.getDevice().getProvince()+" "+"\n"+acc.getDevice().getAreaCode());
					
					deviceBean.setContactPersonFirstAndLastName(acc.getDevice().getContactPerson().getFirstName()+" " +acc.getDevice().getContactPerson().getLastName());
					deviceBean.setContactPersonEmail(acc.getDevice().getContactPerson().getEmail());
					deviceBean.setContactPersonCellphone(acc.getDevice().getContactPerson().getCellphone());
					deviceBean.setContactPersonTellphone(acc.getDevice().getContactPerson().getTelephone());
					
					deviceBean.setSerial(acc.getSerial());
					deviceBean.setAccessoryType(acc.getAccessotyType());
								
					
					result.add(deviceBean);
					ds = new JRBeanCollectionDataSource(result);
                    }
			}
		}catch(Exception e){
			e.getMessage();
		}
		return ds;
	}
}
