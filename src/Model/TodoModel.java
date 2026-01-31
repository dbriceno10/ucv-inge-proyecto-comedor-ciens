package com.todolist.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TodoModel {
    private List<String> tasks;
    private static final String FILE_PATH = "src/Database/tasks.json";

    public TodoModel() {
        this.tasks = new ArrayList<>();
        loadTasks();
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void addTask(String task) {
        tasks.add(task);
        saveTasks();
    }

    public void removeTask(String task) {
        tasks.remove(task);
        saveTasks();
    }

    private void saveTasks() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(FILE_PATH), tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTasks() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                tasks = mapper.readValue(file, List.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}