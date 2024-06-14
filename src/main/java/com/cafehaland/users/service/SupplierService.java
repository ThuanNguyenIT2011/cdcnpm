package com.cafehaland.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cafehaland.exception.SupplierNotFoundException;
import com.cafehaland.pojo.Ingredient;
import com.cafehaland.pojo.Supplier;
import com.cafehaland.users.repository.IngredientRepository;
import com.cafehaland.users.repository.SupplierRepository;

@Service
public class SupplierService {
	@Autowired
	private SupplierRepository supplierReopo;
	
	@Autowired IngredientRepository ingredientRepo;
	
	public List<Supplier> listAll(String sortDir, String sortField, String keyword){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		List<Supplier> suppliers = supplierReopo.findAll(keyword, sort);
		return suppliers;
	}
	
	public void save(Supplier supplier) {
		supplierReopo.save(supplier);
	}
	
	public List<Ingredient> listIngredients() {
		return ingredientRepo.findAll();
	}
	
	public void delete(Integer id) throws SupplierNotFoundException {
		try {
			Supplier supplier = supplierReopo.findById(id).get();
			supplierReopo.deleteById(id);
		} catch (Exception e) {
			if (supplierReopo.existsById(id)) {
				throw new SupplierNotFoundException("Cannot delete Supplier ID " + id);
			} else {
				throw new SupplierNotFoundException("Could not find any Supplier with ID " + id);
			}
		}
	}
	
	public Supplier get(Integer id) throws SupplierNotFoundException {
		try {
			Supplier supplier = supplierReopo.findById(id).get();
			return supplier;
		} catch (Exception e) {
			throw new SupplierNotFoundException("Could not find any Supplier with ID " + id);
		}
	}
	
	public boolean isUniqueName(Integer id, String name) {
		Supplier supplier = supplierReopo.findSupplierByName(name);
		if (supplier == null) return true;
	
		return supplier.getId() == id;
	}
}
