package com.jld.base.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jld.base.core.utils.Constants;
import com.jld.base.dao.UserDao;
import com.jld.base.dao.UserpreferenceDao;
import com.jld.base.form.UserProfileForm;
import com.jld.base.form.UserSettingsForm;
import com.jld.base.model.Picture;
import com.jld.base.model.User;
import com.jld.base.model.Userpreference;
import com.jld.base.model.vo.ModelAttribute;
import com.jld.base.model.vo.ModelFilter;
import com.jld.base.pojo.UserDescription;
import com.jld.base.pojo.UserSettings;

@Service
@Transactional
public class DashboardService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserpreferenceDao userpreferenceDao;

	public UserDescription getUserInfoByUsername(String userName) {

		UserDescription userDescription = new UserDescription();
		
		User user = userDao.findUserByField("username", userName);
		
		if(user != null) {			
			userDescription.setEmail(user.getEmail());
			userDescription.setName(user.getName());
			userDescription.setSurname1(user.getSurname1());
			userDescription.setSurname2(user.getSurname2());
			userDescription.setGroup(user.getUsergroup().getIdusergroup());
			userDescription.setGroupStr(user.getUsergroup().getGroupname());
			userDescription.setUserid(user.getIduser());
			userDescription.setUsername(user.getUsername());

			ModelFilter modelFilter = new ModelFilter();
			modelFilter.getAttributes().add(new ModelAttribute("user.username", userName, Constants.MODEL_OPERATION_EQUAL));
			Userpreference userPreferences = userpreferenceDao.findUserpreference(userName);
			if(userPreferences.getPicture() != null && StringUtils.isNotEmpty(userPreferences.getPicture().getFilekey())) {
				userDescription.setProfilePicture(userPreferences.getPicture().getFilekey() + "." + userPreferences.getPicture().getFileextension());
			}
			/*List<Picture> listProfilePicture = pictureDao.findPicture(modelFilter);
			if(listProfilePicture != null && listProfilePicture.size() > 0) {
				userDescription.setProfilePicture(listProfilePicture.get(0).getFilekey() + "." + listProfilePicture.get(0).getFileextension());
			}
			else {
				userDescription.setProfilePicture("bp" + File.separator + "anonymous-user.png");
			}*/
		}
		
		return userDescription;
	}
	
	@Transactional
	public void setUserProfile(UserProfileForm form) {
		
		// Update user information:
		User user = userDao.findUserByField("username", form.getUsername());
		user.setEmail(form.getEmail());
		user.setName(form.getName());
		user.setSurname1(form.getSurname1());
		user.setSurname2(form.getSurname2());
		
		// Update user profile picture:
		if(!StringUtils.isEmpty(form.getImageUrl())) {		
			String[] tokens = form.getImageUrl().split("\\.(?=[^\\.]+$)");
			Picture newProfilePicture = null;
			List<Picture> userPictures = user.getPictures();
			if(userPictures.size() > 0) {
				for(Picture pic: userPictures) {
					if(pic.getFilekey().equals(tokens[0]) && pic.getFileextension().equals(tokens[1])) {
						newProfilePicture = pic;
						break;
					}
				}
			}
			if(newProfilePicture != null) {
				user.getUserpreferences().get(0).setPicture(newProfilePicture);
			}
			else {
				// TODO: Throw exception (picture not found for the user)
				
			}
		}
		
		// Update user:
		userDao.update(user);
	}

	public UserSettings getUserSettings(String userName) {
		
		UserSettings userSettings = new UserSettings();
		
		User user = userDao.findUserByField("username", userName);
		
		if(user != null) {
			userSettings.setUsername(userName);
			userSettings.setNumResultsInPage(user.getUserpreferences().get(0).getNumresultsinpage());
			//userSettings.setOrderByCode(user.getUserpreferences().get(0).getOrderby());
			userSettings.setOrderByCode("NAME");
		}

		return userSettings;
	}
	
	@Transactional
	public void setUserSettings(UserSettingsForm form) {
		
		// Update user information:
		User user = userDao.findUserByField("username", form.getUsername());
		user.getUserpreferences().get(0).setNumresultsinpage(form.getNumResultsInPage());
		//user.getUserpreferences().get(0).setOrderby(form.getOrderBy());

		// Update user:
		userDao.update(user);
	}
}
