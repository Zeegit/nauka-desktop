package ru.zeet.model;

import ru.zeet.db.ConnectionDB;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentTableModel extends AbstractTableModel {

    private int columnCount = 2;
    private List<String[]> data;


    public DepartmentTableModel() {
        data = new ArrayList<>();
       /* for (int i = 0; i < data.size(); i++) {
            data.add(new String[getColumnCount()]);
        }*/
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
        switch (column) {
            case 0:
                return "id";
            case 1:
                return "name";
        }
        return "";
    }



    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] rows = data.get(rowIndex);
        return rows[columnIndex];
    }


    public void addData(String[] row) {
        /*String[] rowTable = new String[getColumnCount()];
        rowTable = row;*/
        data.add(row);
    }

    public void addData(ConnectionDB conn) {
        ResultSet rs = conn.resultSetQuery("SELECT id, name FROM department ORDER BY id");

        try {
            while (rs.next()) {
                String[] row = {
                        rs.getString("id"),
                        rs.getString("name")
                };
                addData(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

