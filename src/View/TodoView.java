package com.todolist.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TodoView {
    private JFrame frame;
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private JTextField taskInput;
    private JButton addButton;
    private JButton removeButton;

    public TodoView() {
        frame = new JFrame("Todo List");
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskInput = new JTextField(20);
        addButton = new JButton("Add Task");
        removeButton = new JButton("Remove Task");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.add(taskInput);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public void updateTaskList(List<String> tasks) {
        listModel.clear();
        for (String task : tasks) {
            listModel.addElement(task);
        }
    }

    public String getTaskInput() {
        return taskInput.getText();
    }

    public void clearTaskInput() {
        taskInput.setText("");
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JList<String> getTaskList() {
        return taskList;
    }
}