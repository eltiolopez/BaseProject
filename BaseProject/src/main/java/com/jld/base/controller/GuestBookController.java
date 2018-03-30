package com.jld.base.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jld.base.form.GuestBookCreateForm;
import com.jld.base.form.GuestBookSearchForm;
import com.jld.base.model.Guestbook;
import com.jld.base.pagination.PaginationResultInfo;
import com.jld.base.service.GuestBookService;
import com.jld.base.validator.GuestBookCreateFormValidator;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	
	private static final Logger logger = Logger.getLogger(GuestBookController.class);
	
	@Autowired
	private GuestBookService guestBookService;
	
	/* The following code is used for customized Form validation. */
	@Autowired
	private GuestBookCreateFormValidator validator;
	
	@InitBinder("guestbookCreateForm")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(ModelMap model) {
		
		logger.debug("Screen /guestbook/create");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("guestbook/create");
		
		GuestBookCreateForm form = new GuestBookCreateForm();		
		mav.addObject("guestbookCreateForm", form);
		
		return mav;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView doAdd(
			@Valid @ModelAttribute("guestbookCreateForm") GuestBookCreateForm form,
			BindingResult result,
			ModelMap model) {
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("guestbookCreateForm", form);
		
		if(result.hasErrors()) {
			logger.debug("There are some validation errors");
			
			mav.setViewName("guestbook/create");
			mav.addObject("hasValidationErrors", true);
			return mav;
		}

		mav.setViewName("redirect:/guestbook/search");
		
		// Try to save the new message:
		try {
			guestBookService.addNewMessage(form);
			
			logger.debug("Message created correctly");
		} catch(Exception e) {
			logger.error(e);
		}		
		
		return mav;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)//, params="page")
	public ModelAndView search(ModelMap model,
			@ModelAttribute("guestBookSearchForm") GuestBookSearchForm form) {
		
		logger.debug("Screen /guestbook/search");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("guestbook/search");
		
		// Validate form (check field 'page'):
		if(form.getPage() == 0) {
			form.setPage(1);
		}
		
		//form.setPageSize(PaginationConfiguration.DEFAULT_PAGE_SIZE);
		form.setFrom((form.getPage() - 1) * form.getPageSize());
		form.setCurrentPage(form.getPage());
		
		mav.addObject("guestBookSearchForm", form);
		
		// List of messages:
		PaginationResultInfo<Guestbook> resultInfo = guestBookService.getMessages(form);
		mav.addObject("resultAndMetainfo", resultInfo);
		
		return mav;
	}
}
