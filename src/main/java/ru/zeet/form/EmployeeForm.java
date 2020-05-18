package ru.zeet.form;

import ru.zeet.db.ConnectionDB;
import ru.zeet.form.base.Item;
import ru.zeet.form.base.MyJFrame;
import ru.zeet.model.EmployeeModel;
import ru.zeet.util.Util;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class EmployeeForm extends MyJFrame {

    private JPanel contentPane;
    private JTable table;
    private ConnectionDB connect;
    private Vector<Item> departmentVec;
    private Vector<Item> positionVec;


    public EmployeeForm(ConnectionDB connect) {
        this.connect = connect;
        setCenter(800, 300);

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
        EmployeeEdit dialog = new EmployeeEdit("Добавление сотрудника", departmentVec, positionVec);
        dialog.setDate(new Date());
        int result = dialog.showDialog(true);

        if (result == JOptionPane.OK_OPTION) {
            String serviceNumber = dialog.getServiceNumber();
            String firstName = dialog.getFirstName();
            String lastName = dialog.getLastName();
            String address = dialog.getAddress();
            String date = dialog.getDate();
            String departmentId= dialog.getDeprtmentId();
            String positionId = dialog.getPositionId();
            String sex = dialog.getSex();
            String remote  = dialog.getRemote();

            if (date == null || "".equals(date)) {
                JOptionPane.showMessageDialog(this, "Пустая дата", "Инфо", JOptionPane.ERROR_MESSAGE);
            } else {
                String query = "insert into employee (first_name, last_name, service_number, birth_date, sex, remote, address, position_id, department_id) " +
                        "values ('%s', '%s', '%s', '%s', %s, %s, '%s', %s, %s)";
                String sql = String.format(query, firstName, lastName, serviceNumber, date, sex, remote, address, positionId, departmentId);
                System.out.println(sql);
                connect.executeUpdate(sql);
            }
        }
        refreshTimeTable();
    }

    private void editRecord() {
        String oldServiceNumber =  Util.getCurrentRecord(table, 0);
        String oldFirstName = Util.getCurrentRecord(table, 1);
        String oldLastName = Util.getCurrentRecord(table, 2);

        String dateInString = Util.getCurrentRecord(table, 5);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date oldDate = null;
        try { oldDate = formatter.parse(dateInString); } catch (ParseException e) { e.printStackTrace(); }

        String oldAddress = Util.getCurrentRecord(table, 8);

        String recId = Util.getCurrentRecord(table, 9);
        String oldPositionId = Util.getCurrentRecord(table, 10);
        String oldDepartmentId = Util.getCurrentRecord(table, 11);
        String oldSex = Util.getCurrentRecord(table, 6);
        String oldRemote = Util.getCurrentRecord(table, 7);

        int oldPositionIndex = 0;
        int oldDepartmentIndex = 0;
        for (int i = 0; i < positionVec.size(); i++) {
            if (oldPositionId.equals(positionVec.get(i).getId())) {
                oldPositionIndex = i;
                break;
            }
        }
        for (int i = 0; i < departmentVec.size(); i++) {
            if (oldDepartmentId.equals(departmentVec.get(i).getId())) {
                oldDepartmentIndex = i;
                break;
            }
        }

        EmployeeEdit dialog = new EmployeeEdit("Изменение записи", departmentVec, positionVec);
        dialog.setServiceNumber(oldServiceNumber);
        dialog.setFirstName(oldFirstName);
        dialog.setLastName(oldLastName);
        dialog.setDate(oldDate);
        dialog.setSex(oldSex);
        dialog.setRemote(oldRemote);
        dialog.setAddress(oldAddress);
        dialog.setPositionId(oldPositionIndex);
        dialog.setDeprtmentId(oldDepartmentIndex);

        int result = dialog.showDialog(true);

        if (result == JOptionPane.OK_OPTION) {
            String serviceNumber = dialog.getServiceNumber();
            String firstName = dialog.getFirstName();
            String lastName = dialog.getLastName();
            String address = dialog.getAddress();
            String date = dialog.getDate();
            String departmentId= dialog.getDeprtmentId();
            String positionId = dialog.getPositionId();
            String sex = dialog.getSex();
            String remote  = dialog.getRemote();

            if (date == null || "".equals(date)) {
                JOptionPane.showMessageDialog(this, "Пустое имя", "Инфо", JOptionPane.ERROR_MESSAGE);
            } else {
                String query = "update employee " +
                        "set first_name = '%s', " +
                        "last_name  = '%s', " +
                        "service_number  = '%s', " +
                        "birth_date  = '%s', " +
                        "sex  = %s, " +
                        "remote  = %s, " +
                        "address  = '%s', " +
                        "position_id  = %s, " +
                        "department_id  = %s " +
                        "where id = %s";
                String sql = String.format(query, firstName, lastName, serviceNumber, date, sex, remote, address, positionId, departmentId, recId);
                System.out.println(sql);
                connect.executeUpdate(sql);
            }
        }
        refreshTimeTable();
    }

    private void deleteRecord() {
        String recId = Util.getCurrentRecord(table, 9);
        String name = Util.getCurrentRecord(table, 1) + " " + Util.getCurrentRecord(table, 2);

        if (recId != null) {
            int result = JOptionPane.showConfirmDialog(this, "Удалить сотрудника '" + name + "' ?", "Удалить", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                String sql = "delete from employee where id = " + recId;
                connect.executeUpdate(sql);
            }
        }
        refreshTimeTable();
    }

    private JPanel createMainPanel() {
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BorderLayout());

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        panelMain.add(scrollPane, BorderLayout.CENTER);

        refreshTimeTable();

        return panelMain;
    }

    private void refreshTimeTable() {
        EmployeeModel model = new EmployeeModel();
        model.addData(connect);
        table.setModel(model);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(40);
        table.getColumnModel().getColumn(7).setPreferredWidth(40);
        table.getColumnModel().getColumn(8).setPreferredWidth(100);

        setColumnInvisible(table.getColumnModel().getColumn(9));
        setColumnInvisible(table.getColumnModel().getColumn(10));
        setColumnInvisible(table.getColumnModel().getColumn(11));

        table.repaint();
        if (table.getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0);
        }
    }

    private void setColumnInvisible(TableColumn column) {
        column.setPreferredWidth(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
    }

    private void init() {
        ResultSet rs1 = connect.resultSetQuery("select id, name from department");
        departmentVec = new Vector<>();
        try {
            while (rs1.next()) {
                departmentVec.add(new Item(rs1.getString("id"), rs1.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs2 = connect.resultSetQuery("select id, name from position");
        positionVec = new Vector<>();
        try {
            while (rs2.next()) {
                positionVec.add(new Item(rs2.getString("id"), rs2.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
