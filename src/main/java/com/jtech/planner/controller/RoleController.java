package com.jtech.planner.controller;

import com.jtech.planner.model.Role;
import com.jtech.planner.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/add")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/addrole").toString());
        return ResponseEntity.created(uri).body(roleService.saveRole(role));
    }

    @GetMapping("/roles")
    public  ResponseEntity<List<Role>> getRoles(){
        return ResponseEntity.ok().body(roleService.getAllRoles());
    }
}
