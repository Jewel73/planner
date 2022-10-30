package com.jtech.planner;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jtech.planner.model.Role;
import com.jtech.planner.model.User;
import com.jtech.planner.service.RoleService;
import com.jtech.planner.service.UserService;

@Configuration
@SpringBootApplication
public class PlannerApplication implements CommandLineRunner{

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    public static void main(String[] args) {
        SpringApplication.run(PlannerApplication.class, args);
    }


	@Override
	public void run(String... args) throws Exception {
		roleService.saveRole(new Role("ROLE_USER"));
        roleService.saveRole(new Role("ROLE_MANAGER"));
        roleService.saveRole(new Role("ROLE_ADMIN"));

        userService.saveUser(new User("Jewel", "rana", "jewel", "sd", new ArrayList<>()));
        userService.saveUser(new User("Sohel", "rana", "sohel", "sd", new ArrayList<>()));
        userService.saveUser(new User("Rohim", "rana", "rohim", "sd", new ArrayList<>()));
       
		userService.addRoleToUser("jewel", "ROLE_ADMIN");
        userService.addRoleToUser("sohel", "ROLE_USER");
	}


    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
