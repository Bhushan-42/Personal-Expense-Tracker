package com.expensetracker.PersonalExpenceTrackerAPI.service;

import com.expensetracker.PersonalExpenceTrackerAPI.DAO.User;
import com.expensetracker.PersonalExpenceTrackerAPI.config.SecurityConfig;
import com.expensetracker.PersonalExpenceTrackerAPI.jpa.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private SecurityConfig securityConfig;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User saveUser(User user){
        user.setPassword(encoder.encode(user.getPassword())); // This line converts text to encryption and save to the database -> Enecrypt
        return userRepository.save(user);
    }
}
