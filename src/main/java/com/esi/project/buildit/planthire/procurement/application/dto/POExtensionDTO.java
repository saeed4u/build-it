package com.esi.project.buildit.planthire.procurement.application.dto;

import com.esi.project.buildit.planthire.common.domain.enums.POExtensionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class POExtensionDTO {

	Long _id;


	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate endDate;

	POExtensionStatus status;

}
