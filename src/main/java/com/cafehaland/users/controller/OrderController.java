package com.cafehaland.users.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cafehaland.pojo.Order;
import com.cafehaland.pojo.OrderDetail;
import com.cafehaland.users.repository.IngredientRepository;
import com.cafehaland.users.repository.OrderDetailRepository;
import com.cafehaland.users.service.OrderService;

@Controller
public class OrderController {
	@Autowired
	private OrderService service;
	
	@GetMapping("/orders")
	public String viewPage(Model model) {
		model.addAttribute("listProducts", service.listProduct());
		return "/orders/add-order";
	}
	
	@GetMapping("/bartendings")
	public String viewOrders(Model model) {
		model.addAttribute("listOrders", service.listAll());
		return "/orders/orders";
	}
	
	@GetMapping("/bartendings/detail/{id}")
	public String viewDetailOrder(@PathVariable("id")Integer id, Model model) {
		model.addAttribute("listOrderDetails", service.getOrderByOrderId(id));
		model.addAttribute("orderId", id);
		return "/orders/orders_detail";
	}
	
	@GetMapping("/bartendings/success/{id}")
	public String successOrder(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		service.updateStatusById(id, "Success");
		List<OrderDetail> orderDetails = service.getOrderByOrderId(id);
		orderDetails.forEach(
					orderDetail -> {
						double quantityOrder = orderDetail.getQuntity();
						orderDetail.getProductSize().getSyntaxs().forEach(
									syn -> {
										service.updateIngredient(syn.getIngredient().getId(), quantityOrder * syn.getQuantity());
									}
								);
					}
				);
		redirectAttributes.addFlashAttribute("message", "Your order with ID has been completed successfully.");
		return "redirect:/bartendings";
	}
}
