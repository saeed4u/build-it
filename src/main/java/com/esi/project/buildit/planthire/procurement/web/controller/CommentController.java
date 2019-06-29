package com.esi.project.buildit.planthire.procurement.web.controller;

import com.esi.project.buildit.planthire.procurement.application.dto.CommentDTO;
import com.esi.project.buildit.planthire.procurement.application.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plant-hire/comment")
public class CommentController extends BaseController<CommentDTO>{

	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@GetMapping
	public List<CommentDTO> getPlantHireComments(){
		return commentService.getAllComments();
	}


}
