package com.esi.project.buildit.planthire.procurement.application.service;

import com.esi.project.buildit.planthire.common.application.dto.EmployeeDTO;
import com.esi.project.buildit.planthire.common.application.exception.ResourceNotFoundException;
import com.esi.project.buildit.planthire.common.application.service.EmployeeAssembler;
import com.esi.project.buildit.planthire.common.domain.enums.Role;
import com.esi.project.buildit.planthire.common.domain.model.Employee;
import com.esi.project.buildit.planthire.procurement.domain.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	private EmployeeRepository repository;
	private EmployeeAssembler assembler;

	public EmployeeService(EmployeeRepository repository, EmployeeAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	public EmployeeDTO createEmployee(String name, Role role){
		Employee employee = new Employee();
		employee.setName(name);
		employee.setRole(role);
		return assembler.toResource(repository.save(employee));
	}

	public Employee getEmployeeWithRole(Role role) {
		return repository.findOneEmployeeByRole(role)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("No employee with role %s was found",role)));
	}

	public EmployeeDTO getEmployeeWithID(Long id){
		return assembler.toResource(repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("No employee with ID %s was found",id))));
	}



}
