package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.ModelUtils;
import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)

class ToDoServiceImplMockTest {

    @Mock
    private ToDoRepository toDoRepository;

    @InjectMocks
    private ToDoServiceImpl toDoService;

    ToDo toDo = ModelUtils.getToDo();

    @Test
    void createToDoTest() {
        Mockito.when(toDoRepository.save(toDo)).thenReturn(toDo);
        assertEquals(toDo, toDoService.create(toDo));
    }

    @Test
    void createToDoNullEntityReferenceExceptionTest() {
        ToDo toDo = null;
        Mockito.when(toDoRepository.save(toDo)).thenThrow(IllegalArgumentException.class);
        assertThrows(NullEntityReferenceException.class, () -> toDoService.create(toDo));
    }
    @Test
    void readByIdToDoTest() {
        Mockito.when(toDoRepository.findById(1L)).thenReturn(Optional.of(toDo));
        assertEquals(toDo, toDoService.readById(1L));
    }

    @Test
    void readByIdToDoEntityNotFoundExceptionTest() {
        Mockito.when(toDoRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,() -> toDoService.readById(100L));
    }

    @Test
    void updateToDoTest() {
        ToDo beginToDo = toDo;
        toDo.setTitle("My todo");

        Mockito.when(toDoRepository.findById(1L)).thenReturn(Optional.of(beginToDo));
        Mockito.when(toDoRepository.save(toDo)).thenReturn(toDo);
        assertEquals(toDo, toDoService.update(beginToDo));
    }

    @Test
    void updateToDoEntityNotFoundExceptionTest() {
        ToDo beginToDo = toDo;
        toDo.setTitle("My todo");

        Mockito.when(toDoRepository.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(toDoRepository.save(toDo)).thenReturn(toDo);
        assertThrows(EntityNotFoundException.class, () -> toDoService.update(beginToDo));
    }

    @Test
    void deleteToDoTest() {
        toDoRepository.save(toDo);
        Mockito.when(toDoRepository.findById(toDo.getId())).thenReturn(Optional.of(toDo));
        toDoService.delete(toDo.getId());
        Mockito.verify(toDoRepository).delete(toDo);
    }

    @Test
    void deleteToDoEntityNotFoundExceptionTest() {
        toDoRepository.save(toDo);
        Mockito.when(toDoRepository.findById(100L)).thenReturn(Optional.ofNullable(null));
        assertThrows(EntityNotFoundException.class, () ->toDoService.delete(100L));
        Mockito.verify(toDoRepository, Mockito.never()).delete(toDo);
    }

    @Test
    void getAllToDoTest() {
        ToDo toDo1 = toDo;
        toDo1.setId(2L);
        Mockito.when(toDoRepository.findAll()).thenReturn(Arrays.asList(toDo, toDo1));
        assertEquals(Arrays.asList(toDo, toDo1), toDoService.getAll());
    }

    @Test
    void getByUserId() {
        Mockito.when(toDoRepository.getByUserId(1l)).thenReturn(Arrays.asList(toDo));
        assertEquals(Arrays.asList(toDo), toDoService.getByUserId(1L));
    }
}