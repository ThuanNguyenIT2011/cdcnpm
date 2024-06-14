package com.cafehaland.users.controller;

import java.util.Date;
import java.util.HashMap;
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

import com.cafehaland.Dao.ReceiptForm;
import com.cafehaland.Security.AccountDetail;
import com.cafehaland.exception.SupplierNotFoundException;
import com.cafehaland.pojo.Receipt;
import com.cafehaland.pojo.Supplier;
import com.cafehaland.pojo.User;
import com.cafehaland.users.service.SupplierService;

@Controller
public class SupplierController {
	@Autowired
	private SupplierService service;
	
	@GetMapping("/suppliers")
	public String viewSuppliers(@Param("sortDir") String sortDir, @Param("keyword")String keyword,
			Model model) {
		String sortReverse = "";
		sortDir = sortDir == null || sortDir.isEmpty() ? "asc" : sortDir;
		keyword = keyword == null || keyword.isEmpty() ? "" : keyword;
		sortReverse = sortDir.equals("asc") ? "desc" : "asc";
		
		List<Supplier> listSuppliers = service.listAll(sortDir, "name",keyword);
		model.addAttribute("listSuppliers", listSuppliers);
		model.addAttribute("reverseSort", sortReverse);
		model.addAttribute("keyword", keyword);
		
		return "/suppliers/suppliers";
	}
	
	@GetMapping("/suppliers/new")
	public String addSupplier(Model model) {
		Supplier supplier = new Supplier();
		model.addAttribute("supplier", supplier);
		
		model.addAttribute("listIngredients", service.listIngredients());
		
		model.addAttribute("titlePage", "Add Supplier");
		return "/suppliers/supplier_form";
	}
	
	@PostMapping("/suppliers/save")
	public String save(Supplier supplier, @AuthenticationPrincipal AccountDetail accountDetail) {
		supplier.setUser(new User(accountDetail.getIduser()));
		supplier.setDate(new Date());
		service.save(supplier);
		return "redirect:/suppliers";
	}
	
	@GetMapping("/suppliers/delete/{id}")
	public String delete(@PathVariable(name = "id") Integer id,
			RedirectAttributes redirectAttributes) {
		try {
			service.delete(id);
			redirectAttributes.addFlashAttribute("message", "The ingredient id " + id + " has been deleted successfully");
		} catch (SupplierNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/suppliers";
	}
	
	@GetMapping("/suppliers/edit/{id}")
	public String edit(@PathVariable(name="id") Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Supplier supplier = service.get(id);
			model.addAttribute("titlePage", "Add Supplier");
			model.addAttribute("supplier", supplier);
			model.addAttribute("listIngredients", service.listIngredients());
			return "/suppliers/supplier_form";
		} catch (SupplierNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/suppliers";
		}
	}
}
