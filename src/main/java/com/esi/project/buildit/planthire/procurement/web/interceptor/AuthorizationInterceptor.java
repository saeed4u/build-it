package com.esi.project.buildit.planthire.procurement.web.interceptor;

import com.esi.project.buildit.planthire.common.domain.enums.Role;
import com.esi.project.buildit.planthire.common.domain.model.Employee;
import com.esi.project.buildit.planthire.common.security.JwtConfig;
import com.esi.project.buildit.planthire.procurement.domain.repository.EmployeeRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	JwtConfig jwtConfig;

	private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String header = request.getHeader("Authorization");
		if (null != header && header.startsWith("Bearer")) {
			String token = header.replace("Bearer", "");
			try {
				Claims claims = Jwts.parser()
						.setSigningKey(jwtConfig.getSecret().getBytes())
						.parseClaimsJws(token)
						.getBody();
				String username = claims.getSubject();
				Employee employee = employeeRepository.findEmployeeByUsername(username).orElse(null);
				String url = request.getRequestURI();
				logger.info("URL {}", url);
				if (employee == null || (url.contains("cancel") && employee.getRole() != Role.SITE_ENGINEER) || ((url.contains("accept") || url.contains("reject")) && employee.getRole() != Role.WORKS_ENGINEER)) {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
					return false;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

}
