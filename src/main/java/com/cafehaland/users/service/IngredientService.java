package com.cafehaland.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cafehaland.exception.IngredientNotFoundException;
import com.cafehaland.pojo.Ingredient;
import com.cafehaland.users.repository.IngredientRepository;

@Service
public class IngredientService {
	public static final int QUANTIYT_MIN = 2;
	
	@Autowired
	private IngredientRepository ingredientRepo;
	public List<Ingredient> listAll(String sortDir, String sortField, String keyword){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		List<Ingredient> listIngredients =ingredientRepo.findAll(keyword, sort);
		
		return listIngredients;
	}
	
	public List<Ingredient> listAll(){
		return ingredientRepo.findAll();
	}
	
	public void save(Ingredient ingredient) {
		ingredientRepo.save(ingredient);
	}
	
	public boolean checkQuantityStock() {
		List<Ingredient> listIngrediens = ingredientRepo.findAll();
		for(Ingredient ingredient : listIngrediens) {
			if (ingredient.getStock() < QUANTIYT_MIN) {
				return false;
			}
		}
		return true;
	}
	
	public void delete(Integer id) throws IngredientNotFoundException {
		try {
			Ingredient ingredient = ingredientRepo.findById(id).get();
			
			ingredientRepo.deleteById(id);
		} catch (Exception e) {
			if (ingredientRepo.existsById(id)) {
				throw new IngredientNotFoundException("Cannot delete Ingredient ID " + id);
			} else {
				throw new IngredientNotFoundException("Could not find any Ingredient with ID " + id);
			}
		}
	}
	
	public Ingredient get(Integer id) throws IngredientNotFoundException {
		try {
			return ingredientRepo.findById(id).get();
		} catch (Exception e) {
			throw new IngredientNotFoundException("Could not find any Ingredient with ID " + id);
		}
	}
	
	public boolean isUniqueName(Integer id, String name) {
		Ingredient ingredient = ingredientRepo.findIngredientByName(name);
		if (ingredient == null) return true;
		
		if(ingredient != null && ingredient.getId() != id) {
			return false;
		}
		
		return true;
	}
}
