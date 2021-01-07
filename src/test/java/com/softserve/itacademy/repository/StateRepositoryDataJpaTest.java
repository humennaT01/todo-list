package com.softserve.itacademy.repository;

import com.softserve.itacademy.ToDoListApplication;
import com.softserve.itacademy.model.State;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = ToDoListApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StateRepositoryDataJpaTest {

    @Autowired
    StateRepository stateRepository;

    @Test
    void getStateByNameTest(){
        State state = stateRepository.getByName("New");
        assertEquals(5L, state.getId());
    }

    @Test
    void getAllStatesTest(){
        List<State> states = stateRepository.getAll();
        assertEquals(4, states.size());
    }
}
