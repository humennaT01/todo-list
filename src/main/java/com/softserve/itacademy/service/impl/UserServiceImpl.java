package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.UserRepository;


import com.softserve.itacademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User.UserBuilder;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;


    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public User create(User user) {
        try {
            return userRepository.save(user);
        } catch (IllegalArgumentException e) {
            throw new NullEntityReferenceException("User cannot be 'null'");
        }
    }

    @Override
    public User readById(long id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new EntityNotFoundException("User with id " + id + " not found");
    }

    @Override
    public User update(User user) {
        if (user != null) {
            User oldUser = readById(user.getId());
            if (oldUser != null) {
                return userRepository.save(user);
            }
        }
        throw new NullEntityReferenceException("User cannot be 'null'");
    }

    @Override
    public void delete(long id) {
        User user = readById(id);
        if (user != null) {
            userRepository.delete(user);
        } else {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users.isEmpty() ? new ArrayList<>() : users;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

}