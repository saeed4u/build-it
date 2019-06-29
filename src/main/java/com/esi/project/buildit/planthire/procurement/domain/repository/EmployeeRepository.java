package com.esi.project.buildit.planthire.procurement.domain.repository;

import com.esi.project.buildit.planthire.common.domain.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long>,CustomEmployeeRepo {
	Optional<Employee> findEmployeeByUsername(String username);
}
