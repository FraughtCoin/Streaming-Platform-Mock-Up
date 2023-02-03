package database;

import utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String DB_URL = "jdbc:mysql://sql7.freesqldatabase.com";
    private static final String USE_DB_NAME = "USE sql7595220";
    private static final String USER = "sql7595220";
    private static final String PASS = "YidQLMnTUQ";

    private static Database database;

    private Database() {

    }

    public static Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public void dropAllTables() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            statement.execute(USE_DB_NAME);

            String query = "SELECT CONCAT('DROP TABLE ', TABLE_NAME, ';') " +
                    "FROM INFORMATION_SCHEMA.tables " +
                    "WHERE TABLE_SCHEMA = 'sql7595220'";
            ResultSet resultSet = statement.executeQuery(query);
            List<String> queryList = new ArrayList<>();

            while (resultSet.next()) {
                queryList.add(resultSet.getString(1));
            }
            resultSet.close();
            for (String s : queryList) {
                statement.execute(s);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void createTable(String tableName, String[] columns, String[] columnsType) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            String query = DatabaseUtils.createTableQuery(tableName, columns, columnsType);

            statement.execute(USE_DB_NAME);
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertInto(String tableName, String[] columns, String[] columnValues) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            String query = DatabaseUtils.insertIntoQuery(tableName, columns, columnValues);

            statement.execute(USE_DB_NAME);
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getStreamerById(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            String query = DatabaseUtils.selectFromWhereEqualsQuery("streamers", "id", Integer.toString(id));

            statement.execute(USE_DB_NAME);
            ResultSet resultSet = statement.executeQuery(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
