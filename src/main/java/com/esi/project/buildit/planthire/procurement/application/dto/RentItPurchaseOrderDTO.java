package com.esi.project.buildit.planthire.procurement.application.dto;

import com.esi.project.buildit.planthire.common.application.dto.BusinessPeriodDTO;
import com.esi.project.buildit.planthire.common.domain.enums.RentItPurchaseOrderStatus;
import com.esi.project.buildit.planthire.common.domain.model.CustomLink;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class RentItPurchaseOrderDTO{
	Long _id;
	RentItPlantInventoryEntryDTO plant;
	BusinessPeriodDTO rentalPeriod;
	BigDecimal total;
	RentItPurchaseOrderStatus status;
	List<POExtensionDTO> poExtensionDTOS = new ArrayList<>();
	CustomLink _links;
	Link link;
}
