package com.example.library.service;

import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User>getAllUsers(){
        return userRepository.findAll();
    }
    public Optional<User> getUserById(int id){
        return userRepository.findById(id);
    }
    public User registerUser(User user){
        user.setRole(User.Role.USER);
        return userRepository.save(user);
    }

    public Optional<User>login(String email , String password){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()&& user.get().getPassword().equals(password)){
            return user;
        }
        return Optional.empty();
    }

    public void deleteUser(int id){
        userRepository.deleteById(id);

    }
}
