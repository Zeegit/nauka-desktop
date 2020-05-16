package ru.zeet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class Holidays {
    private String sql = "select hdate, holiday_type_id from holiday where extract(month from hdate) = ? and extract(year from hdate) = ?";

    public int[] get(ConnectionDB connect, int month, int year) {
        int[] h = new int[31];

        ResultSet rs = connect.resultSetQuery(sql, month, year);
        try {
            while (rs.next()) {
                int day = Instant.ofEpochMilli(rs.getDate("hdate").getTime()).atZone(ZoneId.systemDefault()).toLocalDate().getDayOfMonth();
                h[day-1] = rs.getInt("holiday_type_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return h;
    }
}
