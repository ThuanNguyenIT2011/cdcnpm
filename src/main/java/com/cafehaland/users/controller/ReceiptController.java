package com.cafehaland.users.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cafehaland.Dao.ReceiptForm;
import com.cafehaland.Security.AccountDetail;
import com.cafehaland.exception.ReceiptNotFoundException;
import com.cafehaland.pojo.Ingredient;
import com.cafehaland.pojo.Receipt;
import com.cafehaland.pojo.ReceiptDetail;
import com.cafehaland.pojo.Supplier;
import com.cafehaland.pojo.User;
import com.cafehaland.users.service.ReceiptService;

@Controller
public class ReceiptController {
	@Autowired
	private ReceiptService service;
	
	@GetMapping("/receipts")
	public String viewListReceipt(Model model) {
		List<Receipt> listReceipts = service.listAll();
		model.addAttribute("listReceipts", listReceipts);
		
		return "/warehouse/receipts";
	}
	
	@GetMapping("/receipts/new")
	public String viewAddReceipt(Model model) {
		List<Supplier> listSuppliers = service.listSuppler();
		model.addAttribute("listSuppliers", listSuppliers);
		model.addAttribute("receipt", new Receipt());
		return "/Warehouse/receipts_form";
	}
	
	@PostMapping("/receipts/save")
	public String saveReceipt(Receipt receipt, @RequestParam("quantities")String[] quantities,
			@RequestParam("igredients")String[] igredients,
			@RequestParam("prices")String[] prices,
			@AuthenticationPrincipal AccountDetail accountDetail) {
		for (int i = 0; i < igredients.length; i++) {
			Integer ingredientId = Integer.parseInt(igredients[i]);
			double quantity = Double.parseDouble(quantities[i]);
			double price = Double.parseDouble(prices[i]);
			
			Ingredient ingredient = new Ingredient(ingredientId);
			ReceiptDetail receiptDetail = new ReceiptDetail(quantity, price, receipt, ingredient);
			receipt.getReceiptDetails().add(receiptDetail);
		}
		
		receipt.setUser(new User(accountDetail.getIduser()));
		receipt.setDate(new Date());
		receipt.setStatus("Received");
		
		service.save(receipt);
		
		return "redirect:/receipts";
	}
	
	@GetMapping("/receipts/edit/{id}")
	public String editReceipt(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes redirectAttributes){
		try {
			Receipt receipt = service.get(id);
			model.addAttribute("receipt", receipt);
			
			List<Supplier> listSuppliers = service.listSuppler();
			model.addAttribute("listSuppliers", listSuppliers);
			
			return "/Warehouse/receipts_form";
		} catch (ReceiptNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/receipts";
		}
	}
	
	@GetMapping("/receipts/detail/{id}")
	public String detailReceipt(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes redirectAttributes){
		try {
			Receipt receiptInDb = service.get(id);
			double totalPrice = 0;
			for(ReceiptDetail receiptDetail : receiptInDb.getReceiptDetails()) {
				totalPrice += receiptDetail.getPrice();
			}
			model.addAttribute("totalPrice", totalPrice);
			
			ReceiptForm receiptForm = ReceiptForm.changeReceiptToReceiptForm(receiptInDb);
			model.addAttribute("receiptForm", receiptForm);
			
			model.addAttribute("titlePage", "Deatail Receipt");
			
			return "/warehouse/receipts_form_detail";
		} catch (ReceiptNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "/warehouse/receipts_form_detail";
		}
	}
	
	@GetMapping("/receipts/canncel/{id}")
	public String canncelReceipt(@PathVariable(name = "id")Integer id, Model model, 
			RedirectAttributes redirectAttributes) {
		try {
			Receipt receiptInDb = service.get(id);
			if (!receiptInDb.isCanncel()&&!receiptInDb.isReceived()) {
				service.updateStatus(id, "Canncel");
				redirectAttributes.addFlashAttribute("message", "Cancelled order with ID successfully");
			} else {
				redirectAttributes.addFlashAttribute("message", "Order has been received or cancelled");
			}
		} catch (ReceiptNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/receipts";
	}
	
	@GetMapping("/receipts/delete/{id}")
	public String deleteReceipt(@PathVariable(name = "id")Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Receipt receiptInDb = service.get(id);
			if (!receiptInDb.isReceived()) {
				service.deleteRecipt(id);
				redirectAttributes.addFlashAttribute("message", "Delete order with ID(" + id +") successfully");
			} else {
				redirectAttributes.addFlashAttribute("message", "Order has been received");
			}
		} catch (ReceiptNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/receipts";
	}
	
}
