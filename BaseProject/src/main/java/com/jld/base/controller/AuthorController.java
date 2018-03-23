package com.jld.base.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/author")
public class AuthorController {
	
	private static final Logger logger = Logger.getLogger(AuthorController.class);
	
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public ModelAndView about(ModelMap model) {
		
		logger.debug("Screen /author/about");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("author/about");
		
		return mav;

	}
	
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public ModelAndView contact(ModelMap model) {
		
		logger.debug("Screen /author/contact");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("author/contact");
		
		return mav;
	}
}
