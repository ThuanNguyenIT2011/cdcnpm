package com.cafehaland.users.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafehaland.exception.UserNotException;
import com.cafehaland.pojo.Account;
import com.cafehaland.users.repository.AccountRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AccountService {
	@Autowired
	private AccountRepository accountRepo;
	
	public boolean isUniqueEmail(Integer idUser, String email) {
		Account account = null;
		try {
			account = accountRepo.findById(email).get();
		} catch (NoSuchElementException e) {
			account = null;
		}
		
		if (account == null) {
			return true;
		}
		
		boolean isCreateUser = idUser == null;
		
		if (isCreateUser) {
			if (account != null) return false;
		} else {
			if (account.getUser().getId() != idUser) {
				return false;
			}
		}
		
		return  true;
	}
	
	public void updateEnabled(String email, boolean status) {
		accountRepo.updateEnabledStatus(email, status);
	}
	
	public Account getUserByEmail(String email) throws UserNotException {
		try {
			Account account = accountRepo.findById(email).get();
			return account;
		} catch (Exception e) {
			throw new UserNotException("Could not find any user with email " + email);
		}
	}
}
