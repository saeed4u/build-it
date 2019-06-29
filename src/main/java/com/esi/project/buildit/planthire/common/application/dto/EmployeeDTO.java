package com.esi.project.buildit.planthire.common.application.dto;

import com.esi.project.buildit.planthire.common.domain.enums.Role;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class EmployeeDTO extends ResourceSupport {

	Long _id;

	Role role;

	String firstName;

	String lastName;

	String name;

}
