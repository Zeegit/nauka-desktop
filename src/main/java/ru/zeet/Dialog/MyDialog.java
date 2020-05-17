package ru.zeet.Dialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyDialog extends JDialog {
    private final JPanel contentPanel;

    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int sizeWidth;
    private int sizeHeight;
    private int locationX;
    private int locationY;

    private int result;

    public MyDialog() {
        //setCenter(sizeWidth, sizeHeight);
        getContentPane().setLayout(new BorderLayout());
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.setLayout(new GridLayout(2, 1, 0, 0));

        getContentPane().add(contentPanel, BorderLayout.NORTH);
        getContentPane().add(createButtonPanel(), BorderLayout.SOUTH);
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public void setCenter(int width, int height) {
        sizeWidth = width;
        sizeHeight = height;
        locationX = (screenSize.width - sizeWidth) / 2;
        locationY = (screenSize.height - sizeHeight) / 2;

        super.setBounds(locationX, locationY, sizeWidth, sizeHeight);
    }

    public int showDialog(boolean modal) {
        setModal(modal);
        setVisible(true);
        return result;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public class OkActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            result = JOptionPane.OK_OPTION;
            setVisible(false);
            dispose();
        }
    }

    public class CalcelActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            result = JOptionPane.CANCEL_OPTION;
            setVisible(false);
            dispose();
        }
    }

    public JPanel createButtonPanel() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        {
            JButton okButton = new JButton("OK");
            okButton.setActionCommand("OK");
            buttonPane.add(okButton);
            getRootPane().setDefaultButton(okButton);

            okButton.addActionListener(new OkActionListener());
        }
        {
            JButton cancelButton = new JButton("Cancel");
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);

            cancelButton.addActionListener(new CalcelActionListener());
        }
        return buttonPane;
    }


}