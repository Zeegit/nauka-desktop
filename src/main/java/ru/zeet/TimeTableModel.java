package ru.zeet;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeTableModel extends AbstractTableModel {


    private int columnCount = 35;
    private List<String[]> data;
    private List<String> columnNames;
    private String sql = "select \n" +
            "  e.id as \"employee_id\",\n" +
            "  e.first_name,\n" +
            "  e.last_name,\n" +
            "  p.name as \"position_name\",\n" +
            "  e.service_number,\n" +
            "  w.work_date,\n" +
            "  c.name as \"work_code_name\"\n" +
            "from work_calendar w\n" +
            "join employee e on e.id = w.employee_id\n" +
            "join work_code c on c.id = w.work_code_id\n" +
            "join position p on p.id = e.position_id\n" +
            "where \n" +
            "\t\te.department_id = ?\n" +
            "\tand extract(month from w.work_date) = ?\n" +
            "\tand extract(year from w.work_date) = ?";


    public TimeTableModel() {
        data = new ArrayList<>();
        /*for (int i = 0; i < data.size(); i++) {
            data.add(new String[getColumnCount()]);
        }*/
        columnNames = new ArrayList<>();
        columnNames.add("ФИО");
        columnNames.add("Должность");
        columnNames.add("Табельный №");
        columnNames.add("Итого");
        for (int i = 1; i <= 31; i++) {
            columnNames.add(String.valueOf(i));
        }

    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public String getColumnName(int column) {
        if (columnNames != null && column < columnNames.size()) {
            return columnNames.get(column);
        }
        return "";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] rows = data.get(rowIndex);
        return rows[columnIndex];
    }

    public void addData(String[] row) {
        data.add(row);
    }

    public void addData(ConnectionDB conn, int departmentId, int month, int year) {
        //ResultSet rs = conn.resultSetQuery(sql, String.valueOf(departmentId), String.valueOf(month), String.valueOf(year));
        ResultSet rs = conn.resultSetQuery(sql, departmentId, month, year);
        Map<String, String[]> work = new HashMap<>();
        try {
            while (rs.next()) {
                String id = rs.getString("employee_id");
                int day = Instant.ofEpochMilli(rs.getDate("work_date").getTime()).atZone(ZoneId.systemDefault()).toLocalDate().getDayOfMonth();

                String[] strings = work.get(id);
                if (strings == null) {
                    strings = new String[columnCount];
                    strings[0] = rs.getString("first_name") + " " + rs.getString("last_name");
                    strings[1] = rs.getString("position_name");
                    strings[2] = rs.getString("service_number");
                }

                strings[day + 3] = rs.getString("work_code_name");
                work.put(id, strings);
            }

            for (Map.Entry<String, String[]> w : work.entrySet()) {
                String[] row = w.getValue();
                Map<String, Integer> total = new HashMap<>();
                for (int i = 4; i < columnCount; i++) {
                    if (row[i] != null) {
                        total.put(row[i], total.getOrDefault(row[i], 0) + 1);
                    }
                }
                // Итог
                row[3] = total.toString();
                addData(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}


