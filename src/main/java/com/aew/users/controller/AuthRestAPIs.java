package com.aew.users.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import com.aew.users.config.jwt.JwtProvider;
import com.aew.users.domain.Authority;
import com.aew.users.domain.RoleName;
import com.aew.users.domain.User;
import com.aew.users.messages.request.LoginForm;
import com.aew.users.messages.request.SignUpForm;
import com.aew.users.messages.response.JwtResponse;
import com.aew.users.repository.RoleRepository;
import com.aew.users.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {
 
    @Autowired
    AuthenticationManager authenticationManager;
 
    @Autowired
    UserRepository userRepository;
 
    @Autowired
    RoleRepository roleRepository;
 
    @Autowired
    PasswordEncoder encoder;
 
    @Autowired
    JwtProvider jwtProvider;
 
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
 
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
 
        SecurityContextHolder.getContext().setAuthentication(authentication);
 
        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
 
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if(userRepository.existsByLogin(signUpRequest.getUsername())) {
            return new ResponseEntity<String>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }
 
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<String>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }
 
        // Creating user's account
        User user = User.builder().login(signUpRequest.getUsername()).email( signUpRequest.getEmail()).
            password(encoder.encode(signUpRequest.getPassword())).firstName(signUpRequest.getFirstName()).lastName(signUpRequest.getLastName()).activated(true).build();
        
        Set<String> strRoles = signUpRequest.getRole();
        Set<Authority> roles = new HashSet<>();
 
        strRoles.forEach(role -> {
          switch(role) {
          case "ROLE_ADMIN":
            Authority adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                  .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
            roles.add(adminRole);
            
            break;
          case "ROLE_MOD":
          Authority pmRole = roleRepository.findByName(RoleName.ROLE_MOD)
                  .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                roles.add(pmRole);
                
            break;
          default:
          Authority userRole = roleRepository.findByName(RoleName.ROLE_USER)
                  .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
              roles.add(userRole);              
          }
        });
        
        user.setAuthorities(roles);
        userRepository.save(user);
 
        return ResponseEntity.ok().body("User registered successfully!");
    }
}