package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modal.Food_fav;
import com.example.demo.repository.FoodFavRepository;

@RestController
@RequestMapping("/api")

public class FoodController {

	@Autowired
	FoodFavRepository foodFavRepository;

	@GetMapping("/foods")
	public ResponseEntity<List<Food_fav>> getAllFoods() {
		try {
			List<Food_fav> foods = new ArrayList<>();
			foodFavRepository.findAll().forEach(foods::add);
			if (foods.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(foods, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Get foods by non-veg name
	@GetMapping("/foods/nonveg/{nonVeg}")
	public ResponseEntity<List<Food_fav>> getFoodsByNonVeg(@PathVariable("nonVeg") String nonVeg) 
	{
		List<Food_fav> foods = foodFavRepository.findByNonVeg(nonVeg);
		if (foods.isEmpty()) 
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(foods, HttpStatus.OK);
	}

	@GetMapping("/foods/{id}")
	public ResponseEntity<Food_fav> getFoodById(@PathVariable("id") int id) {
		Optional<Food_fav> foodData = foodFavRepository.findById(id);
		if (foodData.isPresent()) {
			return new ResponseEntity<>(foodData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/foods")
	public ResponseEntity<Food_fav> createFood(@RequestBody Food_fav food) {
		try {
			Food_fav newFood = new Food_fav(
				food.getName(),
				food.getVeg(),
				food.getVegPrice(),
				food.getNonVeg(),
				food.getNonVegPrice(),
				food.getReason()
			);
			Food_fav savedFood = foodFavRepository.save(newFood);
			return new ResponseEntity<>(savedFood, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@DeleteMapping("/foods/{id}")
	public ResponseEntity<HttpStatus> deleteFood(@PathVariable("id") int id) {
		try {
			foodFavRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/foods") 
	public ResponseEntity<HttpStatus> deleteAllFoods() {
		try {
			foodFavRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@PutMapping("/foods/{id}")
	public ResponseEntity<Food_fav> updateFood(@PathVariable("id") int id, @RequestBody Food_fav food) {
		Optional<Food_fav> foodData = foodFavRepository.findById(id);
		if (foodData.isPresent()) {
			Food_fav existingFood = foodData.get();
			existingFood.setName(food.getName());
			existingFood.setVeg(food.getVeg());
			existingFood.setVegPrice(food.getVegPrice());
			existingFood.setNonVeg(food.getNonVeg());
			existingFood.setNonVegPrice(food.getNonVegPrice());
			existingFood.setReason(food.getReason());
			Food_fav updatedFood = foodFavRepository.save(existingFood);
			return new ResponseEntity<>(updatedFood, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}	


	@PutMapping("/foods")
	public ResponseEntity<List<Food_fav>> updateAllFoods(@RequestBody List<Food_fav> foods) {
		try {
			List<Food_fav> updatedFoods = new ArrayList<>();
			for (Food_fav food : foods) {
				Optional<Food_fav> foodData = foodFavRepository.findById(food.getId());
				if (foodData.isPresent()) {
					Food_fav existingFood = foodData.get();
					existingFood.setName(food.getName());
					existingFood.setVeg(food.getVeg());
					existingFood.setVegPrice(food.getVegPrice());
					existingFood.setNonVeg(food.getNonVeg());
					existingFood.setNonVegPrice(food.getNonVegPrice());
					existingFood.setReason(food.getReason());
					updatedFoods.add(foodFavRepository.save(existingFood));
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			}
			return new ResponseEntity<>(updatedFoods, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	
}