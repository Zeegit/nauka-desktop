package ru.zeet.form;

import ru.zeet.db.ConnectionDB;
import ru.zeet.form.base.Item;
import ru.zeet.form.base.MyJFrame;
import ru.zeet.model.TimeModel;
import ru.zeet.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class TimeForm extends MyJFrame {

    private JPanel contentPane;
    private JTable table;
    private ConnectionDB connect;
    private Vector<Item> emp;
    private Vector<Item> work;


    public TimeForm(ConnectionDB connect) {
        this.connect = connect;
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setCenter(450, 300);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JPanel panelTop = new JPanel();
        contentPane.add(panelTop, BorderLayout.NORTH);
        panelTop.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JLabel lblNewLabel = new JLabel("Список рабочих дней");
        panelTop.add(lblNewLabel);

        JPanel panelBottom = new JPanel();
        FlowLayout fl_panelBottom = (FlowLayout) panelBottom.getLayout();
        fl_panelBottom.setAlignment(FlowLayout.LEFT);
        contentPane.add(panelBottom, BorderLayout.SOUTH);

        JButton btnAdd = new JButton("Добавить");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRecord();
            }
        });
        panelBottom.add(btnAdd);

        JButton btnEdit = new JButton("Изменить");
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editRecord();
            }
        });
        panelBottom.add(btnEdit);

        JButton btnDelete = new JButton("Удалить");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteRecord();
            }
        });
        panelBottom.add(btnDelete);

        contentPane.add(createMainPanel(), BorderLayout.CENTER);
        init();
    }


    private void addRecord() {
        TimeEdit dialog = new TimeEdit("Добавление записи", emp, work);
        int result = dialog.showDialog(true);

        String date = dialog.getDate();
        String employeeId = dialog.getEmployeeId();
        String workcodeId = dialog.getWorkcodeId();

        if (result == JOptionPane.OK_OPTION) {
            if (date == null || "".equals(date)) {
                JOptionPane.showMessageDialog(this, "Пустая дата", "Инфо", JOptionPane.OK_OPTION);
            } else {
                String sql = "insert into work_calendar (work_date, employee_id, work_code_id) values ('" + date + "', " + employeeId + ", " + workcodeId + ");";
                System.out.println(sql);
                connect.executeUpdate(sql);
            }
        }
        refreshTimeTable();
    }

    private void editRecord() {
        String dateInString = Util.getCurrentRecord(table, 2);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateD = null;
        try {
            dateD = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String recId = Util.getCurrentRecord(table, 4);
        String oldEmployeeId = Util.getCurrentRecord(table, 5);
        String oldWorkcodeId = Util.getCurrentRecord(table, 6);
        int oldEmployeeIndex = 0;
        int ooldWorkcodeIndex = 0;
        for (int i = 0; i < emp.size(); i++) {
            if (oldEmployeeId.equals(emp.get(i).getId())) {
                oldEmployeeIndex = i;
                break;
            }
        }
        for (int i = 0; i < work.size(); i++) {
            if (oldWorkcodeId.equals(work.get(i).getId())) {
                ooldWorkcodeIndex = i;
                break;
            }
        }

        TimeEdit dialog = new TimeEdit("Изменение записи", emp, work);
        dialog.setDate(dateD);
        dialog.setEmployeeId(oldEmployeeIndex);
        dialog.setWorkcodeId(ooldWorkcodeIndex);

        int result = dialog.showDialog(true);

        String date = dialog.getDate();
        String employeeId = dialog.getEmployeeId();
        String workcodeId = dialog.getWorkcodeId();

        if (result == JOptionPane.OK_OPTION) {
            if (date == null || "".equals(date)) {
                JOptionPane.showMessageDialog(this, "Пустое имя", "Инфо", JOptionPane.ERROR_MESSAGE);
            } else {
                String sql = "update work_calendar set work_date = '" + date + "', employee_id = " + employeeId + ", work_code_id = " + workcodeId + " where id = " + recId;
                connect.executeUpdate(sql);
            }
        }
        refreshTimeTable();
    }

    private void deleteRecord() {
        String recId = Util.getCurrentRecord(table, 4);

        if (recId != null) {
            int result = JOptionPane.showConfirmDialog(this, "Удалить запись ?", "Удалить", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                String sql = "delete from work_calendar where id = " + recId;
                connect.executeUpdate(sql);
            }
        }
        refreshTimeTable();
    }

    private JPanel createMainPanel() {
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BorderLayout());

        TimeModel model = new TimeModel();

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        panelMain.add(scrollPane, BorderLayout.CENTER);

        refreshTimeTable();

        return panelMain;
    }

    private void refreshTimeTable() {
        TimeModel ttm = new TimeModel();
        ttm.addData(connect);
        table.setModel(ttm);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(60);

        table.getColumnModel().getColumn(4).setPreferredWidth(0);
        table.getColumnModel().getColumn(4).setMinWidth(0);
        table.getColumnModel().getColumn(4).setMaxWidth(0);
        table.getColumnModel().getColumn(5).setPreferredWidth(0);
        table.getColumnModel().getColumn(5).setMinWidth(0);
        table.getColumnModel().getColumn(5).setMaxWidth(0);
        table.getColumnModel().getColumn(6).setPreferredWidth(0);
        table.getColumnModel().getColumn(6).setMinWidth(0);
        table.getColumnModel().getColumn(6).setMaxWidth(0);
        table.repaint();
        if (table.getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0);
        }
    }

    private void init() {
        ResultSet rs1 = connect.resultSetQuery("select id, concat(first_name, ' ', last_name) as name from employee");
        emp = new Vector<>();
        try {
            while (rs1.next()) {
                emp.add(new Item(rs1.getString("id"), rs1.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs2 = connect.resultSetQuery("select id, name from work_code");
        work = new Vector<>();
        try {
            while (rs2.next()) {
                work.add(new Item(rs2.getString("id"), rs2.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
