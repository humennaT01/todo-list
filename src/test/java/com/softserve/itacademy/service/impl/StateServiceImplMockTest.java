package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.ModelUtils;
import com.softserve.itacademy.model.State;
import com.softserve.itacademy.repository.StateRepository;
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
public class StateServiceImplMockTest {

    @Mock
    StateRepository stateRepository;

    @InjectMocks
    StateServiceImpl stateService;

    State state = ModelUtils.getState();

    @Test
    void createStateTest() {
        Mockito.when(stateRepository.save(state)).thenReturn(state);
        State actual = stateService.create(state);
        assertEquals(state, actual);
    }

    @Test
    void readByStateIdTest() {
        Mockito.when(stateRepository.findById(1L)).thenReturn(Optional.of(state));
        State actual = stateService.readById(1L);
        assertEquals(state, actual);
    }

    @Test
    void updateStateTest() {
        State updatedState = state;
        updatedState.setName("UpdatedNew");

        Mockito.when(stateRepository.findById(1L)).thenReturn(Optional.of(state));
        Mockito.when(stateRepository.save(updatedState)).thenReturn(updatedState);
        assertEquals(updatedState, stateService.update(state));
    }

    @Test
    void deleteStateTest() {
        stateRepository.save(state);
        Mockito.when(stateRepository.findById(state.getId())).thenReturn(Optional.of(state));
        stateService.delete(state.getId());
        Mockito.verify(stateRepository).delete(state);
    }

    @Test
    void getByStateNameTest() {
        Mockito.when(stateRepository.getByName("New")).thenReturn(state);
        assertEquals(state, stateService.getByName("New"));
    }

    @Test
    void getAllStetesTest() {
        State newState = new State();
        newState.setId(2L);
        newState.setName("NewState");

        Mockito.when(stateRepository.getAll()).thenReturn(Arrays.asList(state,newState));
        assertEquals(Arrays.asList(state, newState), stateService.getAll());
    }
}
