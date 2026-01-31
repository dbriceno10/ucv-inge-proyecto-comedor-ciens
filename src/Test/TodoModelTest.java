package com.todolist;

import com.todolist.Model.TodoModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TodoModelTest {

    private TodoModel model;

    @BeforeEach
    public void setUp() {
        model = new TodoModel();
    }

    @Test
    public void testAddTask() {
        model.addTask("Task 1");
        List<String> tasks = model.getTasks();
        assertEquals(1, tasks.size());
        assertEquals("Task 1", tasks.get(0));
    }

    @Test
    public void testRemoveTask() {
        model.addTask("Task 1");
        model.removeTask(0);
        List<String> tasks = model.getTasks();
        assertTrue(tasks.isEmpty());
    }
}