package View;

import javax.swing.*;

import View.HelloWord.HelloWordView;

import java.awt.*;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TodoView {
  private JFrame frame;
  private DefaultListModel<String> listModel;
  private JList<String> taskList;
  private JTextField taskInput;
  private JButton addButton;
  private JButton removeButton;
  private JButton helloWorldButton;

  public TodoView() {
    frame = new JFrame("Todo List");
    listModel = new DefaultListModel<>();
    taskList = new JList<>(listModel);
    taskInput = new JTextField(20);
    addButton = new JButton("Add Task");
    removeButton = new JButton("Remove Task");
    helloWorldButton = new JButton("Go to Hello World");

    helloWorldButton.addActionListener(e -> {
      HelloWordView helloWordView = new HelloWordView();
      helloWordView.setVisible(true);
      frame.setVisible(false);
    });

    addButton.addActionListener(e -> {
      String task = taskInput.getText().trim();
      if (!task.isEmpty()) {
        listModel.addElement(task);
        taskInput.setText("");
      }
    });

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.add(new JScrollPane(taskList), BorderLayout.CENTER);

    JPanel inputPanel = new JPanel();
    inputPanel.add(taskInput);
    inputPanel.add(addButton);
    inputPanel.add(removeButton);
    inputPanel.add(helloWorldButton);

    frame.add(panel, BorderLayout.CENTER);
    frame.add(inputPanel, BorderLayout.SOUTH);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 300);
  }

  public void setVisible(boolean visible) {
    if (visible) {
      try {
        String json = new String(Files.readAllBytes(Paths.get("src/Database/tasks.json")));
        List<String> tasks = new ArrayList<>(
            Arrays.asList(json.replace("[", "").replace("]", "").replace("\"", "").split(",")));
        updateTaskList(tasks);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
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

  public JButton getHelloWorldButton() {
    return helloWorldButton;
  }

  public JList<String> getTaskList() {
    return taskList;
  }
}