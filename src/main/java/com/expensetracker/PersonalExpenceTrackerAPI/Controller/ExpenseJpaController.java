package com.expensetracker.PersonalExpenceTrackerAPI.Controller;

import com.expensetracker.PersonalExpenceTrackerAPI.DAO.Expense;
import com.expensetracker.PersonalExpenceTrackerAPI.DAO.User;
import com.expensetracker.PersonalExpenceTrackerAPI.jpa.ExpenseRepository;
import com.expensetracker.PersonalExpenceTrackerAPI.jpa.UserRepository;
import com.expensetracker.PersonalExpenceTrackerAPI.service.JWTService;
import com.expensetracker.PersonalExpenceTrackerAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
//@AllArgsConstructor
public class ExpenseJpaController {
    private UserRepository userRepository;
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    public ExpenseJpaController(UserRepository userRepository, ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user){
        return userService.saveUser(user);
    }

    @PostMapping("/api/login")
    public String login(@RequestBody User user){


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));

        if(authentication.isAuthenticated()) return jwtService.generateToken(user.getUserName());
        else return "login fail";

    }

    @GetMapping("/api/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/api/users/{id}")
    public User retrieveUserById(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new UserNotFoundException("id: "+ id);

        return user.get();
        }

    @DeleteMapping("/api/users/{id}")
    public void deleteByIdUsers(@PathVariable Long id) throws UserPrincipalNotFoundException {
        userRepository.deleteById(id);
    }

    @PostMapping(value = "/api/users",consumes = "application/json")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = userService.saveUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/api/users/{id}/expenses")
    public List<Expense> retrievePostsForUser(@PathVariable Long id) throws UserPrincipalNotFoundException {
        Optional<User> user= userRepository.findById(id);
        if(user.isEmpty()) throw new UserNotFoundException("id: " + id);
        return user.get().getExpense();
    }

    @PostMapping("/api/users/{id}/expenses")
    public ResponseEntity<Object> createPostForUser(@PathVariable Long id, @Valid @RequestBody Expense expense) throws UserPrincipalNotFoundException {
        Optional<User> user= userRepository.findById(id);
        if(user.isEmpty()) throw new UserNotFoundException("id: " + id);
        expense.setUser(user.get());
        Expense savedExpense = expenseRepository.save(expense);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedExpense.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
