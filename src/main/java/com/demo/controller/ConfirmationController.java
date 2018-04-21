package com.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.demo.model.Employee;


@Controller
public class ConfirmationController {
	
	@Autowired
	private HttpSession session;
	
	Employee userName = null;
	ModelAndView model = null;
	String retMessage = null;

	//confirmationMessages 
		@RequestMapping(value={"confirm","confirmation", "confirmations"})
		public ModelAndView confirmationOrderMessages(){
			
			model = new ModelAndView();
			userName = (Employee) session.getAttribute("loggedInUser");
			if(userName !=null){
			     
			    model.addObject("retMessage", retMessage);
			   
			    if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {				
			    	
			    	model.setViewName("confirmations");
			    }
			    
			    else if (userName.getRole().equalsIgnoreCase("Technician")){
			    	
			    	model.setViewName("confirmation");
			   }
			    
			    else if (userName.getRole().equalsIgnoreCase("User")){
			    	
			    	model.setViewName("confirm");
			  }			
			}
			else{
				model.setViewName("login");
			}
			
			return model;
		}
}
