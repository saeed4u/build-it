package com.esi.project.buildit.planthire.procurement.application.dto;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class SupplierDTO extends ResourceSupport {

	Long _id;

	String name;

}