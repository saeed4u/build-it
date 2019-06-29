package com.esi.project.buildit.planthire.procurement.application.dto;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class ConstructionSiteDTO extends ResourceSupport {

	Long _id;

	String address;

	@Override
	public boolean hasLinks() {
		return false;
	}

	@Override
	public boolean hasLink(String rel) {
		return false;
	}
}
