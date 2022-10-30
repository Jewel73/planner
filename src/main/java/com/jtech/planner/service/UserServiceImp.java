package com.jtech.planner.service;

import com.jtech.planner.model.Role;
import com.jtech.planner.model.User;
import com.jtech.planner.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleService roleService;

    @Lazy
    @Autowired
    public PasswordEncoder bCryptPasswordEncoder;

    @Override
    public User saveUser(User user) {
        log.info("Saving user {} to the db", user.getUserName());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public User getUser(Long id) {
        return userRepo.findById(id).get();
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Getting all db users...");
        return userRepo.findAll();
    }

    @Override
    public User getUserByUserName(String userName) {
        log.info("Getting user {} from db.", userName);
        return userRepo.findByUserName(userName).get(0);
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("Deleting user Id: {}", userId);
        userRepo.deleteById(userId);
    }

    
    @Override
    public void addRoleToUser(String userName, String roleName) {
        log.info("Adding role {} to user {} ", roleName, userName);

        User user = userRepo.findByUserName(userName).get(0);
        Role role = roleService.getRoleByName(roleName);
        user.getRoles().add(role);
        userRepo.save(user);
    }
}

