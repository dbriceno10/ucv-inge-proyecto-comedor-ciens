package com.todolist.controllers;

import com.todolist.model.TodoModel;
import com.todolist.view.TodoView;

import javax.swing.*;

public class TodoController {
    private TodoModel model;
    private TodoView view;

    public TodoController(TodoModel model, TodoView view) {
        this.model = model;
        this.view = view;

        this.view.getAddButton().addActionListener(e -> addTask());
        this.view.getRemoveButton().addActionListener(e -> removeTask());
    }

    public void init() {
        view.updateTaskList(model.getTasks());
        view.setVisible(true);
    }

    private void addTask() {
        String task = view.getTaskInput();
        if (!task.isEmpty()) {
            model.addTask(task);
            view.updateTaskList(model.getTasks());
            view.clearTaskInput();
        } else {
            JOptionPane.showMessageDialog(null, "Task cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeTask() {
        String selectedTask = view.getTaskList().getSelectedValue();
        if (selectedTask != null) {
            model.removeTask(selectedTask);
            view.updateTaskList(model.getTasks());
        } else {
            JOptionPane.showMessageDialog(null, "No task selected!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}