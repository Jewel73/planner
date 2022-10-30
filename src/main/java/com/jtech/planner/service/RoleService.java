package com.jtech.planner.service;

import com.jtech.planner.model.Role;

import java.util.List;

public interface RoleService {

    Role saveRole(Role role);
    Role getRole(Long id);
    List<Role> getAllRoles();
    Role getRoleByName(String RoleName);
    void deleteRole(Role roleName);
}
