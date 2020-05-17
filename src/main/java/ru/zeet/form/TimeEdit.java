package ru.zeet.form;

import javax.swing.*;
import java.awt.*;

public class TimeEdit extends MyDialog {
    private JTextField textField;

    public TimeEdit(String title) {
        setCenter(350, 250);

        JPanel panel_1 = new JPanel();
        panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        getContentPanel().add(panel_1);

        JLabel lblTitle = new JLabel(title);
        panel_1.add(lblTitle);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        getContentPanel().add(panel);

        JLabel lblNewLabel_1 = new JLabel("Название");
        panel.add(lblNewLabel_1);

        textField = new JTextField();
        textField.setColumns(20);
        panel.add(textField);
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String name) {
        textField.setText(name);
    }
}
