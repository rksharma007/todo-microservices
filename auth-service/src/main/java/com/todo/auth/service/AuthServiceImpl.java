package com.todo.auth.service;

import org.springframework.stereotype.Service;
import com.todo.auth.repository.UserRepository;
import com.todo.auth.model.User;
import com.todo.auth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
        return "User registered successfully";
    }

    @Override
    public String login(User user) {
        Optional<User> u = repo.findByUsername(user.getUsername());
        if (u.isPresent() && passwordEncoder.matches(user.getPassword(), u.get().getPassword())) {
            return jwtUtil.generateToken(user.getUsername());
        }
        throw new RuntimeException("Invalid credentials");
    }
}
