package com.interview.mockie.service;

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
    
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("@@@@@@ loadUsersByUsername called");
        UserCredDetails ut = userCredentialRepository.getUserCredDetailsByUsername(s);
        log.info("@@@@@@ loadUsersByUsername retrieved username:{} ,pass={} ", ut.getUsername(), ut.getPassword());
        return new User(ut.getUsername(), ut.getPassword(), new ArrayDeque<>());
    }

    public UserDetail register(UserDetail userDetail) {
        log.info("@@@@ Inside the register main() --> ");
        userDetail.getUserCredDetails().setPassword(encodedPassword(userDetail.getUserCredDetails().getPassword()));
        try {
            return userDetailRepository.save(userDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String encodedPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
