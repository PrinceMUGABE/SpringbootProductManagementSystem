package com.auca.serviceImplementation;

import com.auca.modal.User;
import com.auca.repository.UserRepository;
import com.auca.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepository repo;
    @Override
    public User saveUser(User user) {
        return repo.save(user);
    }

    @Override
    public List<User> displayUsers()
    {
        return repo.findAll();
    }

    @Override
    public User findUserById(Long user) throws UserNotFoundException {
        Optional<User> savedUser = repo.findById(user);
        if (savedUser.isPresent()){
            return savedUser.get();
        }
        throw new UserNotFoundException("Could Not find any User" + user);
    }

    @Override
    public User updateUser(Long user) {
        return null;
    }

    @Override
    public void deleteUser(Long user) throws UserNotFoundException {
        Long count = repo.countById(user);
        if (count == null || count == 0){
            throw new UserNotFoundException("Could not find user with ID" + user);
        }
        repo.deleteById(user);
    }

    @Override
    public User updateUser(User user) {
        // Update user information
        if (user.getId() != null) {
            // Check if the user with the given ID exists in the database
            Optional<User> existingUserOptional = repo.findById(user.getId());

            if (existingUserOptional.isPresent()) {
                User existingUser = existingUserOptional.get();
                // Update user information with the new values
                existingUser.setUsername(user.getUsername());
                existingUser.setEmail(user.getEmail());
                existingUser.setPassword(user.getPassword()); // You may want to handle password updates more securely

                // Save the updated user
                return repo.save(existingUser);
            }
        }
        return null; // Handle the case where the user with the given ID is not found
    }



    @Override
    public User findUserByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public boolean usernameExists(String username) {
        return repo.existsByUsername(username);
    }
}
