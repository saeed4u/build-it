package com.esi.project.buildit.planthire.procurement.application.dto;

import com.esi.project.buildit.planthire.common.application.dto.BusinessPeriodDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class RentItPORequestDTO extends ResourceSupport {

	PlantInventoryEntryDTO plant;

	BusinessPeriodDTO rentalPeriod;

	String address;

	BigDecimal total;

}
