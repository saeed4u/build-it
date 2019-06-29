package com.esi.project.buildit.planthire.procurement.web.controller;

import com.esi.project.buildit.planthire.common.rest.Response;
import com.esi.project.buildit.planthire.common.rest.Responses;
import com.esi.project.buildit.planthire.procurement.application.dto.CommentDTO;
import com.esi.project.buildit.planthire.procurement.application.dto.CreatePlantHireRequestDTO;
import com.esi.project.buildit.planthire.procurement.application.dto.POExtensionDTO;
import com.esi.project.buildit.planthire.procurement.application.dto.PlantHireRequestDTO;
import com.esi.project.buildit.planthire.procurement.application.service.PlantHireRequestService;
import com.esi.project.buildit.planthire.procurement.domain.model.PlantHireRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/hire-requests")
public class PlantHireRequestController {

	private PlantHireRequestService plantHireRequestService;

	private Logger logger = LoggerFactory.getLogger(PlantHireRequestController.class);

	public PlantHireRequestController(PlantHireRequestService plantHireRequestService) {
		this.plantHireRequestService = plantHireRequestService;
	}

	@GetMapping
	public List<PlantHireRequestDTO> getAllHireRequests() {
		return plantHireRequestService.getAllHireRequests();
	}

	@GetMapping("/{id}")
	public PlantHireRequestDTO getHireRequest(@PathVariable("id") Long id) {
		return plantHireRequestService.getPlantHireRequest(id);
	}

	@PostMapping
	public ResponseEntity createHireRequest(@RequestBody CreatePlantHireRequestDTO createPlantHireRequestDTO) throws URISyntaxException, ExecutionException, InterruptedException {

		logger.info("CreatePlantHireRequestDTO{}", createPlantHireRequestDTO);
		PlantHireRequestDTO plantHireRequestDTO = plantHireRequestService.createPlantHireRequest(createPlantHireRequestDTO);


		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(new URI(plantHireRequestDTO.getLink("self").getHref()));

		return new ResponseEntity<>(new Resource<>(
				plantHireRequestDTO
				,createUpdateLink(plantHireRequestDTO.get_id())
				,createAcceptLink(plantHireRequestDTO.get_id())
				,createRejectLink(plantHireRequestDTO.get_id())
				,createCancelLink(plantHireRequestDTO.get_id())
				,createAddExtentionLink(plantHireRequestDTO.get_id())), headers, HttpStatus.CREATED);
	}

	private Link createUpdateLink(Long id) {
		return linkTo(methodOn(this.getClass()).updateHireRequest(id,null)).withRel("update");
	}

	private Link createAcceptLink(Long id) throws ExecutionException, InterruptedException {
		return linkTo(methodOn(this.getClass()).acceptHireRequest(id)).withRel("accept");
	}

	private Link createRejectLink(Long id) {
		return linkTo(methodOn(this.getClass()).rejectHireRequest(id,null)).withRel("reject");
	}

	private Link createCancelLink(Long id) throws ExecutionException, InterruptedException {
		return linkTo(methodOn(this.getClass()).cancelHireRequest(id)).withRel("cancel");
	}

	private Link createAddExtentionLink(Long id) throws ExecutionException, InterruptedException {
		return linkTo(methodOn(this.getClass()).cancelHireRequest(id)).withRel("request_extension");
	}

	@PutMapping("/{id}")
	public PlantHireRequestDTO updateHireRequest(@PathVariable("id") Long id, @RequestBody CreatePlantHireRequestDTO createPlantHireRequestDTO) {
		return plantHireRequestService.updatePlantHireRequest(id, createPlantHireRequestDTO);
	}

	@PutMapping("/{id}/cancel")
	public Response cancelHireRequest(@PathVariable("id") Long id) throws ExecutionException, InterruptedException {
		plantHireRequestService.cancelHireRequest(id);
		return Responses.success();
	}

	@PutMapping("/{id}/accept")
	public Response acceptHireRequest(@PathVariable("id") Long id) throws ExecutionException, InterruptedException {
		plantHireRequestService.acceptHireRequest(id);
		return Responses.success();
	}

	@PutMapping("/{id}/reject")
	public PlantHireRequestDTO rejectHireRequest(@PathVariable("id") Long id, @RequestBody CommentDTO commentDTO) {
		return plantHireRequestService.rejectHireRequest(id, commentDTO);
	}

	@PutMapping("/{id}/extend")
	public Response requestExtension(@PathVariable("id") Long id, @RequestBody POExtensionDTO poExtensionDTO) throws ExecutionException, InterruptedException {
		plantHireRequestService.requestExtension(id,poExtensionDTO);
		return Responses.success();
	}

}
