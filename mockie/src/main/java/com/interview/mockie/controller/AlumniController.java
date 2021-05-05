package com.interview.mockie.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alumni")
public class AlumniController {

    @GetMapping("/dashboard")
    public String alumniDashboard(){
        return "Welcome Alumni";
    }
}
