package com.jtech.planner.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtech.planner.model.User;
import com.jtech.planner.service.UserService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired


    @RolesAllowed("ROLE_USER")
    @RequestMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @RequestMapping("/add")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<User> createUser(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/add").toString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/addrole")
    @RolesAllowed("ROLE_ADMIN")
    public void addRole(@RequestBody UserToRoleForm userToRoleForm){
        userService.addRoleToUser(userToRoleForm.getUserName(), userToRoleForm.getRoleName());
    }

    @PostMapping("/refresh/token")
    public void getRefreshToken(HttpServletRequest request, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException{
        
        String authHeader = request.getHeader("Authorization");
        
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            try {
                String token = authHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secretKey".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT=  verifier.verify(token);
                String userName = decodedJWT.getSubject();
                User user = userService.getUserByUserName(userName);

                String access_token = JWT.create()
                .withSubject(user.getUserName())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURI().toString())
                .withClaim("roles", user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()))
                .sign(algorithm);

                Map<String, Object> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                    log.error("Error found to verify refreh token: {}", e.getMessage());
                    response.setHeader("error", e.getMessage());
                    Map<String, String> error = new HashMap<>();
                    error.put("error_msg", e.getMessage());
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
            
        }

    }
}

@Data
class UserToRoleForm{
    private String userName;
    private String roleName;
}
