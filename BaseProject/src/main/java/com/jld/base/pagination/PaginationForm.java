package com.jld.base.pagination;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class PaginationForm {

	private int from;
	
	private int pageSize;
	
	private String orderBy;		// Order by field
	
	private String order;		// ASC or DESC
	
	private int currentPage;
	
	private int page;

	public PaginationForm() {
		super();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication instanceof AnonymousAuthenticationToken) {
			this.pageSize = PaginationConfiguration.DEFAULT_PAGE_SIZE;
		}
		else {
			com.jld.base.core.security.User loguedInUser = (com.jld.base.core.security.User) authentication.getPrincipal();
			this.pageSize = loguedInUser.getUserPreferences().getNumResultsInPage();
		}
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}
