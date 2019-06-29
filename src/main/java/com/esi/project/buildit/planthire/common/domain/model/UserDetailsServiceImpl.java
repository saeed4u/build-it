package com.esi.project.buildit.planthire.common.domain.model;

import com.esi.project.buildit.planthire.procurement.domain.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private EmployeeRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = repo.findEmployeeByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username "+username+" not found"));
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_" + employee.getRole());

		return new AuthUser(employee.id,username, employee.password, grantedAuthorities,employee.getName(),employee.role);
	}
}
