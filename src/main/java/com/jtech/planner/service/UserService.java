package com.jtech.planner.service;

import com.jtech.planner.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    User getUser(Long id);
    List<User> getAllUsers();
    User getUserByUserName(String userName);

    void deleteUser(Long userId);
    void addRoleToUser(String userName, String roleName);

}
