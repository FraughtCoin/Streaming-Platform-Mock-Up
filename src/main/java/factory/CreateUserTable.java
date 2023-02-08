package factory;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import database.Database;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class CreateUserTable implements CreateTableInterface {

    @Override
    public void createTable() {
        Database database = Database.getInstance();
        database.createTable("users",
                new String[]{"id", "name"},
                new String[]{"int", "varchar(255)"});

        database.createTable("usersAndStreams",
                new String[]{"userId", "streamId"},
                new String[]{"int", "int"});
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
                columnValues[1] = "'" + entry[1].replace("'", "''") + "'";
                String[] columns = new String[]{"id", "name"};
                String[] temp = columnValues;
                columnValues = new String[2];
                columnValues[0] = temp[0];
                columnValues[1] = temp[1];

                database.insertInto("users", columns, columnValues);

                String[] streamIds = entry[2].split(" ");
                for (String s : streamIds) {
                    database.insertInto("usersAndStreams",
                            new String[]{"userId", "streamId"},
                            new String[]{entry[0], s});
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
