package factory;

import java.io.File;

public class DatabaseTableFactory {


    public void createTable(File f) {
        switch (f.getName()) {
            case "streamers.csv": {
                CreateStreamerTable c = new CreateStreamerTable();
                c.createTable();
                c.populateTable(f);
                break;
            } case "streams.csv": {
                CreateStreamTable c = new CreateStreamTable();
                c.createTable();
                c.populateTable(f);
                break;
            } case "users.csv": {
                CreateUserTable c = new CreateUserTable();
                c.createTable();
                c.populateTable(f);
                break;
            } default: {
                throw new RuntimeException("Unknown file");
            }
        }
    }

}
