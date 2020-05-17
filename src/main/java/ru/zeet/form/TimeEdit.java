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

public class TimeEdit extends MyDialog {
    private JComboBox cbEmployee;
    private JComboBox cbWorkcode;
    private JDatePicker calendar;

    public TimeEdit(String title, Vector<Item> emp, Vector<Item> work) {
        super();
        setCenter(450, 300);

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

        panel_1.add(new JLabel(title));

        panel_2.add(new JLabel("Дата"));
        calendar = new JDatePicker();
        panel_2.add(calendar);

        panel_3.add(new JLabel("Сотрудник"));
        cbEmployee = new JComboBox(emp);
        cbEmployee.setRenderer(new ItemRenderer());
        panel_3.add(cbEmployee);

        panel_4.add(new JLabel("Тип рабочего дня"));
        cbWorkcode = new JComboBox(work);
        cbWorkcode.setRenderer(new ItemRenderer());
        panel_4.add(cbWorkcode);
    }

    public String getDate() {
        return LocalDate.of(calendar.getModel().getYear(), calendar.getModel().getMonth() + 1, calendar.getModel().getDay()).toString();
    }

    public String getEmployeeId() {
        Item item = (Item) cbEmployee.getSelectedItem();
        return item.getId();
    }

    public String getWorkcodeId() {
        Item item = (Item) cbWorkcode.getSelectedItem();
        return item.getId();
    }


    public void setDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        DateModel<Calendar> dateModel = (DateModel<Calendar>) calendar.getModel();
        dateModel.setValue(c);
    }

    public void setEmployeeId(int index) {
        cbEmployee.setSelectedIndex(index);
    }

    public void setWorkcodeId(int index) {
        cbWorkcode.setSelectedIndex(index);
    }
}
