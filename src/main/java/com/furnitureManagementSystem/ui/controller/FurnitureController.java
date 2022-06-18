package com.furnitureManagementSystem.ui.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.furnitureManagementSystem.rest.model.request.FurnitureDetailsRequestModel;
import com.furnitureManagementSystem.service.FurnitureService;
import com.furnitureManagementSystem.shared.dto.FurnitureDto;

@Controller
public class FurnitureController {

	@Autowired
	FurnitureService service;

	@GetMapping("/furnitures")
	public String listAll(Model model) {

		List<FurnitureDto> furnitures = service.getFurnitures(1, 25);

		model.addAttribute("furnitures", furnitures);
		return "furnitures";
	}

	@GetMapping(path = "/furniture/{id}")
	public String getFurniture(@PathVariable String id, Model model) {

		FurnitureDto furniture = service.getFurniture(id);

		model.addAttribute("furniture", furniture);
		return "furniture";
	}

	@GetMapping("/new_furniture")
	public String newFurniture(Model model) {
		FurnitureDetailsRequestModel furniture = new FurnitureDetailsRequestModel();

		model.addAttribute("furniture", furniture);
		return "new_furniture";
	}

	@PostMapping("/furnitures")
	public String saveFurniture(FurnitureDetailsRequestModel furniture, RedirectAttributes redirectAttributes) {

		FurnitureDto furnitureDto = new FurnitureDto();
		BeanUtils.copyProperties(furniture, furnitureDto);

		service.insertNewFurniture(furnitureDto);

		redirectAttributes.addFlashAttribute("message", "The furniture has been saved successfully");

		return "redirect:/furnitures";
	}

	@GetMapping(path = "/edit_furniture/{id}")
	public String editFurniture(@PathVariable String id, Model model) {
		FurnitureDetailsRequestModel furniture = new FurnitureDetailsRequestModel();

		FurnitureDto furnitureDto = service.getFurniture(id);

		BeanUtils.copyProperties(furnitureDto, furniture);

		model.addAttribute("furniture_id", furnitureDto.getFurnitureId());
		model.addAttribute("furniture", furniture);
		return "edit_furniture";
	}

	@PostMapping(path = "/furnitures/{id}")
	public String updateFurniture(@PathVariable String id, FurnitureDetailsRequestModel updatedFurniture,
			RedirectAttributes redirectAttributes) {

		FurnitureDto furnitureDto = new FurnitureDto();
		BeanUtils.copyProperties(updatedFurniture, furnitureDto);

		service.updateFurniture(id, furnitureDto);

		redirectAttributes.addFlashAttribute("message", "The furniture has been updated successfully");

		return "redirect:/furnitures";
	}

	@GetMapping(path = "/delete_furniture/{id}")
	public String deleteFurniture(@PathVariable String id, RedirectAttributes redirectAttributes) {
		service.deleteFurniture(id);

		redirectAttributes.addFlashAttribute("message", "The furniture has been deleted successfully");

		return "redirect:/furnitures";
	}
}
