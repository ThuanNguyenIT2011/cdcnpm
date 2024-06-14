package com.cafehaland.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cafehaland.pojo.Account;
import com.cafehaland.users.repository.AccountRepository;

public class AccountDetailService implements UserDetailsService{
	@Autowired
	AccountRepository accoutReop;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			Account account = accoutReop.findById(email).get();
			return new AccountDetail(account);
		} catch (Exception e) {
			throw new UsernameNotFoundException(email);
		}
	}
	
}
