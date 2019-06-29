package com.esi.project.buildit.planthire.procurement.domain.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long id;

	@JoinColumn(name = "plant_hire_request_id", nullable = false)
	@ManyToOne(optional = false)
	PlantHireRequest plantHireRequest;

	@Column(nullable = false)
	String comment;

}