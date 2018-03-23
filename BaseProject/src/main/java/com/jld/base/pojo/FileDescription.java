package com.jld.base.pojo;

public class FileDescription {
	
	private String path;
	
	private String key;
	
	private String fileName;
	
	private String fileExtension;
	
	private String description;

	public FileDescription(String path, String key, String fileName, String fileExtension, String description) {
		super();
		this.path = path;
		this.key = key;
		this.fileName = fileName;
		this.fileExtension = fileExtension;
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
