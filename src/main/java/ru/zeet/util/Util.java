package ru.zeet.util;

import javax.swing.*;

public class Util {
    public static String getCurrentRecord(JTable table, int columnIndex) {
        String result = null;
        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length > 0) {
            result = String.valueOf(table.getModel().getValueAt(selectedRows[0], columnIndex));
        }
        return result;
    }
}
