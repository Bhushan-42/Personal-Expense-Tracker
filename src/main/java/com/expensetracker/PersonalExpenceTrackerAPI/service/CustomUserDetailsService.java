package com.expensetracker.PersonalExpenceTrackerAPI.service;

import com.expensetracker.PersonalExpenceTrackerAPI.DAO.User;
import com.expensetracker.PersonalExpenceTrackerAPI.DAO.UserPrincipal;
import com.expensetracker.PersonalExpenceTrackerAPI.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
      User user = userRepository.findByUserName(userName);

        if(user == null) throw new UsernameNotFoundException("User : 404" + userName);

        return new UserPrincipal(user);
    }
}
