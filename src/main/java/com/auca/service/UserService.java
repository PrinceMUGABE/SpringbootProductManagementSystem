package com.auca.service;

import com.auca.modal.User;
import com.auca.serviceImplementation.UserNotFoundException;

import java.util.List;


public interface UserService {
    User saveUser(User user);

    User updateUser(User user);


    User findUserByUsername(String username);
    boolean usernameExists(String username);
    List<User> displayUsers();
    User findUserById(Long user) throws UserNotFoundException;
    User updateUser(Long user);
    void deleteUser(Long user) throws UserNotFoundException;

}
