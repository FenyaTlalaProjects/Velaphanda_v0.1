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
			his.setModelNumber("Fenya Tlala");
			his.setPartNumber("PT0000");
			his.setItemDescription("Black Guys");
			his.setQuantity("1234");
			
			History his1 = new History();
			his1.setModelNumber("Fenya Tlala Consulting");
			his1.setPartNumber("Ptddk9d");
			his1.setItemDescription("Yellow Nation");
			his1.setQuantity("3");
			
			History his2 = new History();
			his2.setModelNumber("Madibeng Municipality From Vandabeiltjpark");
			his2.setPartNumber("PZKIFJNFNF");
			his2.setItemDescription("Magenta Suation");
			his2.setQuantity("234");
			
			History his3 = new History();
			his3.setModelNumber("Madibeng");
			his3.setPartNumber("Plsks");
			his3.setItemDescription("4L");
			his3.setQuantity("34");
			
			
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
