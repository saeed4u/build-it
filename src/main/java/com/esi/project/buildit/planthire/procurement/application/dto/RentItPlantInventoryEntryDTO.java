package com.esi.project.buildit.planthire.procurement.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
public class RentItPlantInventoryEntryDTO {

	Long _id;
	String name;
	String description;
	@Column(precision = 8, scale = 2)
	BigDecimal price;
	List<Link> links;

}