package com.demo.reports;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class HistoryDao {
	public JRDataSource getHistory(){
		JRDataSource ds = null;
		try{
			Date d = new Date();

			List<History> items = new ArrayList<History>();
			
			History his = new History();
			his.setCustomer("Fenya Tlala");
			his.setDate(d);
			his.setId(1L);
			his.setTotal(1234.03);
			
			History his1 = new History();
			his1.setCustomer("Fenya Tlala Consulting");
			his1.setDate(d);
			his1.setId(2L);
			his1.setTotal(12234.03);
			
			History his2 = new History();
			his2.setCustomer("Madibeng Municipality From Vandabeiltjpark");
			his2.setDate(d);
			his2.setId(3L);
			his2.setTotal(234.03);
			
			History his3 = new History();
			his3.setCustomer("Madibeng ");
			his3.setDate(d);
			his3.setId(4L);
			his3.setTotal(34.03);
			
			
			items.add(his);
			items.add(his1);
			items.add(his2);
			items.add(his3);
			
			 ds = new JRBeanCollectionDataSource(items);
			
		}catch(Exception e){
			e.getMessage();
		}
		return ds;
	}

}
