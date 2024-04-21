package com.microservice.identity.service;

import com.microservice.identity.entity.Role;
import com.microservice.identity.entity.User;
import com.microservice.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository
                .findByEmail(username)
                .orElse(null);
    }

    public User getUserById(int id){
        return repository.findById(id).orElse(null);
    }


    public boolean userExists(String email) {
        return repository
                .findByEmail(email)
                .isPresent();
    }

    public User addUser(User user) {
        return repository.save(user);
    }

    public boolean adminExists() {
        return repository
                .findByRole(Role.Admin)
                .isPresent();
    }

    public boolean isAdmin(User user){
        return user.getRole()==Role.Admin;
    }


}
