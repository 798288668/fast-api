/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * @author fengcheng
 * @version 2017/8/8
 */
public class JwtUser implements UserDetails {
	private final String id;
	private final String username;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;
	private final Date lastPasswordResetDate;
	private final Boolean isEnabled;

	JwtUser(String id, String username, String password, Collection<? extends GrantedAuthority> authorities,
			Date lastPasswordResetDate, Boolean isEnabled) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.lastPasswordResetDate = lastPasswordResetDate;
		this.isEnabled = isEnabled;
	}

	@JsonIgnore
	public String getId() {
		return id;
	}

	@JsonIgnore
	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
}
