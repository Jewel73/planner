package com.jtech.planner.service;

import com.jtech.planner.model.Role;
import com.jtech.planner.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService{
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public Role getRole(Long id) {
        return roleRepo.findById(id).get();
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepo.findByName(roleName);
    }

    @Override
    public void deleteRole(Role role) {
        roleRepo.delete(role);
    }
}
