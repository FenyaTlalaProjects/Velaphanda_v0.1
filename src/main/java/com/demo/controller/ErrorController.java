package com.demo.controller;

import java.io.FileNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ErrorController {
	
	@RequestMapping(value="error",method=RequestMethod.GET)
	public ModelAndView logTicket(){
		
		ModelAndView model = new ModelAndView();
		
		return model;
	}
	@RequestMapping(value="errorParts",method=RequestMethod.GET)
	public ModelAndView errorParts(){
		
		ModelAndView model = new ModelAndView();
		
		return model;
	}
	@ExceptionHandler({FileNotFoundException.class})
    public ModelAndView dataIntegrity(Exception ex) {
        ModelAndView model = new ModelAndView("405");
 
        model.addObject("exception", ex.getMessage()+ ". Please create a folder 'VelaphandaReports' on C drive to save the report");
        return model;
        
    }

}
