package com.kantor.kantor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kantor.kantor.model.User;
import com.kantor.kantor.repository.UserRepository;

@Service
public class UserService {
    
private final UserRepository userRepository;
private final BCryptPasswordEncoder  passwordEncoder;

@Autowired
public UserService(UserRepository userRepository){
    this.userRepository = userRepository;
    this.passwordEncoder = new BCryptPasswordEncoder();
}

public User registerUser(User user){
    if (userRepository.existsByUsername(user.getUsername())) {
        throw new RuntimeException("Użytkownik o tej nazwie juz istnieje");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    if (user.getRole() == null || user.getRole().isEmpty()) {
        user.setRole("USER");
    }

    return userRepository.save(user);
}

public Optional<User> findByUsername(String username){
    return userRepository.findByUsername(username);
}

public List<User> getAllUsers(){
    return userRepository.findAll();
}

public boolean isPasswordValid(User user, String rawPassword){
    return passwordEncoder.matches(rawPassword, user.getPassword());
}

}
