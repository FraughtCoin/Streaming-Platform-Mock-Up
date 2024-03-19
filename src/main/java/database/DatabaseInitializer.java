package database;

import factory.DatabaseTableFactory;

import java.io.File;
import java.util.ArrayList;

public class DatabaseInitializer {

    private static final String PATH = "src/main/resources/";

    public Database initializeDatabase(String[] args) {
        Database.reset();
        Database database = Database.getInstance();
        database.dropAllTables();
        ArrayList<File> inputs = new ArrayList<>();
        inputs.add(new File(PATH + args[0]));
        inputs.add(new File(PATH + args[1]));
        inputs.add(new File(PATH + args[2]));
        DatabaseTableFactory databaseTableFactory = new DatabaseTableFactory();
        for (File f : inputs) {
            databaseTableFactory.createTable(f);
        }


        return database;
    }
}
