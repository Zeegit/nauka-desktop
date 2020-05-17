package ru.zeet.form;

import ru.zeet.db.ConnectionDB;
import ru.zeet.form.base.MyJFrame;
import ru.zeet.model.TimeModel;
import ru.zeet.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeForm extends MyJFrame {

    private JPanel contentPane;
    private JTable table;
    private ConnectionDB connect;


    public EmployeeForm(ConnectionDB connect) {
        this.connect = connect;
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setCenter(450, 300);
        contentPane = new JPanel();

        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JPanel panelTop = new JPanel();
        contentPane.add(panelTop, BorderLayout.NORTH);
        panelTop.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JLabel lblNewLabel = new JLabel("Список сотрудников");
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
    }

    private void editRecord() {
        String recId = Util.getCurrentRecord(table,0);
        String name = Util.getCurrentRecord(table,1);

        TimeEdit dialog = new TimeEdit("Изменение записи", null, null);
        //dialog.setText(name);
        int result = dialog.showDialog(true);
        String text = ""; //dialog.getText();

        if (result == JOptionPane.OK_OPTION) {
            if (text == null || "".equals(text)) {
                JOptionPane.showMessageDialog(this, "Пустое имя", "Инфо", JOptionPane.OK_OPTION);
            } else {
                String sql = "update department set name = '" + text + "' where id = " + recId;
                connect.executeUpdate(sql);
            }
        }
        refreshTimeTable();
    }

    private void addRecord() {
        TimeEdit dialog = new TimeEdit("Добавление записи", null, null);
        int result = dialog.showDialog(true);
        String text = ""; //dialog.getText();

        if (result == JOptionPane.OK_OPTION) {
            if (text == null || "".equals(text)) {
                JOptionPane.showMessageDialog(this, "Пустое имя", "Инфо", JOptionPane.OK_OPTION);
            } else {
                String sql = "insert into department (name) values (?)";
                connect.executeUpdate(sql, text);
            }
        }
        refreshTimeTable();
    }

    private void deleteRecord() {
        String recId = Util.getCurrentRecord(table, 0);
        String name = Util.getCurrentRecord(table,1);

        if (recId != null) {
            int result = JOptionPane.showConfirmDialog(this, "Удалить запись ?", "Удалить", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                String sql = "delete from work_calendar where id = " + recId;
                connect.executeUpdate(sql);
            }
        }
        refreshTimeTable();
    }

/*    private String getCurrentRecord(int columnIndex) {
        String result = null;
        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length > 0) {
            result = String.valueOf(table.getModel().getValueAt(selectedRows[0], columnIndex));
        }
        return result;
    }*/

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
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);

        table.repaint();
        if (table.getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0);
        }
    }

}
