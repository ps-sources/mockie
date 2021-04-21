package com.interview.mockie.service;

import com.interview.mockie.converter.UserDetailsConverter;
import com.interview.mockie.dto.UserDetailDTO;
import com.interview.mockie.models.Role;
import com.interview.mockie.models.UserCredDetails;
import com.interview.mockie.models.UserDetail;
import com.interview.mockie.repository.UserCredentialRepository;
import com.interview.mockie.repository.UserDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    private UserCredentialRepository userCredentialRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserDetailsConverter userDetailsConverter;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserCredDetails ut = userCredentialRepository.getUserCredDetailsByUsername(s);
        log.info("@@@@@@ loadUsersByUsername retrieved username:{} ,pass={} ", ut.getUsername(), ut.getPassword());
        return new User(ut.getUsername(), ut.getPassword(), new ArrayDeque<>());
    }

    public UserDetailDTO register(UserDetailDTO dto, Role role) {
        log.info("@@@@ Registering user with Role: {}", role);
        UserDetail userDetail = userDetailsConverter.from(dto);
        userDetail.getUserCredDetails().setRoles(role);
        userDetail.getUserCredDetails().setPassword(encodedPassword(userDetail.getUserCredDetails().getPassword()));
        try {
            return userDetailsConverter.to(userDetailRepository.save(userDetail));
        } catch (Exception e) {
            log.error("Error occurred while registering user: {} ", e.getMessage());
            return null;
        }
    }

    private String encodedPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

}
