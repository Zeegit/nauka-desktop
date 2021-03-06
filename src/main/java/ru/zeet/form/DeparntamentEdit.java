package ru.zeet.form;

import ru.zeet.form.base.MyDialog;

import javax.swing.*;
import java.awt.*;

public class DeparntamentEdit extends MyDialog {
    private JTextField textField;

    public DeparntamentEdit(String title) {
        setCenter(450, 450);

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

    @Override
    public int checkInput() {
        if (getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Пустое название", "Ошибка", JOptionPane.ERROR_MESSAGE);
            textField.requestFocusInWindow();
            return -1;
        }
        return 0;
    }
}
