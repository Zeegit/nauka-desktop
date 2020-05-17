package ru.zeet.form;

import ru.zeet.model.DepartmentTableModel;
import ru.zeet.db.ConnectionDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeForm extends MyJFrame {

    private JPanel contentPane;
    private JTable table;
    private ConnectionDB connect;

    /**
     * Launch the application.
     */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DepartmentForm frame = new DepartmentForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

    /**
     * Create the frame.
     */
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

        JLabel lblNewLabel = new JLabel("Список департаментов");
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
        String recId = getCurrentRecord(0);
        String name = getCurrentRecord(1);

        DeparntamentEdit dialog = new DeparntamentEdit("Изменение департамента");
        dialog.setText(name);
        int result = dialog.showDialog(true);
        String text = dialog.getText();

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
        DeparntamentEdit dialog = new DeparntamentEdit("Добавление департамента");
        int result = dialog.showDialog(true);
        String text = dialog.getText();

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
        String recId = getCurrentRecord(0);
        String name = getCurrentRecord(1);

        if (recId != null) {
            int result = JOptionPane.showConfirmDialog(this, "Удалить запись '" + name + "'?", "Удалить", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                String sql = "delete from department where id = " + recId;
                connect.executeUpdate(sql);
            }
        }
        refreshTimeTable();
    }

    private String getCurrentRecord(int columnIndex) {
        String result = null;
        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length > 0) {
            result = String.valueOf(table.getModel().getValueAt(selectedRows[0], columnIndex));
        }
        return result;
    }

    private JPanel createMainPanel() {
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BorderLayout());

        DepartmentTableModel dtm = new DepartmentTableModel();

        table = new JTable(dtm);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        panelMain.add(scrollPane, BorderLayout.CENTER);

        refreshTimeTable();

        return panelMain;
    }

    private void refreshTimeTable() {
        DepartmentTableModel ttm = new DepartmentTableModel();
        ttm.addData(connect);
        table.setModel(ttm);
        table.repaint();
        if (table.getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0);
        }
    }

}
