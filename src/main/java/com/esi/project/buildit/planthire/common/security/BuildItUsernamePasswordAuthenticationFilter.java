package com.esi.project.buildit.planthire.common.security;

import com.esi.project.buildit.planthire.common.domain.model.Auth;
import com.esi.project.buildit.planthire.common.domain.model.AuthUser;
import com.esi.project.buildit.planthire.common.domain.model.Error;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

public class BuildItUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private JwtConfig jwtConfig;

	private AuthenticationManager manager;

	public BuildItUsernamePasswordAuthenticationFilter(JwtConfig jwtConfig, AuthenticationManager manager) {
		this.jwtConfig = jwtConfig;
		this.manager = manager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfig.getUri(), "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {

			Auth auth = new ObjectMapper().readValue(request.getInputStream(), Auth.class);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					auth.getUsername(), auth.getPassword(), Collections.emptyList());

			return manager.authenticate(authToken);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		Long now = System.currentTimeMillis();
		String token = Jwts.builder()
				.setSubject(authResult.getName())
				.claim("authorities", authResult.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(now))
				.setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))  // in milliseconds
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
				.compact();

		response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		UserDetails userDetails =
				(UserDetails) authResult.getPrincipal();
		AuthUser authUser = (AuthUser) userDetails;
		authUser.setToken(token);
		PrintWriter writer = response.getWriter();
		writer.print(new ObjectMapper().writeValueAsString(authUser));
		writer.flush();
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		Error error = new Error();
		error.setError(true);
		writer.print(new ObjectMapper().writeValueAsString(error));
		writer.flush();
	}
}
