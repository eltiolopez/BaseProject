package com.jld.base.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jld.base.exception.EntityNotFoundException;
import com.jld.base.form.UserCreateForm;
import com.jld.base.form.UserProfileForm;
import com.jld.base.form.UserSearchForm;
import com.jld.base.model.User;
import com.jld.base.model.Usergroup;
import com.jld.base.pagination.PaginationResultInfo;
import com.jld.base.pojo.UserDescription;
import com.jld.base.service.UserService;
import com.jld.base.validator.UserCreateFormValidator;
import com.jld.base.validator.UserUpdateFormValidator;

@Controller
@RequestMapping(value={"/users", "/profile"})
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
	public ModelAndView create(ModelMap model) {
		
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
	}
	
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
	public ModelAndView doDelete(@RequestParam("userid") Integer idUser,
			@ModelAttribute("userSearchForm") UserSearchForm form,
			final RedirectAttributes redirectAttributes) {
		
		logger.debug("Screen /users/delete");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/users/search");
		
		if(idUser != null) {
			try {
				userService.deleteUserById(idUser);
				redirectAttributes.addFlashAttribute("deleteOk", true);
				
				logger.debug("User deleted correctly");
			} catch(EntityNotFoundException enfe) {
				redirectAttributes.addFlashAttribute("deleteError", true);
				
				logger.error(enfe);
			} catch(Exception e) {
				redirectAttributes.addFlashAttribute("deleteError", true);
				
				logger.error(e);
			}
			
			// Redirect to confirmation page:
			//mav.setViewName("users/search");
		}
		else {
			//error (set parameter flash)
			redirectAttributes.addFlashAttribute("deleteError", true);
			//mav.setViewName("users/search");
		}
		
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
	
	@RequestMapping(value="/{userName}", method=RequestMethod.GET)
	public ModelAndView getUserProfile(ModelAndView mav, 
			@PathVariable("userName") String userName, 
			@ModelAttribute("userProfileForm") UserProfileForm form) {

		mav.setViewName("users/profile");
		
		// No root users are not allowed to see other user's profile:
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User loguedInUser = (org.springframework.security.core.userdetails.User)authentication.getPrincipal();
		Collection<GrantedAuthority> userRoles = loguedInUser.getAuthorities();
		
		if(!userRoles.contains(new SimpleGrantedAuthority("ROLE_ADMIN")) && !loguedInUser.getUsername().equalsIgnoreCase(userName)) {
			
			//mav.addObject("error", "not_allowed");
			throw new AccessDeniedException("You don't have access to this resource.");
		}
		else {
			
			UserDescription userDescription = userService.getUserInfoByUsername(userName);
			form.setUserid(userDescription.getUserid());
			form.setUsername(userDescription.getUsername());
			form.setEmail(userDescription.getEmail());
			form.setName(userDescription.getName());
			form.setSurname1(userDescription.getSurname1());
			form.setSurname2(userDescription.getSurname2());
			form.setImageUrl(userDescription.getProfilePicture());
		}
		
		mav.addObject("userProfileForm", form);
		
		return mav;
	}
	
	@RequestMapping(value="/{userName}", method=RequestMethod.POST)
	public ModelAndView setUserProfile(@PathVariable("userName") String userName) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect: /users/profile/" + userName);
		
		
		return mav;
	}
	
	@RequestMapping(value="/settings", method=RequestMethod.GET)
	public ModelAndView getUserSettings() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("users/settings");
		
		return mav;
	}
	
	@RequestMapping(value="/statistics", method=RequestMethod.GET)
	public ModelAndView getUserStatistics() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("users/statistics");
		
		return mav;
	}
}
