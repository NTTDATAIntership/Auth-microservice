package com.rouai.jwt.services;

import com.rouai.jwt.models.ApplicationUser;
import com.rouai.jwt.dtos.AuthenticationRequest;
import com.rouai.jwt.dtos.LoginResponseDto;
import com.rouai.jwt.models.Role;
import com.rouai.jwt.repository.RoleRepository;
import com.rouai.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticatioService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public ApplicationUser registerUser(String username, String password){
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        return userRepository.save(new ApplicationUser(0, username, encodedPassword, authorities));
    }

public LoginResponseDto loginUser(AuthenticationRequest authenticationRequest){

    try{

                authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword())
        );
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow();
        String token = jwtService.generateToken(user);

        return new LoginResponseDto(userRepository.findByUsername(authenticationRequest.getUsername()).get(), token);

    } catch(AuthenticationException e){
        return
                new LoginResponseDto(null, "");
    }
}
}
