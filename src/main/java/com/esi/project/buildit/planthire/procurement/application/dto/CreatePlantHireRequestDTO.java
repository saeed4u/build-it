package com.esi.project.buildit.planthire.procurement.application.dto;

import com.esi.project.buildit.planthire.common.application.dto.BusinessPeriodDTO;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class CreatePlantHireRequestDTO extends ResourceSupport {

	Long constructionSiteId;

	Long supplierId;

	String plantHref;

	BusinessPeriodDTO rentalPeriod;

	Long plantId;

}