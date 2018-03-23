package com.jld.base.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonController {
	
	private static final Logger logger = Logger.getLogger(CommonController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView root() {
		
		logger.debug("Screen /");
		
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		
		logger.debug("Screen /index");
		
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}

	@RequestMapping(value = "/loginError", method = RequestMethod.GET)
	public String loginError(ModelMap model) {
		
		logger.debug("Screen /loginError");
		
		model.addAttribute("error", "true");
		return "index";
	}
	
	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {
		
		logger.debug("Screen /accessDenied");

		ModelAndView model = new ModelAndView("accessDenied");
		return model;

	}

	@RequestMapping(value="/spring", method=RequestMethod.GET)
	public String springUrl() {
		
		logger.debug("Screen /spring");
		
		return "spring";
	}
	
	@RequestMapping(value="/hibernate", method=RequestMethod.GET)
	public String hibernateUrl() {
		
		logger.debug("Screen /hibernate");
		
		return "hibernate";
	}
	
	@RequestMapping(value="/mysql", method=RequestMethod.GET)
	public String mysqlUrl() {
		
		logger.debug("Screen /mysql");
		
		return "mysql";
	}
	
	@RequestMapping(value="/frontend", method=RequestMethod.GET)
	public String frontEndUrl() {
		logger.debug("Screen /frontend");
		
		return "frontend";
	}
}
