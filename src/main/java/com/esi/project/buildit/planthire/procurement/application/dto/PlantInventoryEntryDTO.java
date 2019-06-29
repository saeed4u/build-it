package com.esi.project.buildit.planthire.procurement.application.dto;

import com.esi.project.buildit.planthire.common.application.dto.MoneyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class PlantInventoryEntryDTO extends ResourceSupport {

	String href;

	Long _id;

	String name;

	String desc;

	MoneyDTO pricePerDay;

}