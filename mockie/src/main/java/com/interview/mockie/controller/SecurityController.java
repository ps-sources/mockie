package com.interview.mockie.controller;

import com.interview.mockie.dto.UserDetailDTO;
import com.interview.mockie.models.AuthenticationRequest;
import com.interview.mockie.models.AuthenticationResponse;
import com.interview.mockie.models.Role;
import com.interview.mockie.service.MyUserDetailsService;
import com.interview.mockie.util.JWTUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class SecurityController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Autowired
    JWTUtility jwtUtils;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     Everytime any mapping occurs,spring security will not allow before it is really gets authenticated.
     Thus, to make this endpoint authenticate we need to permit it through SpringSecurityConfig
    */
    @PostMapping("/authenticate")
    public String createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body("Incorrect username or password!").toString();
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails);
        final String role = jwtUtils.extractRole(jwt);
        if ("USER".equals(role)) return "redirect:/user/dashboard";
        else if ("ALUMNI".equals(role)) return "redirect:/alumni/dashboard";
        else if ("ADMIN".equals(role)) return "redirect:/admin/dashboard";
        else return ResponseEntity.badRequest().body("You are not authorized !").toString();
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserDetailDTO userDetailDTO){
        UserDetailDTO registeredUser = myUserDetailsService.register(userDetailDTO, Role.USER);
        if(Objects.nonNull(registeredUser))
            return ResponseEntity.ok().body("User created successfully with username: " + registeredUser.getUserCredDetails().getUsername());
        else
            return ResponseEntity.badRequest().body("Not able to register user at this moment. Please try again after some time!");
    }

    @PostMapping("/register-alumni")
    public ResponseEntity<Object> registerAlumni(@RequestBody  UserDetailDTO userDetailDTO){
        UserDetailDTO registeredUser = myUserDetailsService.register(userDetailDTO, Role.ALUMNI);
        if(Objects.nonNull(registeredUser))
            return ResponseEntity.ok().body("User created successfully with username: " + registeredUser.getUserCredDetails().getUsername());
        else
            return ResponseEntity.badRequest().body("Not able to register user at this moment. Please try again after some time!");
    }

}
