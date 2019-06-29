package com.esi.project.buildit.planthire.common.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDate;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class BusinessPeriodDTO extends ResourceSupport {

	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate startDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate endDate;
}
