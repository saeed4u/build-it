package com.esi.project.buildit.planthire.common.domain.model;

import com.esi.project.buildit.planthire.common.domain.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class AuthUser extends User {
	private String name;
	private Role role;
	private String token;
	private Long id;

	public AuthUser(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, String name, Role role) {
		super(username, password, authorities);
		this.name = name;
		this.role = role;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Role getRole() {
		return role;
	}

	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}
}
