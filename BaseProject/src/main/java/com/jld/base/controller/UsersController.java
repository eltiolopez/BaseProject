package com.jld.base.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jld.base.exception.EntityNotFoundException;
import com.jld.base.form.UserCreateForm;
import com.jld.base.form.UserSearchForm;
import com.jld.base.model.User;
import com.jld.base.model.Usergroup;
import com.jld.base.pagination.PaginationResultInfo;
import com.jld.base.service.UserService;
import com.jld.base.validator.UserCreateFormValidator;
import com.jld.base.validator.UserUpdateFormValidator;

@Controller
@RequestMapping(value={"/users"})
public class UsersController {
	
	@Value("${fileuploading.destination}")
	private String UPLOAD_REPOSITORY_PATH;
	
	private static final Logger logger = Logger.getLogger(UsersController.class);
	
	@Autowired
	private UserService userService;
	
	/* The following code is used for customized Form validation. */
	@Autowired
	private UserCreateFormValidator createValidator;
	
	@Autowired
	private UserUpdateFormValidator updateValidator;
	
	@InitBinder("userCreateForm")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(createValidator);
    }
	
	@InitBinder("userUpdateForm")
    private void initBinderValidator(WebDataBinder binder) {
        binder.setValidator(updateValidator);
    }
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		
		if(!model.containsAttribute("userCreateForm")) {
			model.addAttribute("userCreateForm", new UserCreateForm());
		}
		
		List<Usergroup> listUsergroups = userService.getAllUsergroups();		
		model.addAttribute("groupList", listUsergroups);		
		
		return "users/create";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String doAdd(
			@Valid @ModelAttribute("userCreateForm") UserCreateForm form,
			BindingResult result,
			RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			logger.debug("There are some validation errors");
			
			redirectAttributes.addFlashAttribute("hasValidationErrors", true);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userCreateForm", result);
		    redirectAttributes.addFlashAttribute("userCreateForm", form);
			return "redirect:/users/create";
		}
		
		// Try to save the new user:
		try {
			userService.addNewUser(UPLOAD_REPOSITORY_PATH, form);
			
			redirectAttributes.addFlashAttribute("createOk", true);
			
			logger.debug("User created correctly");
		} catch(Exception e) {
			logger.error(e);
			
			redirectAttributes.addFlashAttribute("createError", true);
		}
		
		redirectAttributes.addFlashAttribute("createOk", true);
		
		return "redirect:/users/search";
	}
	
	/*@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("users/create");
		
		UserCreateForm form = new UserCreateForm();		
		mav.addObject("userCreateForm", form);
		
		List<Usergroup> listUsergroups = userService.getAllUsergroups();		
		mav.addObject("groupList", listUsergroups);
		
		return mav;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView doAdd(
			@Valid @ModelAttribute("userCreateForm") UserCreateForm form,
			BindingResult result,
			ModelMap model,
			final RedirectAttributes redirectAttributes) {
		
		ModelAndView mav = new ModelAndView();
		
		if(result.hasErrors()) {
			logger.debug("There are some validation errors");
			
			List<Usergroup> listUsergroups = userService.getAllUsergroups();		
			mav.addObject("groupList", listUsergroups);
			
			mav.setViewName("users/create");
			mav.addObject("hasValidationErrors", true);			
			mav.addObject("userCreateForm", form);
			
			return mav;
		}
		
		// Try to save the new user:
		try {
			userService.addNewUser(UPLOAD_REPOSITORY_PATH, form);
			
			redirectAttributes.addFlashAttribute("createOk", true);
			
			logger.debug("User created correctly");
		} catch(Exception e) {
			logger.error(e);
			
			redirectAttributes.addFlashAttribute("createError", true);
		}
		
		mav.setViewName("redirect:/users/search");
		
		return mav;
	}*/
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(ModelMap model,
			@ModelAttribute("userSearchForm") UserSearchForm form) {
		
		logger.debug("Screen /users/search");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("users/search");
		
		// Validate form (check field 'page'):
		if(form.getPage() < 1) {
			form.setPage(1);
		}
		
		//form.setPageSize(PaginationConfiguration.DEFAULT_PAGE_SIZE);
		form.setFrom((form.getPage() - 1) * form.getPageSize());
		form.setCurrentPage(form.getPage());
		
		mav.addObject("userSearchForm", form);
		
		PaginationResultInfo<User> resultInfo = userService.searchUsersPaginated(form);
		mav.addObject("resultAndMetainfo", resultInfo);
		
		List<Usergroup> listUsergroups = userService.getAllUsergroups();		
		mav.addObject("groupList", listUsergroups);
		
		return mav;
	}
	
	@RequestMapping(value = "/details", params = {"userid"}, method = RequestMethod.GET)
	public ModelAndView details(@RequestParam("userid") Integer idUser) {
		
		logger.debug("Screen /users/details");
		
		ModelAndView mav = new ModelAndView("users/details");
		
		List<Usergroup> listUsergroups = userService.getAllUsergroups();		
		mav.addObject("groupList", listUsergroups);
		
		if(idUser != null) {		
			UserCreateForm userCreateForm = userService.getUserInfoById(idUser);
			mav.addObject("userCreateForm", userCreateForm);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.POST)
	public ModelAndView doUpdate(
			@Valid @ModelAttribute("userUpdateForm") UserCreateForm form,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {
		
		ModelAndView mav = new ModelAndView();
		
		if(result.hasErrors()) {
			logger.debug("There are some validation errors");
			
			List<Usergroup> listUsergroups = userService.getAllUsergroups();		
			mav.addObject("groupList", listUsergroups);
			
			mav.setViewName("users/details");
			mav.addObject("userid", form.getUserid());
			mav.addObject("hasValidationErrors", true);
			mav.addObject("userCreateForm", form);
			
			return mav;
		}
		
		try {
			userService.updateUser(form);
			
			redirectAttributes.addFlashAttribute("updateOk", true);
			
			logger.debug("User updated correctly");
		} catch(Exception e) {
			logger.error(e);
			
			redirectAttributes.addFlashAttribute("updateError", true);
		}
		
		mav.setViewName("redirect:/users/search");
		
		return mav;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String doDelete(@RequestParam("userid") Integer idUser,
			final RedirectAttributes redirectAttributes) {
		
		logger.debug("Screen /users/delete");

		if(idUser != null) {
			try {
				userService.deleteUserById(UPLOAD_REPOSITORY_PATH, idUser);
				redirectAttributes.addFlashAttribute("deleteOk", true);
				
				logger.debug("User deleted correctly");
			} catch(EntityNotFoundException enfe) {
				logger.error("Not possible to remove the user: " + enfe);
				
				redirectAttributes.addFlashAttribute("deleteError", true);
			} catch(IOException ioe) {
				logger.error("Not possible to remove the user directory: " + ioe);
				
				redirectAttributes.addFlashAttribute("deleteError", true);
			} catch(Exception e) {				
				logger.error("Not possible to remove the user" + e);
				
				redirectAttributes.addFlashAttribute("deleteError", true);
			}
		}
		else {
			//error (set parameter flash)
			redirectAttributes.addFlashAttribute("deleteError", true);
		}
		
		return "redirect:/users/search";
	}
}
