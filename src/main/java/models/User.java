package models;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String name;
    private List<Stream> streamList;

    public User(ResultSet resultSet) {
        Database database = Database.getInstance();
        try {
            id = resultSet.getInt("id");
            name = resultSet.getString("name");
            streamList = database.getStreamsByUserId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Stream> getStreamList() {
        return streamList;
    }

    public void setStreamList(List<Stream> streamList) {
        this.streamList = streamList;
    }
}
