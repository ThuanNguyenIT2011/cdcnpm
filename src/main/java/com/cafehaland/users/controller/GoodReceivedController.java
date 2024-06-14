package com.cafehaland.users.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cafehaland.Dao.ReceiptForm;
import com.cafehaland.Security.AccountDetail;
import com.cafehaland.pojo.GoodReceived;
import com.cafehaland.pojo.Receipt;
import com.cafehaland.pojo.User;
import com.cafehaland.users.service.GoodReceivedService;

@Controller
public class GoodReceivedController {
	@Autowired
	private GoodReceivedService service;
	
	@GetMapping("/goodreceiveds")
	public String viewList(Model model) {
		List<GoodReceived> listGoodReceiveds = service.listAll();
		model.addAttribute("listGoodReceiveds", listGoodReceiveds);
		return "/warehouse/good_receiveds";
	}
	
	@GetMapping("/goodreceiveds/new")
	public String addGoodReceived(Model model, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "Please select the purchase order for the items to be imported");
		return "redirect:/receipts";
	}
	
	@PostMapping("/goodreceived/order/save")
	public String saveGoodReceived(ReceiptForm receiptForm, Model model, @AuthenticationPrincipal AccountDetail logged) {
		GoodReceived goodReceived = new GoodReceived();
		goodReceived.setDate(new Date());
		goodReceived.setUser(new User(logged.getIduser()));
		goodReceived.setReceipt(new Receipt(receiptForm.getId()));
		
		Receipt receipt = service.getReceipt(receiptForm.getId());
		receipt.getReceiptDetails().forEach(
					t -> {
						service.updateStock(t.getIngredient().getId(), t.getQuantity());
					}
				);
		
		service.save(goodReceived);
		service.updateStatus(receiptForm.getId(), "Received");
		model.addAttribute("message", "Successfully imported the raw materials");
		return "redirect:/goodreceiveds";
	}
}
