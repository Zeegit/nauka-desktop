package ru.zeet.model;

import ru.zeet.db.ConnectionDB;
import ru.zeet.model.base.MyModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeModel extends MyModel {

    public EmployeeModel() {
        super();

        setColumnCount(12);
        setSql("SELECT \n" +
                "    e.id,\n" +
                "    service_number,\n" +
                "    first_name,\n" +
                "    last_name,\n" +
                "    birth_date,\n" +
                "    sex,\n" +
                "    CASE\n" +
                "        WHEN sex THEN 'м'\n" +
                "        ELSE 'ж'\n" +
                "    END AS sex_name,\n" +
                "    remote,\n" +
                "    CASE\n" +
                "        WHEN remote THEN 'да'\n" +
                "        ELSE 'нет'\n" +
                "    END AS remote_name,\n" +
                "    address,\n" +
                "    photo oid,\n" +
                "    position_id,\n" +
                "    department_id,\n" +
                "    p.name as position_name,\n" +
                "    d.name as department_name\n" +
                "from employee e\n" +
                "join position p on p.id = e.position_id\n" +
                "join department d on d.id = e.department_id");

        addColumnName("Табельный номер");
        addColumnName("Имя");
        addColumnName("Фамилия");
        addColumnName("Департамент");
        addColumnName("Должность");
        addColumnName("Дата рождения");
        addColumnName("Пол");
        addColumnName("Удаленка");
        addColumnName("Адрес");
        addColumnName("id");
        addColumnName("position_id");
        addColumnName("department_id");

    }

    @Override
    public void addData(ConnectionDB conn) {
        ResultSet rs = conn.resultSetQuery(getSql());
        try {
            while (rs.next()) {
                String[] row = {
                        rs.getString("service_number"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("department_name"),
                        rs.getString("position_name"),
                        rs.getString("birth_date"),
                        rs.getString("sex_name"),
                        rs.getString("remote_name"),
                        rs.getString("address"),
                        rs.getString("id"),
                        rs.getString("position_id"),
                        rs.getString("department_id")
                };
                addData(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


