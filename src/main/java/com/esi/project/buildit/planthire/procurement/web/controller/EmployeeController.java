package com.esi.project.buildit.planthire.procurement.web.controller;

import com.esi.project.buildit.planthire.common.application.dto.EmployeeDTO;
import com.esi.project.buildit.planthire.procurement.application.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/{id}")
	public EmployeeDTO getEmployee(@PathVariable Long id) {
		return employeeService.getEmployeeWithID(id);
	}

	@PostMapping
	public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO) {
		return employeeService.createEmployee(employeeDTO.getName(), employeeDTO.getRole());
	}
/*

	@GetMapping("/role/{role}")
	public EmployeeDTO getEmployeeWithRole(@PathVariable String role) {
		return employeeService.getEmployeeWithRole(Role.valueOf(role));
	}
*/

}
