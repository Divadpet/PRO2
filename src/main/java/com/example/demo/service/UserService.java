package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
public interface UserService extends UserDetailsService{
    User saveUser(User user);
}
