package com.interview.mockie.controller;

import com.interview.mockie.models.*;
import com.interview.mockie.service.MyUserDetailsService;
import com.interview.mockie.util.JWTUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            log.info("@@@@ Inside authenticate:: uname=" + authenticationRequest.getUsername() + "\tpass=" + authenticationRequest.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body("Incorrect username or password!");
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));   //return 200 OK
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDetail userDetail){
        log.info("@@@@@ inside register-user: ");
        userDetail.getUserCredDetails().setRoles(Role.USER);
        UserDetail registeredUser = myUserDetailsService.register(userDetail);
        if(Objects.nonNull(registeredUser))
            return ResponseEntity.ok().body("User created successfully with username: " + registeredUser.getUserCredDetails().getUsername());
        else
            return ResponseEntity.badRequest().body("Not able to register user at this moment. Please try again after some time!");
    }

    @PostMapping("/register-alumni")
    public ResponseEntity<?> registerAlumni(@RequestBody  UserDetail userDetail){
        userDetail.getUserCredDetails().setRoles(Role.ALUMNI);
        UserDetail registeredUser = myUserDetailsService.register(userDetail);
        if(Objects.nonNull(registeredUser))
            return ResponseEntity.ok().body("User created successfully with username: " + registeredUser.getUserCredDetails().getUsername());
        else
            return ResponseEntity.badRequest().body("Not able to register user at this moment. Please try again after some time!");
    }

}
