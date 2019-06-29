package com.esi.project.buildit.planthire.common.application.service;

import com.esi.project.buildit.planthire.common.application.dto.BusinessPeriodDTO;
import com.esi.project.buildit.planthire.common.domain.model.BusinessPeriod;
import com.esi.project.buildit.planthire.procurement.web.controller.CommentController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class BusinessPeriodAssembler extends ResourceAssemblerSupport<BusinessPeriod, BusinessPeriodDTO> {

	public BusinessPeriodAssembler() {
		super(CommentController.class, BusinessPeriodDTO.class);
	}

	@Override
	public BusinessPeriodDTO toResource(BusinessPeriod period) {
		return BusinessPeriodDTO.of(period.getStartDate(), period.getEndDate());
	}

}
