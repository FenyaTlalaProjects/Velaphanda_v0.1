package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SuccessError {
	
	@RequestMapping(value="successerror",method=RequestMethod.GET)
	public ModelAndView loadSuccessError() {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("successerror");
		return model;
		
	}

}
