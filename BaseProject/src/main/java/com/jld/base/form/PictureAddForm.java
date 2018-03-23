package com.jld.base.form;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jld.base.pagination.PaginationForm;

public class PictureAddForm extends PaginationForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<MultipartFile> files;
	
	public PictureAddForm() {
		
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
}
