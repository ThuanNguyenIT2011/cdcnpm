package com.cafehaland;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("/")
	public String viewPage() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
