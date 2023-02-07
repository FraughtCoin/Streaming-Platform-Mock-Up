package models;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Stream {

    private int streamType;
    private int id;
    private int streamGenre;
    private long noOfStreams;
    private int streamerId;
    private long length;
    private long dateAdded;
    private String name;

    public Stream(ResultSet resultSet) {
        try {
            streamType = resultSet.getInt("streamType");
            id = resultSet.getInt("id");
            streamGenre = resultSet.getInt("streamGenre");
            noOfStreams = resultSet.getLong("noOfStreams");
            streamerId = resultSet.getInt("streamerId");
            length = resultSet.getLong("length");
            dateAdded = resultSet.getLong("dateAdded");
            name = resultSet.getString("name");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getStreamType() {
        return streamType;
    }

    public int getId() {
        return id;
    }

    public int getStreamGenre() {
        return streamGenre;
    }

    public long getNoOfStreams() {
        return noOfStreams;
    }

    public int getStreamerId() {
        return streamerId;
    }

    public long getLength() {
        return length;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "Stream{" +
                "streamType=" + streamType +
                ", id=" + id +
                ", streamGenre=" + streamGenre +
                ", noOfStreams=" + noOfStreams +
                ", streamerId=" + streamerId +
                ", length=" + length +
                ", dateAdded=" + dateAdded +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stream stream = (Stream) o;
        return id == stream.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
