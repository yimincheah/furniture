package com.furnitureManagementSystem.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.furnitureManagementSystem.rest.model.request.FurnitureDetailsRequestModel;
import com.furnitureManagementSystem.rest.model.response.FurnitureRest;
import com.furnitureManagementSystem.rest.model.response.OperationStatusModel;
import com.furnitureManagementSystem.rest.model.response.RequestOperationName;
import com.furnitureManagementSystem.rest.model.response.RequestOperationStatus;
import com.furnitureManagementSystem.service.FurnitureService;
import com.furnitureManagementSystem.shared.dto.FurnitureDto;

@RestController
@RequestMapping("api/furnitures") // http://localhost:8080/api/furnitures
public class FurnitureRestController {

	@Autowired
	FurnitureService service;

	@GetMapping(path = "/{id}")
	public FurnitureRest getFurniture(@PathVariable String id) {

		FurnitureRest returnValue = new FurnitureRest();

		FurnitureDto furnitureDto = service.getFurniture(id);

		BeanUtils.copyProperties(furnitureDto, returnValue);

		return returnValue;
	}

	@GetMapping
	public List<FurnitureRest> getFurnitures(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {

		List<FurnitureRest> returnValue = new ArrayList<>();

		List<FurnitureDto> furnitures = service.getFurnitures(page, limit);

		for (FurnitureDto furnitureDto : furnitures) {
			FurnitureRest furnitureRest = new FurnitureRest();
			BeanUtils.copyProperties(furnitureDto, furnitureRest);
			returnValue.add(furnitureRest);
		}

		return returnValue;
	}

	@PostMapping
	public FurnitureRest insertNewFurniture(@RequestBody FurnitureDetailsRequestModel furnitureDetails)
			throws Exception {

		FurnitureDto furnitureDto = new FurnitureDto();
		BeanUtils.copyProperties(furnitureDetails, furnitureDto);

		FurnitureDto newFurniture = service.insertNewFurniture(furnitureDto);
		FurnitureRest returnValue = new FurnitureRest();
		BeanUtils.copyProperties(newFurniture, returnValue);

		return returnValue;
	}

	@PutMapping(path = "/{id}")
	public FurnitureRest updateFurniture(@PathVariable String id,
			@RequestBody FurnitureDetailsRequestModel furnitureDetails) {

		FurnitureRest returnValue = new FurnitureRest();

		FurnitureDto furnitureDto = new FurnitureDto();
		BeanUtils.copyProperties(furnitureDetails, furnitureDto);

		FurnitureDto updatedFurniture = service.updateFurniture(id, furnitureDto);
		BeanUtils.copyProperties(updatedFurniture, returnValue);

		return returnValue;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFurniture(@PathVariable String id) {
		
		OperationStatusModel returnValue = new OperationStatusModel();

		service.deleteFurniture(id);
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

		return returnValue;
		
	}

}
