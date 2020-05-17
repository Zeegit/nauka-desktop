package ru.zeet.model;

import ru.zeet.db.ConnectionDB;
import ru.zeet.model.base.MyModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeModel extends MyModel {

    public EmployeeModel() {
        super();

        setColumnCount(5);
        setSql("SELECT\n" +
                "    w.id,\n" +
                "    work_date,\n" +
                "    employee_id,\n" +
                "    work_code_id,\n" +
                "    e.first_name,\n" +
                "    e.last_name,\n" +
                "    c.name as \"work_code_name\"\n" +
                "from work_calendar w\n" +
                "join employee e on e.id = w.employee_id\n" +
                "join work_code c on c.id = w.work_code_id");

        addColumnName("id");
        addColumnName("Имя");
        addColumnName("Фамилия");
        addColumnName("Дата");
        addColumnName("Код");
    }

    @Override
    public void addData(ConnectionDB conn) {
        ResultSet rs = conn.resultSetQuery(getSql());
        try {
            while (rs.next()) {
                String[] row = {
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("work_date"),
                        rs.getString("work_code_name"),
                        rs.getString("id"),
                        rs.getString("employee_id"),
                        rs.getString("work_code_id"),
                };
                addData(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


