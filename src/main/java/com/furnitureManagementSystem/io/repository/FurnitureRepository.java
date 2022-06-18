package com.furnitureManagementSystem.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.furnitureManagementSystem.io.entity.FurnitureEntity;

@Repository
public interface FurnitureRepository extends PagingAndSortingRepository<FurnitureEntity, Long> {
	FurnitureEntity findByFurnitureId(String furnitureId);
}
