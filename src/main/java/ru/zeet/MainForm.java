package ru.zeet;


import ru.zeet.db.ConnectionDB;
import ru.zeet.form.DepartmentForm;
import ru.zeet.form.EmployeeForm;
import ru.zeet.form.TimeForm;
import ru.zeet.model.DepartmentTableModel;
import ru.zeet.model.TimeTableModel;
import ru.zeet.render.Render;
import ru.zeet.util.Holidays;
import ru.zeet.util.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;


public class MainForm {
    private final String[] months = new String[]{"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int sizeWidth = 1200;
    private final int sizeHeight = 600;
    private final int locationX = (screenSize.width - sizeWidth) / 2;
    private final int locationY = (screenSize.height - sizeHeight) / 2;
    JSpinner spinner;
    private String iniFileName = "database.ini";
    //ArrayList<Department> departments = new ArrayList<>();
    private ConnectionDB connect;
    private JFrame frame;
    private JTable tableDepartments;
    private JTable tableTimeTable;
    private int dep_id;
    private JComboBox comboBox;
    private String host;
    private String user;
    private String password;
    private String dbname;


    public MainForm() throws SQLException, ClassNotFoundException, IOException {
        readIni(iniFileName);
        initialize();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainForm window = new MainForm();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void readIni(String fileName) throws IOException {
        Properties p = new Properties();

        p.load(new FileInputStream(fileName));
        host = p.getProperty("host");
        user = p.getProperty("user");
        password = p.getProperty("password");
        dbname = p.getProperty("dbname");
        p.list(System.out);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(locationX, locationY, sizeWidth, sizeHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        connect = new ConnectionDB(host, user, password);
        connect.setDBName(dbname);
        connect.init();

        frame.getContentPane().add(createTopPanel(), BorderLayout.NORTH);
        frame.getContentPane().add(createBottomPanel(), BorderLayout.SOUTH);
        frame.getContentPane().add(createMainPanel(), BorderLayout.CENTER);
        frame.getContentPane().add(createLeftPanel(), BorderLayout.WEST);

        tableDepartments.setRowSelectionInterval(0, 0);
    }

    private JPanel createMainPanel() {
        JPanel panelMain = new JPanel();
        panelMain.setSize(new Dimension(200, 200));
        panelMain.setLayout(new BorderLayout());

        tableTimeTable = new JTable();

        JScrollPane mainScrollPane = new JScrollPane(tableTimeTable);
        mainScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mainScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        panelMain.add(mainScrollPane, BorderLayout.CENTER);
        return panelMain;
    }

    private void refreshTimeTable(int departmentId, int month, int year) {
        //tableTimeTable.getModel();
        TimeTableModel ttm = new TimeTableModel();
        ttm.addData(connect, departmentId, month, year);

        tableTimeTable.setModel(ttm);

        tableTimeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableTimeTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        tableTimeTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        tableTimeTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        tableTimeTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        for (int i = 4; i < 35; i++) {
            tableTimeTable.getColumnModel().getColumn(i).setPreferredWidth(20);
        }

        Holidays h = new Holidays();
        int[] hd = h.get(connect, month, year);

        for (int i = 0; i < 35; i++) {
            tableTimeTable.getColumnModel().getColumn(i).setCellRenderer(new Render(hd));
        }

        tableTimeTable.repaint();
        if (tableTimeTable.getRowCount() > 0) {
            tableTimeTable.setRowSelectionInterval(0, 0);
        }
    }

    private void refreshDepartmentTable() {
        DepartmentTableModel dtm = new DepartmentTableModel();
        dtm.addData(connect);

        tableDepartments.setModel(dtm);

        tableDepartments.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableDepartments.getColumnModel().getColumn(0).setPreferredWidth(20);
        tableDepartments.getColumnModel().getColumn(1).setPreferredWidth(170);

        tableDepartments.repaint();
        if (tableDepartments.getRowCount() > 0) {
            tableDepartments.setRowSelectionInterval(0, 0);
        }
    }

    private JPanel createLeftPanel() {
        JPanel panelLeft = new JPanel();
        panelLeft.setPreferredSize(new Dimension(200, 100));
        panelLeft.setLayout(new BorderLayout());

        DepartmentTableModel dtm = new DepartmentTableModel();
        tableDepartments = new JTable(dtm);

        JScrollPane depScrollPane = new JScrollPane(tableDepartments);
        panelLeft.add(depScrollPane, BorderLayout.CENTER);

        ListSelectionModel selModel = tableDepartments.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                String recNo = Util.getCurrentRecord(tableDepartments, 0);
                if (recNo != null) {
                    dep_id = Integer.parseInt(recNo);
                    refreshTimeTable(dep_id, comboBox.getSelectedIndex() + 1, Integer.parseInt("" + spinner.getValue()));
                }
            }
        });
        refreshDepartmentTable();

        return panelLeft;
    }

    private JPanel createBottomPanel() {
        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JButton btnTimeTable = new JButton("Табель");
        btnTimeTable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TimeForm frame = new TimeForm(connect);
                frame.setModal(true);
                frame.setVisible(true);

                refreshTimeTable(dep_id, comboBox.getSelectedIndex() + 1, Integer.parseInt("" + spinner.getValue()));

                frame.setVisible(false);
                frame.dispose();

            }
        });
        panelBottom.add(btnTimeTable);

        JButton btnDepatment = new JButton("Департаменты");
        btnDepatment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DepartmentForm frame = new DepartmentForm(connect);
                frame.setModal(true);
                frame.setVisible(true);
                refreshDepartmentTable();
                frame.setVisible(false);
                frame.dispose();

            }
        });
        panelBottom.add(btnDepatment);

        JButton btnEmployee = new JButton("Сотрудники");
        btnEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EmployeeForm frame = new EmployeeForm(connect);
                frame.setModal(true);
                frame.setVisible(true);

                refreshTimeTable(dep_id, comboBox.getSelectedIndex() + 1, Integer.parseInt("" + spinner.getValue()));

                frame.setVisible(false);
                frame.dispose();

            }
        });
        panelBottom.add(btnEmployee);

        return panelBottom;
    }

    private JPanel createTopPanel() {
        JPanel panelTop = new JPanel();

        panelTop.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(months));
        comboBox.setSelectedIndex(LocalDate.now().getMonth().getValue() - 1);
        panelTop.add(comboBox);

        spinner = new JSpinner();
        spinner.setModel(new SpinnerNumberModel(2020, 2010, 2030, 1));
        spinner.setValue(LocalDate.now().getYear());
        panelTop.add(spinner);

        JButton btnNewButton = new JButton("Обновить");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshTimeTable(dep_id, comboBox.getSelectedIndex() + 1, Integer.parseInt("" + spinner.getValue()));
            }
        });
        panelTop.add(btnNewButton);

        return panelTop;
    }

}
