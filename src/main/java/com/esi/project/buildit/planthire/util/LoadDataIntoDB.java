package com.esi.project.buildit.planthire.util;

import com.esi.project.buildit.planthire.common.domain.enums.Role;
import com.esi.project.buildit.planthire.common.domain.model.Employee;
import com.esi.project.buildit.planthire.procurement.domain.model.ConstructionSite;
import com.esi.project.buildit.planthire.procurement.domain.model.Supplier;
import com.esi.project.buildit.planthire.procurement.domain.repository.ConstructionSiteRepository;
import com.esi.project.buildit.planthire.procurement.domain.repository.EmployeeRepository;
import com.esi.project.buildit.planthire.procurement.domain.repository.SupplierRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class LoadDataIntoDB {

	private static final Logger logger = LoggerFactory.getLogger(LoadDataIntoDB.class);


	@Autowired
	private BCryptPasswordEncoder encoder;

	@Bean
	public CommandLineRunner initDatabase(EmployeeRepository employeeRepo, ConstructionSiteRepository constructionSiteRepository, SupplierRepo supplierRepo) {
		return args -> {
			logger.info("Preloading db {}", args);
			employeeRepo.save(new Employee(Role.SITE_ENGINEER,"Saeed","Issah","brasaeed",encoder.encode("password")));
			employeeRepo.save(new Employee(Role.WORKS_ENGINEER,"Sachini","Sachini","sachini",encoder.encode("password")));

			constructionSiteRepository.save(new ConstructionSite("raatuse 22"));
			constructionSiteRepository.save(new ConstructionSite("narva 25"));
			constructionSiteRepository.save(new ConstructionSite("narva 27"));

			supplierRepo.save(new Supplier("RentIt 1"));
			supplierRepo.save(new Supplier("RentIt 2"));
			supplierRepo.save(new Supplier("RentIt 1"));
		};
	}
}
