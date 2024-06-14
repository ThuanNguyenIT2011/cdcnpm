package com.cafehaland.users.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cafehaland.Dao.ProductForm;
import com.cafehaland.Security.AccountDetail;
import com.cafehaland.exception.ProductNotFoundException;
import com.cafehaland.pojo.Ingredient;
import com.cafehaland.pojo.Product;
import com.cafehaland.pojo.ProductSize;
import com.cafehaland.pojo.Syntax;
import com.cafehaland.pojo.User;
import com.cafehaland.pojo.id.SyntaxId;
import com.cafehaland.uploadfile.UploadFileUntil;
import com.cafehaland.users.service.ProductService;
import com.fasterxml.jackson.annotation.JacksonInject.Value;

@Controller
public class ProductController {
	@Autowired
	private ProductService service;
	
	@GetMapping("/products")
	public String viewFirstPage(Model model) {
		return viewPageProduct("asc", "", 1, model);
	}
	
	@GetMapping("/products/page/{pageNum}")
	public String viewPageProduct(@Param("sortDir") String sortDir, @Param("keyword") String keyword,
			@PathVariable("pageNum") int pageNum, Model model) {
		sortDir = sortDir == null || sortDir.isEmpty() ? "asc" : sortDir;
		keyword = keyword == null || sortDir.isEmpty() ? "" : keyword;
		String sortReverse = sortDir.equals("asc") ? "desc" : "asc";
		
		Page<Product> page = service.listPage(pageNum, sortDir, "name", keyword);
		
		int startCount = (pageNum - 1)*service.PAGE_PRO_NUM + 1;
		int endCount = startCount + service.PAGE_PRO_NUM - 1;
		if (endCount > page.getTotalElements()) {
			endCount = (int)page.getTotalElements();
		}
		
		List<Product> listProducts = page.getContent();
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("reverseSort", sortReverse);
		model.addAttribute("keyword", keyword);
		model.addAttribute("sortDir", sortDir);
		
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		
		model.addAttribute("sortField", "name");
		
		return "/products/products";
	}
	
	@GetMapping("/products/new")
	public String addProduct(Model model) {
		Product product = new Product();
		
		List<String> productSize = new ArrayList<>(Arrays.asList("X", "L"));
		
		model.addAttribute("product", product);
		model.addAttribute("sizes", productSize);
		model.addAttribute("listIngredients", service.listIngredients());
		
		return "/products/products_form";
	}
	
	@PostMapping("/products/save")
	public String save(Product product, 
			@RequestParam("fileImage") MultipartFile multipartFile,
			@RequestParam("nameSizes") String[] nameSizes,
			@RequestParam("priceProducs") String[] priceProducs,
			@RequestParam("sizeIDs") String[] sizeIDs,
			@AuthenticationPrincipal AccountDetail logged, 
			RedirectAttributes redirectAttributes) throws IOException {
		setPriceProduct(sizeIDs, nameSizes, priceProducs, product);
		if(!multipartFile.isEmpty()) {
			product.setUser(new User(logged.getIduser()));
			String nameFile = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			product.setImage(nameFile);
			Product saveProduct = service.save(product);
			
			String uploadDir = "product-photo/" + saveProduct.getId();
			UploadFileUntil.clearDir(uploadDir);
			UploadFileUntil.saveFile(uploadDir, nameFile, multipartFile);
		} else {
			service.save(product);
		}
		redirectAttributes.addFlashAttribute("message", "The product has been saved successfully");
		return "redirect:/products/page/1?keyword="+product.getName()+"&sortDir=asc";
	}
	

	private void setPriceProduct(String[] sizeIDs, String[] nameSizes, String[] priceProducs, Product product) {
		if (nameSizes == null || nameSizes.length == 0) return;
		
		List<ProductSize> productSizes = new ArrayList<>();
		for (int i = 0; i < nameSizes.length; i++) {
			String name = nameSizes[i];
			
			double price = priceProducs[i].isEmpty() ? 0 : Double.parseDouble(priceProducs[i]);

			if (!name.isEmpty() && price >= 0) {
				Integer id = Integer.parseInt(sizeIDs[i]);
				if (id != 0) {
					productSizes.add(new ProductSize(id, price, name, product));
				} else {
					productSizes.add(new ProductSize(name, price, product));
				}
			}
		}
		System.out.println(productSizes.size());
		product.setProductSizes(productSizes);
		
	}

