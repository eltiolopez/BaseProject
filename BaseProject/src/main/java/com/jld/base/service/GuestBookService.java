package com.jld.base.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jld.base.dao.GuestbookDao;
import com.jld.base.dao.UserDao;
import com.jld.base.form.GuestBookCreateForm;
import com.jld.base.form.GuestBookSearchForm;
import com.jld.base.model.Guestbook;
import com.jld.base.model.User;
import com.jld.base.pagination.PaginationResultInfo;

@Service
@Transactional
public class GuestBookService {
	
	@Autowired
	private GuestbookDao guestbookDao;
	
	@Autowired
	private UserDao userDao;

	@Transactional
	public void addNewMessage(GuestBookCreateForm form) {
		Guestbook message = new Guestbook();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User loguedInUser = (org.springframework.security.core.userdetails.User)authentication.getPrincipal();
		
		User user = new User();
		user.setUsername(loguedInUser.getUsername());
		
		//message.setUsername(form.getUsername());
		message.setUser(userDao.searchUsersByFieldsPaginated(user, 0, 0).get(0));
		message.setMessage(form.getMessage());
		message.setDate(Calendar.getInstance().getTime());
		
		guestbookDao.create(message);
	}

	public List<Guestbook> getMessages() {
		return guestbookDao.findAll();
	}

	public PaginationResultInfo<Guestbook> getMessages(GuestBookSearchForm guestBookSearchForm) {
		PaginationResultInfo<Guestbook> resultInfo = new PaginationResultInfo<Guestbook>();
		resultInfo.setListaResultados(guestbookDao.findByForm(guestBookSearchForm));
		resultInfo.setTotalResultados(guestbookDao.countByForm(guestBookSearchForm));
		
		return resultInfo;
	}
}
