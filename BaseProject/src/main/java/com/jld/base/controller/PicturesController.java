package com.jld.base.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jld.base.form.PictureAddForm;
import com.jld.base.model.Picture;
import com.jld.base.pagination.PaginationConfiguration;
import com.jld.base.pagination.PaginationResultInfo;
import com.jld.base.pojo.FileDescription;
import com.jld.base.service.PictureService;

@Controller
@RequestMapping("/pictures")
public class PicturesController {
	
	@Value("${fileuploading.destination}")
	private String UPLOAD_REPOSITORY_PATH;
	
	@Value("${fileuploading.temporal}")
	private String UPLOAD_TEMPORAL_PATH;
	
	private static final Logger logger = Logger.getLogger(PicturesController.class);
	
	@Autowired
	private PictureService pictureService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView display(ModelMap model,
			@ModelAttribute("pictureAddForm") PictureAddForm form) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("picture/portfolio");
		
		// Validate form (check field 'page'):
		if(form.getPage() < 1) {
			form.setPage(1);
		}
		
		//form.setPageSize(PaginationConfiguration.DEFAULT_PAGE_SIZE);
		form.setFrom((form.getPage() - 1) * form.getPageSize());
		form.setCurrentPage(form.getPage());
		
		mav.addObject("pictureAddForm", form);
		
		// List of messages:
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		com.jld.base.core.security.User loguedInUser = (com.jld.base.core.security.User) authentication.getPrincipal();
        
		PaginationResultInfo<Picture> resultInfo = pictureService.getUserPictures(loguedInUser.getUsername());
		mav.addObject("resultAndMetainfo", resultInfo);
		
		return mav;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView upload(@ModelAttribute("pictureAddForm") PictureAddForm form) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/pictures");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		com.jld.base.core.security.User loguedInUser = (com.jld.base.core.security.User) authentication.getPrincipal();
        
		try {
			List<FileDescription> uploadedFiles = pictureService.addPicturesToUser(UPLOAD_REPOSITORY_PATH, loguedInUser.getUsername(), form.getFiles());
	        mav.addObject("files", uploadedFiles);
		} catch (Exception e) {
            logger.error(e.getMessage());
        } 
        
        
        return mav;
    }
	
	@RequestMapping(value = "/{pictureid}.{pictureextension}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] fetchPicture(@PathVariable(value = "pictureid") String pictureid,
			@PathVariable(value="pictureextension") String pictureextension) throws IOException {
		
		try {			
	        File serverFile = pictureService.fetchPictureFromServer(UPLOAD_REPOSITORY_PATH, pictureid, pictureextension);
	        
	        return Files.readAllBytes(serverFile.toPath());
		}
		catch(Exception e) {
			logger.error("Cannot find image with key=" + pictureid + " - " + e.getMessage());
			return null;
		}
	}

}
