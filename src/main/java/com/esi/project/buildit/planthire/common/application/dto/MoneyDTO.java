package com.esi.project.buildit.planthire.common.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import java.math.BigDecimal;

// @Value(staticConstructor = "of")
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class MoneyDTO extends ResourceSupport {

	@Column(precision = 8, scale = 2)
	BigDecimal total;

}