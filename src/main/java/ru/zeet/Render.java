package ru.zeet;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class Render extends DefaultTableCellRenderer {
    public static final Color lineDark = new Color(230, 237, 247);
    public static final Color lineWhite = new Color(255, 255, 255);
    public static final Color lineSelected = new Color(170, 238, 187);

    public static final Color lineOddWork = new Color(224, 241, 211);
    public static final Color lineOddWeekend = new Color(236, 178, 186);
    public static final Color lineEvenWork = new Color(243, 255, 217);
    public static final Color lineEvenWeekend = new Color(255, 192, 192);

    public static final Color lineSelWork = new Color(179, 242, 165);
    public static final Color lineSelWeek = new Color(191, 179, 140);

    int[] days;


    public Render(int[] days) {
        this.days = days;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Color color = lineWhite;

        // TODO: 16.05.2020 Переделать на новое определение цветов
        /*int token = 0;
        if (isSelected) { token += 1000; }
        if (column >= 4) { token += 100; }
        if (row % 2 == 1) { token += 10; }
        if (column > 4 && days[column - 4] == 1) { token += 1; }


        switch (token) {
            case 100: color = lineOddWork; break;
            case 101: color = lineSelected; break;

            case 1110: color = lineSelected; break;
            default: color = lineWhite;
        }*/

        // Раскраски
        if (column < 4) {
            if (isSelected) {
                color = lineSelected;
            } else if (row % 2 == 0) {
                color = lineDark;
            }
        } else {
            if (isSelected) {
                //color = lineSelected;
                if (days[column - 4] == 0) {
                    color = lineSelWork;
                } else {
                    color = lineSelWeek;
                }
            } else if (days[column - 4] == 0) {
                if (row % 2 == 0) {
                    color = lineOddWork;
                } else {
                    color = lineEvenWork;
                }
            } else {
                if (row % 2 == 0) {
                    color = lineOddWeekend;
                } else {
                    color = lineEvenWeekend;
                }
            }
        }

        cell.setBackground(color);
        return cell;
    }
}
