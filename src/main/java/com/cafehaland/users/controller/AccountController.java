package com.cafehaland.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cafehaland.exception.UserNotException;
import com.cafehaland.pojo.Account;
import com.cafehaland.users.service.AccountService;

@Controller
public class AccountController {
	@Autowired
	AccountService service;
	
	@GetMapping("/staffs/{email}/enabled/{status}")
	public String editEnabled(@PathVariable(name="email")String email, 
			@PathVariable(name="status") boolean status, RedirectAttributes redirectAttributes) {
		Account account;
		try {
			account = service.getUserByEmail(email);
			service.updateEnabled(email, status);
			
			String strStatus = status ? "Enabled" : "Disabled";
			redirectAttributes.addFlashAttribute("message", "The user id " + account.getUser().getId() + " has been " + strStatus);
			
		} catch (UserNotException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return "redirect:/staffs";
	}
}