	@GetMapping("/products/edit/{id}")
	public String updateProduct(@PathVariable("id")Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Product productInDb = service.get(id);
				
			model.addAttribute("product", productInDb);
			model.addAttribute("titlePage", "Edit Product");
			
			return "/products/products_form";
			
		} catch (ProductNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/products";
		}
	}
	
	@GetMapping("/products/{id}/enabled/{status}")
	public String updateEnabled(@PathVariable(name = "id")Integer id, @PathVariable(name = "status")boolean status,
			Model model, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal AccountDetail logged) {
		try {
			service.updateEnabled(id, status);
			
			String strStatus = status ? "Enabled" : "Disabled";
			redirectAttributes.addFlashAttribute("message", "The product id " + id + " has been " + strStatus);
		} catch (ProductNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/products";
	}
	
	@GetMapping("/products/delete/{id}")
	public String delete(@PathVariable(name = "id")Integer id, Model model, 
			RedirectAttributes redirectAttributes) {
		try {
			service.delete(id);
			redirectAttributes.addFlashAttribute("message", "The staff id " + id + " has been deleted successfully");
		} catch (ProductNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/products";
	}
	
	//add and edit syntax
	@GetMapping("/products/editSyntax/{id}")
	public String addSyntax(@PathVariable(name = "id")Integer id,  Model model, 
			RedirectAttributes redirectAttributes) {
		try {
			Product productInDb = service.get(id);
			List<Ingredient> listIngredients = service.listIngredients();
			
			List<Integer> listIngredientByProduct = service.listIngredientByProduct(id);
			
			model.addAttribute("listIngredients", listIngredients);
			model.addAttribute("product", productInDb);
			model.addAttribute("checkSave", "noSave");
			model.addAttribute("listIngredientByProduct", listIngredientByProduct);
			return "/syntaxs/syntaxs";
		} catch (ProductNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/products";
		}
	}
	
	private boolean isIngredientInSyntax(ProductSize size, Ingredient ingredient) {
		for (Syntax s : size.getSyntaxs()) {
			if (s.getIngredient().getId() == ingredient.getId()) {
				return true;
			}
		}
		return false;
	}
	
	@PostMapping("/products/addSyntax")
	public String addSyntax(@RequestParam("nameIngrediens") String[] nameSizes,
			@RequestParam("quantityIng") String[] quantitySizes,
			@RequestParam("sizeIDs") String[] sizeIDs,
			@RequestParam("ingredients") String[] ingredients, Model model,
			@RequestParam("idProduct") String idProduct,
			RedirectAttributes redirectAttributes
			) {
		
		int lengthSelectedIngredients = quantitySizes.length / sizeIDs.length;
		int idx = 0;
		
		for (int i = 0; i < sizeIDs.length; ++i) {
			Integer sizeId = Integer.parseInt(sizeIDs[i]);
			ProductSize productSize = service.getProductSize(sizeId);
			
			List<Syntax> syntaxs = new ArrayList<>();
			for (int j = 0; j < lengthSelectedIngredients; ++j) {
				Integer ingredientId = Integer.parseInt(ingredients[j]);
				double quan = Double.parseDouble(quantitySizes[idx]);
				
				Syntax syntax = new Syntax(new SyntaxId(productSize.getId(), ingredientId), productSize, new Ingredient(ingredientId), quan);
				syntaxs.add(syntax);
				idx++;
			}
			productSize.setSyntaxs(syntaxs);
			service.saveProductSize(productSize);	
		}
		
		redirectAttributes.addAttribute("check", "success");
		return "redirect:/products/editSyntax/" + idProduct;
	}
	
	
}
