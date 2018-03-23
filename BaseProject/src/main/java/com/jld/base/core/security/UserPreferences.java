package com.jld.base.core.security;

public class UserPreferences {
	
	private String username;
	
	private String repositoryPath;
	
	private String profilePicture;
	
	private int numResultsInPage;

	public UserPreferences() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRepositoryPath() {
		return repositoryPath;
	}

	public void setRepositoryPath(String repositoryPath) {
		this.repositoryPath = repositoryPath;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public int getNumResultsInPage() {
		return numResultsInPage;
	}

	public void setNumResultsInPage(int numResultsInPage) {
		this.numResultsInPage = numResultsInPage;
	}
}
