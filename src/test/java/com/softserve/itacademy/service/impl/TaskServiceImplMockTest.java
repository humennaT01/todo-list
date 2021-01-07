package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.ModelUtils;
import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class TaskServiceImplMockTest {

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    Task task = ModelUtils.getTask();

    @Test
    void createTaskTest() {
        Mockito.when(taskRepository.save(task)).thenReturn(task);
        assertEquals(task, taskService.create(task));
    }

    @Test
    void createTaskNullEntityReferenceExceptionTest() {
        Task task = null;
        Mockito.when(taskRepository.save(task)).thenThrow(IllegalArgumentException.class);
        assertThrows(NullEntityReferenceException.class, () -> taskService.create(task));
    }

    @Test
    void readTaskByIdTest() {
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        assertEquals(task, taskService.readById(1L));
    }

    @Test
    void readTaskByIdEntityNotFoundExceptionTest() {
        Mockito.when(taskRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,() -> taskService.readById(100L));
    }

    @Test
    void updateTaskTest() {
        Task beginTask = task;
        task.setName("My task");

        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(beginTask));
        Mockito.when(taskRepository.save(task)).thenReturn(task);
        assertEquals(task, taskService.update(beginTask));
    }

    @Test
    void updateTaskEntityNotFoundExceptionTest() {
        Task beginTask = task;
        task.setName("My task");

        Mockito.when(taskRepository.findById(2L)).thenReturn(Optional.empty());
        Mockito.when(taskRepository.save(task)).thenReturn(task);
        assertThrows(EntityNotFoundException.class, () -> taskService.update(beginTask));
    }

    @Test
    void deleteTaskTest() {
        taskRepository.save(task);
        Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        taskService.delete(task.getId());
        Mockito.verify(taskRepository).delete(task);
    }

    @Test
    void deleteTaskEntityNotFoundExceptionTest() {
        taskRepository.save(task);
        Mockito.when(taskRepository.findById(100L)).thenReturn(Optional.ofNullable(null));
        assertThrows(EntityNotFoundException.class, () ->taskService.delete(100L));
        Mockito.verify(taskRepository, Mockito.never()).delete(task);
    }

    @Test
    void getAllTasksTest() {
        Task task1 = task;
        task1.setId(2L);
        Mockito.when(taskRepository.findAll()).thenReturn(Arrays.asList(task, task1));
        assertEquals(Arrays.asList(task, task1), taskService.getAll());
    }

    @Test
    void getByTodoIdTasksTest() {
        Mockito.when(taskRepository.getByTodoId(1l)).thenReturn(Arrays.asList(task));
        assertEquals(Arrays.asList(task), taskService.getByTodoId(1L));
    }
}