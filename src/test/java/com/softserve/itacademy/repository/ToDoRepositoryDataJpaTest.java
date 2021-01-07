package com.softserve.itacademy.repository;

import com.softserve.itacademy.ToDoListApplication;
import com.softserve.itacademy.model.ToDo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = ToDoListApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ToDoRepositoryDataJpaTest {

    @Autowired
    ToDoRepository toDoRepository;

    @Test
    void getByUserId() {
        List<ToDo> toDoList = toDoRepository.getByUserId(6L);
        assertEquals(4, toDoList.size());
    }
}