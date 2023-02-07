package database;

import models.Stream;
import models.Streamer;
import models.User;
import utils.DatabaseUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String DB_URL = "jdbc:mysql://sql7.freesqldatabase.com";
    private static final String USE_DB_NAME = "USE sql7596417";
    private static final String USER = "sql7596417";
    private static final String PASS = "xm5SQA6mtn";

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
                    "WHERE TABLE_SCHEMA = 'sql7596417'";
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

            try (PrintWriter p = new PrintWriter(new BufferedWriter(new FileWriter("text.txt")))) {
                p.println(query);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            statement.execute(USE_DB_NAME);
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Streamer getStreamerById(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            String query = DatabaseUtils.selectFromWhereEqualsQuery("streamers", "id", Integer.toString(id));

            statement.execute(USE_DB_NAME);
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                return null;
            }
            Streamer streamer = new Streamer(resultSet);
            resultSet.close();
            return streamer;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserById(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            String query = DatabaseUtils.selectFromWhereEqualsQuery("users", "id", Integer.toString(id));

            statement.execute(USE_DB_NAME);
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                return null;
            }
            User user = new User(resultSet);
            resultSet.close();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Stream getStreamById(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            String query = DatabaseUtils.selectFromWhereEqualsQuery("streams", "id", Integer.toString(id));

            statement.execute(USE_DB_NAME);
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                return null;
            }
            Stream stream = new Stream(resultSet);
            resultSet.close();
            return stream;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Stream> getStreamsByStreamerId(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            String query = DatabaseUtils.selectFromWhereEqualsQuery("streams", "streamerId", Integer.toString(id));

            statement.execute(USE_DB_NAME);
            ResultSet resultSet = statement.executeQuery(query);

            ArrayList<Stream> streamList = new ArrayList<>();
            while (resultSet.next()) {
                Stream stream = new Stream(resultSet);
                streamList.add(stream);
            }
            resultSet.close();
            return streamList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Stream> getStreamsByUserId(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            String query = DatabaseUtils.selectFromWhereEqualsQuery("usersAndStreams", "userId", Integer.toString(id));

            statement.execute(USE_DB_NAME);
            ResultSet resultSet = statement.executeQuery(query);

            List<Stream> streamList = new ArrayList<>();

            List<Integer> idList = new ArrayList<>();
            while (resultSet.next()) {
                int streamId = resultSet.getInt("streamId");
                idList.add(streamId);
            }
            resultSet.close();
            for (Integer i : idList) {
                query = DatabaseUtils.selectFromWhereEqualsQuery("streams", "id", Integer.toString(i));
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    Stream stream = new Stream(resultSet);
                    streamList.add(stream);
                }
                resultSet.close();
            }
            return streamList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Stream> getStreamsByStreamerIdAndType(int id, int type) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            String query = DatabaseUtils.selectFromWhere2EqualsQuery("streams", "streamerId", Integer.toString(id),
                    "streamType", Integer.toString(type));

            statement.execute(USE_DB_NAME);
            ResultSet resultSet = statement.executeQuery(query);

            ArrayList<Stream> streamList = new ArrayList<>();
            while (resultSet.next()) {
                Stream stream = new Stream(resultSet);
                streamList.add(stream);
            }
            resultSet.close();
            return streamList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Streamer> getAllStreamers() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM streamers";

            statement.execute(USE_DB_NAME);
            ResultSet resultSet = statement.executeQuery(query);

            List<Streamer> streamers = new ArrayList<>();
            while (resultSet.next()) {
                streamers.add(new Streamer(resultSet));
            }

            resultSet.close();
            return streamers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStream(int streamerId, int streamId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            String query = DatabaseUtils.deleteFromWhere2EqualsQuery("streams", "id", Integer.toString(streamId),
                    "streamerId", Integer.toString(streamerId));

            statement.execute(USE_DB_NAME);
            statement.execute(query);

            query = DatabaseUtils.deleteFromWhereEqualsQuery("usersAndStreams", "streamId", Integer.toString(streamId));
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStream(int streamId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            statement.execute(USE_DB_NAME);
            Stream stream = getStreamById(streamId);
            long noOfStreams = stream.getNoOfStreams();

            String query = DatabaseUtils.updateColumnWhere("streams", "noOfStreams",
                    Long.toString(noOfStreams), Long.toString(++noOfStreams));

            statement.execute(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
