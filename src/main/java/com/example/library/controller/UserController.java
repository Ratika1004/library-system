package com.example.library.controller;


import com.example.library.model.User;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    //getALLUsers
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    //register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        try{
            User saved = userService.registerUser(user);
            return ResponseEntity.ok(saved);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Email already exists");
        }
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        System.out.println("Login attempt - Email: " + email + " Password: " + password); // debug

        Optional<User> user = userService.login(email, password);

        System.out.println("User found: " + user.isPresent()); // debug

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }

    //delete a user(admin use)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted");
    }
}
