package com.esi.project.buildit.planthire.procurement.application.assembler;

import com.esi.project.buildit.planthire.procurement.application.dto.CommentDTO;
import com.esi.project.buildit.planthire.procurement.domain.model.Comment;
import com.esi.project.buildit.planthire.procurement.web.controller.CommentController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class CommentAssembler extends ResourceAssemblerSupport<Comment, CommentDTO> {

	public CommentAssembler() {
		super(CommentController.class, CommentDTO.class);
	}

	@Override
	public CommentDTO toResource(Comment comment) {
		CommentDTO dto = createResourceWithId(comment.getId(), comment);
		dto.set_id(comment.getId());
		dto.setComment(comment.getComment());
		dto.setPlantHireID(comment.getPlantHireRequest().getId());
		return dto;
	}

}
