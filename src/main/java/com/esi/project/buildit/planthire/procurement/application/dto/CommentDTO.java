package com.esi.project.buildit.planthire.procurement.application.dto;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class CommentDTO extends ResourceSupport {

	Long _id;

	Long plantHireID;

	String comment;

}