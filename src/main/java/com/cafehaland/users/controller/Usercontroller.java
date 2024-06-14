package com.cafehaland.users.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cafehaland.exception.UserNotException;
import com.cafehaland.pojo.Account;
import com.cafehaland.pojo.Role;
import com.cafehaland.pojo.User;
import com.cafehaland.uploadfile.UploadFileUntil;
import com.cafehaland.users.service.UserService;
import com.fasterxml.jackson.core.sym.Name;


@Controller
public class Usercontroller {
	@Autowired
	private UserService service;
	
	@GetMapping("/staffs")
	public String viewPageUser(Model model) {
		return listPage(1, model, "firstName", "asc", "");
	}
	
	@GetMapping("/staffs/page/{pageNum}")
	public String listPage (@PathVariable("pageNum") int pageNum, Model model,
			@Param("sortField")String sortField, @Param("sortDir")String sortDir,
			@Param("keyword")String keyword) {
		Page<User> page = service.listPage(pageNum, sortField, sortDir, keyword);
		
		List<User> listUsers = page.getContent();
		model.addAttribute("listUsers", listUsers);
		
		int startCount = (pageNum - 1)*service.PAGE_PER_NUM + 1;
		int endCount = startCount + service.PAGE_PER_NUM - 1;
		if (endCount > page.getTotalElements()) {
			endCount = (int)page.getTotalElements();
		}
		
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		
		model.addAttribute("keyword", keyword);
		
		String revertSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("revertSortDir", revertSortDir);
		
		return "/users/users";
	}
	
	@GetMapping("/staffs/new")
	public String newUser(Model model) {
		User user = new User();
		Account account = new Account();
		account.setEnabled(true);
		user.setAccount(account);
		
		List<Role> listRoles = service.listRoles();
		
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		
		model.addAttribute("titlePage", "Add Staff");
		
		return "/users/user_form";
	}
	
	@PostMapping("/staffs/save")
	public String saveStaff(User user, RedirectAttributes redirectAttributes,
			@RequestParam("image")MultipartFile multipartFile) throws IOException {
		if (!multipartFile.isEmpty()) {
			String nameFile = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(nameFile);
			
			User saveUser = service.save(user);
			
			String uploadDir = "user-photo/" + saveUser.getId();
			
			UploadFileUntil.clearDir(uploadDir);
			
			UploadFileUntil.saveFile(uploadDir, nameFile, multipartFile);
			
		} else {
			service.save(user);
		}
		redirectAttributes.addFlashAttribute("message", "The staff has been saved successfully");
		return "redirect:/staffs";
	}
	
	@GetMapping("/staffs/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			service.delete(id);
			redirectAttributes.addFlashAttribute("message", "The staff id " + id + " has been deleted successfully");
		} catch (UserNotException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/staffs";
	}
	
	@GetMapping("/staffs/edit/{id}")
	public String editUsers(@PathVariable(name="id")Integer id,
			Model model, RedirectAttributes redirectAttributes) {
		try {
			User userInDb = service.get(id);
			List<Role> listRoles = service.listRoles();
			
			model.addAttribute("user", userInDb);
			model.addAttribute("listRoles", listRoles);
			
			model.addAttribute("titlePage", "Edit Staff (ID: " + userInDb.getId() + ")");
			
			return "/users/user_form";
		} catch (UserNotException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/staffs";
		}
	}
	
}
