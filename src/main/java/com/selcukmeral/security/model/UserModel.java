package com.selcukmeral.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private List<AuthorityModel> authorities = new ArrayList<AuthorityModel>();

	

	@Override
	public Collection<AuthorityModel> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	
}
