package com.demo.reports;


import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.demo.dao.OrderDetailsDaoInt;


@Controller
public class OrderReport {
	
protected static Logger logger = Logger.getLogger("controller");

   @Autowired
   private OrderDetailsDaoInt detailsDaoInt;
   
   
    @RequestMapping(value = "/orderDownloadPDF", method = RequestMethod.GET)
    public ModelAndView doSalesReportPDF(@RequestParam("recordID") Long recordID) 
		 {
    	logger.debug("Received request to download PDF report");
		ModelAndView modelAndView = null;
		// Retrieve our data from a custom data provider
		// Our data comes from a DAO layer
		//SalesDAO dataprovider = new SalesDAO();
		JRDataSource orderDetails = detailsDaoInt.getOrderDetailsDataSource(recordID);
		
		// Assign the datasource to an instance of JRDataSource
		// JRDataSource is the datasource that Jasper understands
		// This is basically a wrapper to Java's collection classes
		//JRDataSource datasource  = orderDetails.getDataSource();
		
		// In order to use Spring's built-in Jasper support, 
		// We are required to pass our datasource as a map parameter
		// parameterMap is the Model of our application
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("deliveryNoteDatanowsource", orderDetails);
		
		// pdfReport is the View of our application
		// This is declared inside the /WEB-INF/deliveryNote-views.xml
		modelAndView = new ModelAndView("deliveryNotepdfReport", parameterMap);
		
		// Return the View and the Model combined
		return modelAndView;
	}
    /**
     * Retrieves the download file in XLS format
     * 
     * @return
     */
    /*@RequestMapping(value = "/download/xls", method = RequestMethod.GET)
    public ModelAndView doSalesReportXLS(ModelAndView modelAndView) 
		 {
		logger.debug("Received request to download Excel report");
		
		// Retrieve our data from a custom data provider
		// Our data comes from a DAO layer
		SalesDAO dataprovider = new SalesDAO();
		
		// Assign the datasource to an instance of JRDataSource
		// JRDataSource is the datasource that Jasper understands
		// This is basically a wrapper to Java's collection classes
		JRDataSource datasource  = dataprovider.getDataSource();
		
		// In order to use Spring's built-in Jasper support, 
		// We are required to pass our datasource as a map parameter
		// parameterMap is the Model of our application
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("datasource", datasource);
		
		// xlsReport is the View of our application
		// This is declared inside the /WEB-INF/jasper-views.xml
		modelAndView = new ModelAndView("xlsReport", parameterMap);
		
		// Return the View and the Model combined
		return modelAndView;
	}
    
*/

}
