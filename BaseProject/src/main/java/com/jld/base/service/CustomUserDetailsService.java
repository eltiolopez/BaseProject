package com.jld.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.jld.base.core.security.User;
import com.jld.base.core.security.UserPreferences;

/**
 * A custom {@link UserDetailsService} where user information
 * is retrieved from a JPA repository
 */
@Transactional(readOnly=true)
@SessionAttributes("userInfo")
public class CustomUserDetailsService implements UserDetailsService {



	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private UserService userService;

	/**
	 * Returns a populated {@link User} object. 
	 * The username is first retrieved from the database and then mapped to 
	 * a {@link User} object.
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {

			String sql = "SELECT u FROM User u WHERE u.username=?1";
			Query query = entityManager.createQuery(sql);
			query.setParameter(1, username);

			com.jld.base.model.User user = (com.jld.base.model.User) query.getSingleResult();
			
			com.jld.base.model.Usergroup userGroup = user.getUsergroup();
			List<com.jld.base.model.Role> listRoles = userGroup.getRoles();

			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			UserPreferences userPreferences = userService.getUserPreferences(username);

			return new User(
					user.getUsername(), 
					user.getPassword(),//.toLowerCase(),
					enabled,
					accountNonExpired,
					credentialsNonExpired,
					accountNonLocked,
					getGrantedAuthorities(listRoles),
					userPreferences);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects
	 * @param roles {@link String} of roles
	 * @return list of granted authorities
	 */
	public static List<GrantedAuthority> getGrantedAuthorities(List<com.jld.base.model.Role> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (com.jld.base.model.Role userRole : roles) {
			authorities.add(new SimpleGrantedAuthority(userRole.getIdrole()));
		}
		return authorities;
	}
}