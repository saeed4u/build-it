package com.esi.project.buildit.planthire.common.application.service;

import com.esi.project.buildit.planthire.common.application.dto.EmployeeDTO;
import com.esi.project.buildit.planthire.common.domain.model.Employee;
import com.esi.project.buildit.planthire.procurement.web.controller.EmployeeController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class EmployeeAssembler extends ResourceAssemblerSupport<Employee, EmployeeDTO> {

	public EmployeeAssembler() {
		super(EmployeeController.class, EmployeeDTO.class);
	}

	@Override
	public EmployeeDTO toResource(Employee employee) {
		EmployeeDTO dto = createResourceWithId(employee.getId(), employee);

		dto.set_id(employee.getId());
		dto.setFirstName(employee.getFirstName());
		dto.setLastName(employee.getLastName());
		dto.setName(employee.getName());
		dto.setRole(employee.getRole());

		return dto;
	}

}