package com.esi.project.buildit.planthire.common.application.dto;

import com.esi.project.buildit.planthire.common.domain.enums.RentItPurchaseOrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(force = true)
public class RentItPOStatusUpdateDTO {

	@NotNull
	String href;

	@NotNull
	RentItPurchaseOrderStatus value;

}