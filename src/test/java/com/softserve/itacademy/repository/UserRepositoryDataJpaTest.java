package com.softserve.itacademy.repository;

import com.softserve.itacademy.ToDoListApplication;
import com.softserve.itacademy.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = ToDoListApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryDataJpaTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void getUserByEmailTest(){
        User user = userRepository.getUserByEmail("nora@mail.com");
        assertEquals(6L, user.getId());
    }
}
