package com.jld.base.controller;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jld.base.form.PictureAddForm;
import com.jld.base.form.UserProfileForm;
import com.jld.base.model.Picture;
import com.jld.base.pagination.PaginationResultInfo;
import com.jld.base.pojo.UserDescription;
import com.jld.base.service.DashboardService;
import com.jld.base.service.PictureService;

@Controller
@RequestMapping(value={"/{userName}"})
public class DashboardController {
	
	@Value("${fileuploading.destination}")
	private String UPLOAD_REPOSITORY_PATH;
	
	private static final Logger logger = Logger.getLogger(DashboardController.class);
	
	@Autowired
	private DashboardService dashboardService;
	
	@Autowired
	private PictureService pictureService;
	
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public ModelAndView getUserProfile(ModelAndView mav, 
			@PathVariable("userName") String userName, 
			@RequestParam(value="profilePicture", required=false) String profilePicture, 
			@ModelAttribute("userProfileForm") UserProfileForm form) {

		mav.setViewName("dashboard/profile");
		
		// No root users are not allowed to see other user's profile:
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User loguedInUser = (org.springframework.security.core.userdetails.User)authentication.getPrincipal();
		Collection<GrantedAuthority> userRoles = loguedInUser.getAuthorities();
		
		if(!userRoles.contains(new SimpleGrantedAuthority("ROLE_ADMIN")) && !loguedInUser.getUsername().equalsIgnoreCase(userName)) {
			
			//mav.addObject("error", "not_allowed");
			throw new AccessDeniedException("You don't have access to this resource.");
		}
		else {
			
			UserDescription userDescription = dashboardService.getUserInfoByUsername(userName);
			form.setUserid(userDescription.getUserid());
			form.setUsername(userDescription.getUsername());
			form.setEmail(userDescription.getEmail());
			form.setName(userDescription.getName());
			form.setSurname1(userDescription.getSurname1());
			form.setSurname2(userDescription.getSurname2());
			if(!StringUtils.isEmpty(profilePicture)){
				if(isValidPicture(profilePicture)) {
					form.setImageUrl(profilePicture);
				}
				else {					
					logger.debug("Invalid picture received: " + profilePicture);
					form.setImageUrl(userDescription.getProfilePicture());
				}
			}
			else {
				form.setImageUrl(userDescription.getProfilePicture());
			}
		}
		
		mav.addObject("userProfileForm", form);
		
		return mav;
	}
	
	private boolean isValidPicture(String picture) {
		if(picture.contains(".")) {
			String[] tokens = picture.split("\\.(?=[^\\.]+$)");
			if(tokens[0] != null && tokens[1] != null) {
				try {
					pictureService.fetchPictureFromServer(UPLOAD_REPOSITORY_PATH, tokens[0], tokens[1]);
					return true;
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
        
		return false;
	}
	
	@RequestMapping(value="/profile", method=RequestMethod.POST)
	public ModelAndView setUserProfile(@PathVariable("userName") String userName) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect: /dashboard/profile");
		
		
		return mav;
	}
	
	@RequestMapping(value="/images", method=RequestMethod.GET)
	public ModelAndView getUserImages(ModelAndView mav, 
			@PathVariable("userName") String userName,
			@ModelAttribute("pictureAddForm") PictureAddForm form) {

		mav.setViewName("picture/portfolio");
		
		// Validate form (check field 'page'):
		if(form.getPage() < 1) {
			form.setPage(1);
		}
		
		//form.setPageSize(PaginationConfiguration.DEFAULT_PAGE_SIZE);
		form.setFrom((form.getPage() - 1) * form.getPageSize());
		form.setCurrentPage(form.getPage());
		
		mav.addObject("pictureAddForm", form);
		mav.addObject("optionSelectOnly", true);
		
		// List of messages:
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		com.jld.base.core.security.User loguedInUser = (com.jld.base.core.security.User) authentication.getPrincipal();
        
		PaginationResultInfo<Picture> resultInfo = pictureService.getUserPictures(loguedInUser.getUsername());
		mav.addObject("resultAndMetainfo", resultInfo);
		
		return mav;
	}
	
	@RequestMapping(value="/settings", method=RequestMethod.GET)
	public ModelAndView getUserSettings() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("dashboard/settings");
		
		return mav;
	}
	
	@RequestMapping(value="/statistics", method=RequestMethod.GET)
	public ModelAndView getUserStatistics() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("dashboard/statistics");
		
		return mav;
	}
}
