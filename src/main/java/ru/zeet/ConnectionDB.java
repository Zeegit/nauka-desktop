package ru.zeet;

import java.sql.*;

public class ConnectionDB {
    private final String host;
    private final String user;
    private final String password;
    Connection connection;
    private String dbname;
    private String url;

    public ConnectionDB(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public void setDBName(String dbname) {
        this.dbname = dbname;
    }

    public void initProperties() {
        this.url = "jdbc:postgresql://" + this.host + ":5432/" + this.dbname;
    }

    public int init() {
        initProperties();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, this.user, this.password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public ResultSet resultSetQuery(String sql) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet resultSetQuery(String sql, Integer... params) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                statement.setInt(i + 1, params[i]);
            }

            ResultSet rs = statement.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void executeUpdate(String sql, String... params) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                statement.setString(i + 1, params[i]);
            }
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void executeUpdate(String sql) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
