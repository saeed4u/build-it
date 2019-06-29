package com.esi.project.buildit.planthire.procurement.web.controller;

import com.esi.project.buildit.planthire.common.application.dto.BusinessPeriodDTO;
import com.esi.project.buildit.planthire.common.security.JwtConfig;
import com.esi.project.buildit.planthire.procurement.application.dto.PlantInventoryEntryDTO;
import com.esi.project.buildit.planthire.procurement.application.service.PlantInventoryEntryService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/plants")
public class PlantInventoryEntryController {

	private PlantInventoryEntryService service;

	@Autowired
	private JwtConfig jwtConfig;

	public PlantInventoryEntryController(PlantInventoryEntryService service) {
		this.service = service;
	}


	@GetMapping
	public List<PlantInventoryEntryDTO> queryPlants(HttpServletRequest request, @RequestParam(name = "name") String name,
													@RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
													@RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

		Logger logger = LoggerFactory.getLogger(this.getClass());

		String auth = request.getHeader("Authorization");

		logger.info("Auth {}",auth);
		try{
			Claims claims = Jwts.parser()
					.setSigningKey(jwtConfig.getSecret().getBytes())
					.parseClaimsJws(auth.replace(jwtConfig.getPrefix(),""))
					.getBody();
			String username = claims.getSubject();
			if (username!=null){
				logger.info("Username {}",username);
			}
		}catch (Exception e){
			e.printStackTrace();

		}

		return service.queryRentitCatalog(name, BusinessPeriodDTO.of(startDate, endDate));
	}
}
