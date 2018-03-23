package com.jld.base.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jld.base.core.utils.Constants;
import com.jld.base.core.utils.RandomAlphaNumericString;
import com.jld.base.dao.PictureDao;
import com.jld.base.dao.UserDao;
import com.jld.base.exception.EntityNotFoundException;
import com.jld.base.model.Picture;
import com.jld.base.model.User;
import com.jld.base.model.vo.ModelAttribute;
import com.jld.base.model.vo.ModelFilter;
import com.jld.base.pagination.PaginationResultInfo;
import com.jld.base.pojo.FileDescription;

@Service
@Transactional
public class PictureService {
	
	@Autowired
	private PictureDao pictureDao;
	
	@Autowired
	private UserDao userDao;

	public PaginationResultInfo<Picture> getUserPictures(String userName) {
		ModelFilter modelFilter = new ModelFilter();
		modelFilter.getAttributes().add(new ModelAttribute("user.username", userName, Constants.MODEL_OPERATION_EQUAL));
		modelFilter.setOrderBy("creationdate", Constants.MODEL_ORDER_DES);
		
		PaginationResultInfo<Picture> resultInfo = new PaginationResultInfo<Picture>();
		resultInfo.setListaResultados(pictureDao.findList(modelFilter));
		resultInfo.setTotalResultados(pictureDao.findCount(modelFilter));
		
		return resultInfo;
	}

	public void addPictureToUser(String userName, List<FileDescription> uploadedFiles) {
		Picture picture = new Picture();
		
		User user = new User();
		user.setUsername(userName);
		
		for(FileDescription file: uploadedFiles) {
			picture.setUser(userDao.searchUsersByFieldsPaginated(user, 0, 0).get(0));
			picture.setFilekey(file.getKey());
			picture.setFilepath(file.getPath());
			picture.setFilename(file.getFileName());
			picture.setFileextension(file.getFileExtension());
			picture.setCreationdate(new Date());
			
			pictureDao.create(picture);
		}
	}

	public List<FileDescription> addPicturesToUser(String repositoryPath, String username, List<MultipartFile> files) throws Exception {
		List<FileDescription> uploadedFiles = new ArrayList<FileDescription>();
		
		if (!files.isEmpty()) {
	    	RandomAlphaNumericString rans = new RandomAlphaNumericString();
	    	
	        for(MultipartFile file: files) {
	        	String key = rans.generate(32);
	        	String fileName = FilenameUtils.getBaseName(file.getOriginalFilename());
	        	String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
	            String path = repositoryPath + File.separator + username;
	            File destinationFile = new File(path + File.separator + key + "." + fileExtension);
	            file.transferTo(destinationFile);
	            uploadedFiles.add(new FileDescription(username, key, fileName, fileExtension, ""));
	        }
	        
	        addPictureToUser(username, uploadedFiles);
        }
		
		return uploadedFiles;
	}

	public File fetchPictureFromServer(String repositoryPath, String pictureid, String pictureextension) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		com.jld.base.core.security.User loguedInUser = (com.jld.base.core.security.User) authentication.getPrincipal();
		
		ModelFilter modelFilter = new ModelFilter();
		modelFilter.getAttributes().add(new ModelAttribute("user.username", loguedInUser.getUsername(), Constants.MODEL_OPERATION_EQUAL));
		modelFilter.getAttributes().add(new ModelAttribute("filekey", pictureid, Constants.MODEL_OPERATION_EQUAL));
		if(StringUtils.isNotEmpty(pictureextension)) {
			modelFilter.getAttributes().add(new ModelAttribute("fileextension", pictureextension, Constants.MODEL_OPERATION_EQUAL));
		}
		List<Picture> listPictures = pictureDao.findList(modelFilter);
		if(listPictures == null || listPictures.size() == 0) {
			throw new EntityNotFoundException("ERROR_ITEM_NOT_FOUND", "ERROR_ITEM_NOT_FOUND");
		}
		
		String path = repositoryPath + File.separator + listPictures.get(0).getFilepath() + File.separator + pictureid + "." + listPictures.get(0).getFileextension();
        
        return new File(path);
	}
}
