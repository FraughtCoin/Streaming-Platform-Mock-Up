package factory;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import database.Database;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CreateStreamerTable implements CreateTableInterface {

    @Override
    public void createTable() {
        Database database = Database.getInstance();
        database.createTable("streamers",
                new String[]{"streamerType", "id", "name"},
                new String[]{"int", "int", "varchar(255)"});
    }

    @Override
    public void populateTable(File f) {
        Database database = Database.getInstance();
        try (BufferedReader reader = Files.newBufferedReader(Path.of(f.getPath()));
             CSVReader csvReader = new CSVReader(reader)) {

            csvReader.readNext();

            String[] entry;
            while ((entry = csvReader.readNext()) != null) {
                String[] columnValues = entry;
                columnValues[2] = "'" + entry[2].replace("'", "''") + "'";
                String[] columns = new String[]{"streamerType", "id", "name"};
                database.insertInto("streamers", columns, columnValues);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }

    }
}
