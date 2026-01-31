package todolist.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HelloWordView extends JFrame {
    private JLabel helloLabel;
    private JButton backButton;

    public HelloWordView() {
        super("Hello World");
        this.setLayout(new BorderLayout());

        helloLabel = new JLabel("Hello, World!");
        helloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(helloLabel, BorderLayout.CENTER);

        backButton = new JButton("Back to Todo List");
        backButton.addActionListener(e -> {
            TodoView todoView = new TodoView();
            todoView.setVisible(true);
            this.dispose();
        });
        this.add(backButton, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 200);
    }
}