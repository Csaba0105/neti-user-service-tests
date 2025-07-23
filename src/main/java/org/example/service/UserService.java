package org.example.service;

import org.example.exception.DataConflictException;
import org.example.exception.ResourceNotFoundException;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new DataConflictException("Username already exists: " + user.getUsername());
        });
        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new DataConflictException("Email already exists: " + user.getEmail());
        });
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);

        userRepository.findByUsername(userDetails.getUsername()).ifPresent(u -> {
            if (!u.getId().equals(id)) {
                throw new DataConflictException("Username already exists: " + userDetails.getUsername());
            }
        });
        userRepository.findByEmail(userDetails.getEmail()).ifPresent(u -> {
            if (!u.getId().equals(id)) {
                throw new DataConflictException("Email already exists: " + userDetails.getEmail());
            }
        });

        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setFullName(userDetails.getFullName());
        user.setActive(userDetails.isActive());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
