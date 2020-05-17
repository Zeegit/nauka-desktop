package ru.zeet.model.base;

import ru.zeet.db.ConnectionDB;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyModel extends AbstractTableModel {
    private int columnCount;
    private List<String[]> data;
    private List<String> columnNames;
    private String sql;


    public MyModel() {
        data = new ArrayList<>();
        columnNames = new ArrayList<>();
    }

    public void addColumnName(String name) {
        columnNames.add(name);
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
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

    public void addData(ConnectionDB conn) {
    }

}
