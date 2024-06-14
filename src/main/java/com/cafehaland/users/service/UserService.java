package com.cafehaland.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafehaland.exception.UserNotException;
import com.cafehaland.pojo.Role;
import com.cafehaland.pojo.User;
import com.cafehaland.users.repository.RoleRepository;
import com.cafehaland.users.repository.UserRepository;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static final int PAGE_PER_NUM = 4;

	public List<User> listAll() {
		List<User> listUsers = userRepo.findAll();
		return listUsers;
	}
	
	public int getLengthStaff() {
		return userRepo.findAll().size();
	}
	
	public Page<User>listPage(int pageNum, String sortField, String sortDir, String keyword){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		PageRequest pageRequest = PageRequest.of(pageNum - 1, PAGE_PER_NUM, sort);
		
		return userRepo.findAll(keyword, pageRequest);
	}

	public List<Role> listRoles() {
		List<Role> roles = roleRepo.findAll();
		return roles;
	}

	public User save(User user) {
		boolean isUpdate = user.getId() != null;
		
		if (isUpdate) {
			User userInDb = userRepo.findById(user.getId()).get();
			if (user.getAccount().getPassword().isEmpty()) {
				user.getAccount().setPassword(userInDb.getAccount().getPassword());
			} else {
				encodePassword(user);
			}
		} else {
			encodePassword(user);
		}
		
		return userRepo.save(user);
	}
	
	private void encodePassword(User user) {
		String passEncode = passwordEncoder.encode(user.getAccount().getPassword());		
		user.getAccount().setPassword(passEncode);
	}
	
	public void delete(Integer id) throws UserNotException {
		Long countUser = userRepo.countById(id);
		
		if (countUser == 0 || countUser == null) {
			throw new UserNotException("Could not find any user with ID " + id);
		} else {
			if (userRepo.existsById(id)) {
				throw new UserNotException("Cannot delete Staff ID " + id);
			}
			userRepo.deleteById(id);
		}
	}
	
	public User get(Integer id) throws UserNotException {
		try {
			return userRepo.findById(id).get();
		} catch (Exception e) {
			throw new UserNotException("Could not find any user with ID " + id);
		}
	}
}
