package com.cafehaland.users.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cafehaland.Security.AccountDetail;
import com.cafehaland.exception.IngredientNotFoundException;
import com.cafehaland.pojo.Ingredient;
import com.cafehaland.pojo.User;
import com.cafehaland.users.repository.IngredientRepository;
import com.cafehaland.users.service.IngredientService;

@Controller
public class IngredientController {
	@Autowired
	private IngredientService service;
	
	@GetMapping("/ingredients")
	public String viewIngredient(@Param("sortDir")String sortDir, 
			@Param("keyword")String keyword, Model model) {
		sortDir = sortDir == null || sortDir.isEmpty()? "asc" : sortDir;
		keyword = keyword == null || keyword.isEmpty() ? "" : keyword;
		String reverseSort = sortDir.equals("asc") ? "desc" : "asc";
		
		List<Ingredient> listIngredients = service.listAll(sortDir, "name", keyword);
		
		model.addAttribute("listIngredients", listIngredients);
		model.addAttribute("reverseSort", reverseSort);
		model.addAttribute("keyword", keyword);
		model.addAttribute("sortDir", sortDir);
		
		return "/ingredients/ingredients";
	}
	
	@GetMapping("/ingredients/new")
	public String newIngredient(Model model) {
		Ingredient ingredient = new Ingredient();
		ingredient.setStock(0);
		model.addAttribute("ingredient", ingredient);
		model.addAttribute("titlePage", "Add Ingredient");
		return "/ingredients/ingredents_form";
	}
	
	@PostMapping("/ingredients/save")
	public String addIngredient(Ingredient ingredient, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal AccountDetail accountDetail) {
		User user = new User(accountDetail.getIduser());
		ingredient.setUser(user);
		ingredient.setDate(new Date());
		service.save(ingredient);
		redirectAttributes.addFlashAttribute("message", "The Ingredient has been saved successfully");
		return "redirect:/ingredients";
	}
	
	@GetMapping("/ingredients/delete/{id}")
	public String delete(@PathVariable(name = "id")Integer id,
			RedirectAttributes redirectAttributes) {
		try {
			service.delete(id);
			redirectAttributes.addFlashAttribute("message", "The ingredient id " + id + " has been deleted successfully");
		} catch (IngredientNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/ingredients";
	}
	
	@GetMapping("/ingredients/edit/{id}")
	public String edit(@PathVariable(name = "id")Integer id,
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Ingredient ingredientInDb = service.get(id);
			model.addAttribute("ingredient",ingredientInDb);
			redirectAttributes.addFlashAttribute("message", "The ingredient id " + id + " has been deleted successfully");
		} catch (IngredientNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", "The Ingredient has been saved successfully");
		}
		return "/ingredients/ingredents_form";
	}
	
}




