package com.jld.base.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
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

import com.jld.base.form.PictureAddForm;
import com.jld.base.form.UserProfileForm;
import com.jld.base.form.UserSettingsForm;
import com.jld.base.model.Picture;
import com.jld.base.pagination.PaginationResultInfo;
import com.jld.base.pojo.UserDescription;
import com.jld.base.pojo.UserSettings;
import com.jld.base.service.DashboardService;
import com.jld.base.service.PictureService;
import com.jld.base.service.UserService;
import com.jld.base.validator.UserProfileFormValidator;
import com.jld.base.validator.UserSettingsFormValidator;

@Controller
@RequestMapping(value={"/{userName}"})
public class DashboardController {
	
	@Value("${fileuploading.destination}")
	private String UPLOAD_REPOSITORY_PATH;
	
	private static final Logger logger = Logger.getLogger(DashboardController.class);
	
	@Autowired
	private DashboardService dashboardService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PictureService pictureService;
	
	/* The following code is used for customized Form validation. */	
	@Autowired
	private UserProfileFormValidator userProfileValidator;
	
	@Autowired
	private UserSettingsFormValidator userSettinsFormValidator;
	
	@InitBinder("userProfileForm")
    private void initBinderProfile(WebDataBinder binder) {
        binder.setValidator(userProfileValidator);
    }
	
	@InitBinder("userSettingsForm")
    private void initBinderSettings(WebDataBinder binder) {
        binder.setValidator(userSettinsFormValidator);
    }
	
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public ModelAndView getUserProfile(ModelAndView mav, 
			@PathVariable("userName") String userName, 
			@RequestParam(value="profilePicture", required=false) String profilePicture, 
			@ModelAttribute("userProfileForm") UserProfileForm form) {

		mav.setViewName("dashboard/profile");

		// User profile:
		UserDescription userDescription = dashboardService.getUserInfoByUsername(userName);
		//form.setUserid(userDescription.getUserid());
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
	public String setUserProfile(
			@PathVariable("userName") String userName,
			@Valid @ModelAttribute("userProfileForm") UserProfileForm form,
			BindingResult result,
			RedirectAttributes redirectAttributes) {

		try {
			form.setUsername(userName);		// Just for security.
			dashboardService.setUserProfile(form);
			
			// Refresh user preferences:
			userService.refreshUserPreferences();
				
			redirectAttributes.addFlashAttribute("updateOk", true);
			
			logger.debug("User updated correctly");
			
			redirectAttributes.addFlashAttribute("updateOk", true);
		} catch(Exception e) {
			logger.error(e);
			
			redirectAttributes.addFlashAttribute("updateError", true);
		}
		
		return "redirect:/" + userName + "/profile";
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
		
		// List of pictures:        
		PaginationResultInfo<Picture> resultInfo = pictureService.getUserPictures(userName);
		mav.addObject("resultAndMetainfo", resultInfo);
		
		return mav;
	}
	
	@RequestMapping(value="/settings", method=RequestMethod.GET)
	public ModelAndView getUserSettings(ModelAndView mav,
			@PathVariable("userName") String userName,
			@ModelAttribute("userSettingsForm") UserSettingsForm form) {
		
		mav.setViewName("dashboard/settings");

		// User settings:
		UserSettings userSettings = dashboardService.getUserSettings(userName);
		form.setUsername(userSettings.getUsername());
		form.setNumResultsInPage(userSettings.getNumResultsInPage());
		form.setOrderBy(userSettings.getOrderByCode());
		
		mav.addObject("userSettingsForm", form);		
		
		return mav;
	}
	
	@RequestMapping(value="/settings", method=RequestMethod.POST)
	public String setUserSettings(ModelAndView mav,
			@PathVariable("userName") String userName,
			@ModelAttribute("userSettingsForm") UserSettingsForm form,
			//BindingResult result,
			RedirectAttributes redirectAttributes) {
		
		try {
			form.setUsername(userName);		// Just for security.
			dashboardService.setUserSettings(form);
			
			// Refresh user preferences:
			userService.refreshUserPreferences();
			
			redirectAttributes.addFlashAttribute("updateOk", true);
			
			logger.debug("User preferences updated correctly");
		} catch(Exception e) {
			logger.error(e);
			
			redirectAttributes.addFlashAttribute("updateError", true);
		}
		
		return "redirect:/" + userName + "/settings";
	}
	
	@RequestMapping(value="/statistics", method=RequestMethod.GET)
	public ModelAndView getUserStatistics() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("dashboard/statistics");
		
		return mav;
	}
}
