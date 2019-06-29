package com.esi.project.buildit.planthire.procurement.application.assembler;

import com.esi.project.buildit.planthire.procurement.application.dto.ConstructionSiteDTO;
import com.esi.project.buildit.planthire.procurement.domain.model.ConstructionSite;
import com.esi.project.buildit.planthire.procurement.web.controller.ConstructionSiteController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class ConstructionSiteAssembler extends ResourceAssemblerSupport<ConstructionSite, ConstructionSiteDTO> {


	public ConstructionSiteAssembler() {
		super(ConstructionSiteController.class, ConstructionSiteDTO.class);
	}

	@Override
	public ConstructionSiteDTO toResource(ConstructionSite constructionSite) {
		ConstructionSiteDTO dto = createResourceWithId(constructionSite.getId(), constructionSite);
		dto.set_id(constructionSite.getId());
		dto.setAddress(constructionSite.getAddress());
		return dto;
	}
}
