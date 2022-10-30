package com.jtech.planner.config;
import com.jtech.planner.model.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.jtech.planner.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserDetailsServiceImp implements UserDetailsService{

    @Autowired
    private UserService userService;

  
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
        User user = userService.getUserByUserName(username);

        if(user == null){
            log.error("User not found............");
            throw new UsernameNotFoundException("User not found in the database");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);

		
	}
    
}
