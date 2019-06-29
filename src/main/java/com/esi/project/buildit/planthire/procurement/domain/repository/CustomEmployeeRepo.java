package com.esi.project.buildit.planthire.procurement.domain.repository;


import com.esi.project.buildit.planthire.common.domain.enums.Role;
import com.esi.project.buildit.planthire.common.domain.model.Employee;

import java.util.Optional;

public interface CustomEmployeeRepo {

	Optional<Employee> findOneEmployeeByRole(Role role);

}
