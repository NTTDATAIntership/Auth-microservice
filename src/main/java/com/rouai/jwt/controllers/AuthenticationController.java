package com.rouai.jwt.controllers;

import com.rouai.jwt.models.ApplicationUser;
import com.rouai.jwt.dtos.AuthenticationRequest;
import com.rouai.jwt.dtos.LoginResponseDto;
import com.rouai.jwt.dtos.RegistrationDTO;
import com.rouai.jwt.services.AuthenticatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticatioService authenticationService;
    @PostMapping("/register")

    public ApplicationUser registerUser(@RequestBody RegistrationDTO body){
        return authenticationService.registerUser(body.getUsername(), body.getPassword());
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody AuthenticationRequest body){
        return ResponseEntity.ok(authenticationService.loginUser(body));
    }
}
