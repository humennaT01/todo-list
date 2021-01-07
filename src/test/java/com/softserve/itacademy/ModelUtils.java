package com.softserve.itacademy;

import com.softserve.itacademy.model.*;

import java.time.LocalDateTime;
import java.util.Collections;

public class ModelUtils {


    public static State getState(){

        State state = new State();
        state.setId(1L);
        state.setName("New");
        return state;
    }

    public static ToDo getToDo(){
        ToDo toDo = new ToDo();
        toDo.setId(1L);
        toDo.setTitle("Task#1");
        toDo.setCreatedAt(LocalDateTime.now());
        toDo.setOwner(getUser());
        return toDo;
    }

    public static Task getTask(){
        Task task = new Task();
        task.setId(1L);
        task.setName("Clean my room");
        task.setPriority(Priority.LOW);
        task.setState(getState());
        task.setTodo(getToDo());
        return task;
    }

    public static User getUser(){
        User user = new User();
        user.setId(1L);
        user.setRole(getRole());
        user.setFirstName("Maksym");
        user.setLastName("Mysak");
        user.setEmail("maksym@ua.com");
        user.setPassword("12323213");
        user.setMyTodos(Collections.singletonList(new ToDo()));
        return user;
    }

    public static Role getRole(){
        Role role = new Role();
        role.setId(1L);
        role.setName("USER");
        return role;
    }
}
