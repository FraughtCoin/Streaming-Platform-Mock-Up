package models;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Streamer {

    private int streamerType;
    private int id;
    private String name;
    private List<Stream> streamList;

    public Streamer(ResultSet resultSet) {
        Database database = Database.getInstance();
        try {
            streamerType = resultSet.getInt("streamerType");
            id = resultSet.getInt("id");
            name = resultSet.getString("name");
            streamList = database.getStreamsByStreamerId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Stream> getStreamList() {
        return streamList;
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

    public void setStreamList(List<Stream> streamList) {
        this.streamList = streamList;
    }

}
