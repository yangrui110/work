package com.lvshou.magic.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.lvshou.magic.verification.entity.Verification;
import com.lvshou.magic.verification.service.VerificationService;

@Component
public class MyUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private VerificationService verificationService;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("username="+username);
		Verification verification=verificationService.findByAccount(username);
		Collection<? extends GrantedAuthority> list=verification.getAuthorities();
		return new User(verification.getAccount(),verification.getPassword(),verification.getAuthorities());
	}
}
