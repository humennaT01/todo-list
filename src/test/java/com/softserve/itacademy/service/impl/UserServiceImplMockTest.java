package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.ModelUtils;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class UserServiceImplMockTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    User user = ModelUtils.getUser();

    @Test
    void createUserTest() {
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User actual = userService.create(user);
        assertEquals(user, actual);
    }

    @Test
    void readByUserIdTest() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User actual = userService.readById(1L);
        assertEquals(user, actual);
    }

    @Test
    void updateUserTest() {
        User updatedUser = user;
        updatedUser.setFirstName("New");
        updatedUser.setLastName("User");
        updatedUser.setEmail("new.user@gmail.com");
        updatedUser.setPassword("pa$$word");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        assertEquals(updatedUser, userService.update(user));
    }

    @Test
    void deleteUserTest() {
        userRepository.save(user);
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.delete(user.getId());
        Mockito.verify(userRepository).delete(user);
    }

    @Test
    void getAllUsersTest() {
        User newUser = user;
        newUser.setId(2L);
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user, newUser));
        assertEquals(Arrays.asList(user, newUser), userService.getAll());
    }
}
