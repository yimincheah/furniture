package com.furnitureManagementSystem.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.furnitureManagementSystem.exception.FurnitureServiceException;
import com.furnitureManagementSystem.io.entity.FurnitureEntity;
import com.furnitureManagementSystem.io.repository.FurnitureRepository;
import com.furnitureManagementSystem.rest.model.response.ErrorMessages;
import com.furnitureManagementSystem.service.FurnitureService;
import com.furnitureManagementSystem.shared.Utils;
import com.furnitureManagementSystem.shared.dto.FurnitureDto;

@Service
public class FurnitureServiceImp implements FurnitureService {

	@Autowired
	FurnitureRepository repo;

	@Autowired
	Utils utils;

	// Rest
	@Override
	public FurnitureDto insertNewFurniture(FurnitureDto furniture) {

		FurnitureEntity entity = new FurnitureEntity();
		BeanUtils.copyProperties(furniture, entity);

		String publicFurnitureId = utils.generatedPublicId(30);
		entity.setFurnitureId(publicFurnitureId);

		FurnitureEntity savedFurniture = repo.save(entity);

		FurnitureDto returnValue = new FurnitureDto();
		BeanUtils.copyProperties(savedFurniture, returnValue);

		return returnValue;
	}

	@Override
	public FurnitureDto getFurniture(String furnitureId) {

		FurnitureDto returnValue = new FurnitureDto();

		FurnitureEntity furnitureEntity = repo.findByFurnitureId(furnitureId);

		if (furnitureEntity == null)
			throw new FurnitureServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage(), HttpStatus.NOT_FOUND);

		BeanUtils.copyProperties(furnitureEntity, returnValue);

		return returnValue;
	}

	@Override
	public List<FurnitureDto> getFurnitures(int page, int limit) {

		List<FurnitureDto> returnValue = new ArrayList<>();

		if (page > 0)
			page = page - 1;

		Pageable pageableRequest = PageRequest.of(page, limit);

		Page<FurnitureEntity> furnituresPage = repo.findAll(pageableRequest);

		List<FurnitureEntity> furnitures = furnituresPage.getContent();

		for (FurnitureEntity entity : furnitures) {
			FurnitureDto furnitureDto = new FurnitureDto();
			BeanUtils.copyProperties(entity, furnitureDto);
			returnValue.add(furnitureDto);
		}

		return returnValue;
	}

	@Override
	public FurnitureDto updateFurniture(String furnitureId, FurnitureDto furniture) {
		FurnitureDto returnValue = new FurnitureDto();

		FurnitureEntity furnitureEntity = repo.findByFurnitureId(furnitureId);

		if (furnitureEntity == null)
			throw new FurnitureServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage(), HttpStatus.NOT_FOUND);

		furnitureEntity.setName(furniture.getName());
		furnitureEntity.setBrand(furniture.getBrand());
		furnitureEntity.setCategory(furniture.getCategory());
		furnitureEntity.setDescription(furniture.getDescription());
		furnitureEntity.setPrice(furniture.getPrice());

		FurnitureEntity updatedFurniture = repo.save(furnitureEntity);
		BeanUtils.copyProperties(updatedFurniture, returnValue);

		return returnValue;
	}

	@Override
	public void deleteFurniture(String furnitureId) {
		FurnitureEntity furnitureEntity = repo.findByFurnitureId(furnitureId);

		if (furnitureEntity == null)
			throw new FurnitureServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage(), HttpStatus.NOT_FOUND);

		repo.delete(furnitureEntity);

	}

}
