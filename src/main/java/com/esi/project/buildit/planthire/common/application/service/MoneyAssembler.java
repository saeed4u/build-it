package com.esi.project.buildit.planthire.common.application.service;

import com.esi.project.buildit.planthire.common.application.dto.MoneyDTO;
import com.esi.project.buildit.planthire.common.domain.model.Money;
import com.esi.project.buildit.planthire.procurement.web.controller.CommentController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class MoneyAssembler extends ResourceAssemblerSupport<Money, MoneyDTO> {

	public MoneyAssembler() {
		super(CommentController.class, MoneyDTO.class);
	}

	@Override
	public MoneyDTO toResource(Money period) {

		return MoneyDTO.of(period.getTotal());
	}

}
