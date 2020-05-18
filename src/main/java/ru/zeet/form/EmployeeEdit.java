package ru.zeet.form;


import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;
import ru.zeet.form.base.Item;
import ru.zeet.form.base.MyDialog;
import ru.zeet.render.ItemRenderer;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class EmployeeEdit extends MyDialog {

    private JTextField textServiceNumber;
    private JTextField textFirstName;
    private JTextField textLastName;
    private JTextField textAddress;
    private JDatePicker calendarBirthdate;
    private JComboBox comboDepartment;
    private JComboBox comboPosition;
    private JCheckBox checkSex;
    private JCheckBox checkRemote;


    public EmployeeEdit(String title, Vector<Item> departmentVec, Vector<Item> positionVec) {
        super();
        setCenter(550, 450);

        JPanel panel_1 = new JPanel();
        panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        getContentPanel().add(panel_1);

        JPanel panel_2 = new JPanel();
        panel_2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        getContentPanel().add(panel_2);

        JPanel panel_3 = new JPanel();
        panel_3.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        getContentPanel().add(panel_3);

        JPanel panel_4 = new JPanel();
        panel_4.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        getContentPanel().add(panel_4);

        JPanel panel_5 = new JPanel();
        panel_5.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        getContentPanel().add(panel_5);

        JPanel panel_6 = new JPanel();
        panel_6.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        getContentPanel().add(panel_6);

        JPanel panel_7 = new JPanel();
        panel_7.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        getContentPanel().add(panel_7);

        JPanel panel_8 = new JPanel();
        panel_8.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        getContentPanel().add(panel_8);

        JPanel panel_9 = new JPanel();
        panel_9.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        getContentPanel().add(panel_9);

        JPanel panel_10 = new JPanel();
        panel_10.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        getContentPanel().add(panel_10);

        panel_1.add(new JLabel(title));

        panel_2.add(new JLabel("Табельный номер"));
        textServiceNumber = new JTextField();
        textServiceNumber.setColumns(20);
        panel_2.add(textServiceNumber);

        panel_3.add(new JLabel("Имя"));
        textFirstName = new JTextField();
        textFirstName.setColumns(20);
        panel_3.add(textFirstName);


        panel_4.add(new JLabel("Фамилия"));
        textLastName = new JTextField();
        textLastName.setColumns(20);
        panel_4.add(textLastName);

        panel_5.add(new JLabel("Дата рождения"));
        calendarBirthdate = new JDatePicker();
        panel_5.add(calendarBirthdate);

        panel_6.add(new JLabel("Департамент"));
        comboDepartment = new JComboBox(departmentVec);
        comboDepartment.setRenderer(new ItemRenderer());
        panel_6.add(comboDepartment);

        panel_7.add(new JLabel("Должность"));
        comboPosition = new JComboBox(positionVec);
        comboPosition.setRenderer(new ItemRenderer());
        panel_7.add(comboPosition);

        panel_8.add(new JLabel("Пол (ваделен - мужской, пусто - женский)"));
        checkSex = new JCheckBox();
        panel_8.add(checkSex);

        panel_9.add(new JLabel("Удаленка"));
        checkRemote = new JCheckBox();
        panel_9.add(checkRemote);

        panel_10.add(new JLabel("Адрес"));
        textAddress = new JTextField();
        textAddress.setColumns(20);
        panel_10.add(textAddress);
    }

    public String getDate() {
        return LocalDate.of(calendarBirthdate.getModel().getYear(), calendarBirthdate.getModel().getMonth() + 1, calendarBirthdate.getModel().getDay()).toString();
    }

    public void setDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        DateModel<Calendar> dateModel = (DateModel<Calendar>) calendarBirthdate.getModel();
        dateModel.setValue(c);
    }

    public String getDeprtmentId() {
        Item item = (Item) comboDepartment.getSelectedItem();
        return item.getId();
    }

    public void setDeprtmentId(int index) {
        comboDepartment.setSelectedIndex(index);
    }

    public String getPositionId() {
        Item item = (Item) comboPosition.getSelectedItem();
        return item.getId();
    }

    public void setPositionId(int index) {
        comboPosition.setSelectedIndex(index);
    }

    public String getServiceNumber() { return textServiceNumber.getText(); }
    public String getFirstName() { return textFirstName.getText(); }
    public String getLastName() { return textLastName.getText(); }
    public String getAddress() { return textAddress.getText(); }

    public String getSex() { return checkSex.isSelected()?"true":"false"; }
    public String getRemote() { return checkRemote.isSelected()?"true":"false"; }


    public void setServiceNumber(String text) { textServiceNumber.setText(text); }
    public void setFirstName(String text) { textFirstName.setText(text); }
    public void setLastName(String text) { textLastName.setText(text); }
    public void setAddress(String text) { textAddress.setText(text); }

    public void setSex(String text) { checkSex.setSelected("м".equals(text)); }
    public void setRemote(String text) { checkRemote.setSelected("да".equals(text)); }

    @Override
    public int checkInput() {
        if (getServiceNumber().equals("")) {
            JOptionPane.showMessageDialog(this, "Пустой табельный номер", "Ошибка", JOptionPane.ERROR_MESSAGE);
            textServiceNumber.requestFocusInWindow();
            return -1;
        }

        if (getFirstName().equals("")) {
            JOptionPane.showMessageDialog(this, "Пустое имя", "Ошибка", JOptionPane.ERROR_MESSAGE);
            textFirstName.requestFocusInWindow();
            return -1;
        }

        if (getLastName().equals("")) {
            JOptionPane.showMessageDialog(this, "Пустая фамилия", "Ошибка", JOptionPane.ERROR_MESSAGE);
            textLastName.requestFocusInWindow();
            return -1;
        }

        if (getDate().equals("")) {
            JOptionPane.showMessageDialog(this, "Пустая дата", "Ошибка", JOptionPane.ERROR_MESSAGE);
            calendarBirthdate.requestFocusInWindow();
            return -1;
        }

        return 0;
    }
}
