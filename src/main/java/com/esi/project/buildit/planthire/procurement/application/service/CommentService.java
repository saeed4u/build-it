package com.esi.project.buildit.planthire.procurement.application.service;

import com.esi.project.buildit.planthire.procurement.application.assembler.CommentAssembler;
import com.esi.project.buildit.planthire.procurement.application.dto.CommentDTO;
import com.esi.project.buildit.planthire.procurement.domain.model.Comment;
import com.esi.project.buildit.planthire.procurement.domain.model.PlantHireRequest;
import com.esi.project.buildit.planthire.procurement.domain.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

	private CommentRepository repository;

	private CommentAssembler assembler;


	@Autowired
	public CommentService(CommentRepository repository, CommentAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	@Transactional
	public CommentDTO createComment(CommentDTO comment, PlantHireRequest plantHireRequest) {
		Comment newComment = new Comment();
		newComment.setComment(comment.getComment());
		newComment.setPlantHireRequest(plantHireRequest);
		newComment = repository.save(newComment);
		plantHireRequest.getComments().add(newComment);
		return assembler.toResource(newComment);
	}

	@Transactional(readOnly = true)
	public List<CommentDTO> getAllComments() {
		return repository.findAll().stream().map(comment -> assembler.toResource(comment)).collect(Collectors.toList());
	}

}
