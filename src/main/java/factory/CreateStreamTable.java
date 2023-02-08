package factory;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import database.Database;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CreateStreamTable implements CreateTableInterface{

    @Override
    public void createTable() {
        Database database = Database.getInstance();
        database.createTable("streams",
                new String[]{"streamType", "id", "streamGenre", "noOfStreams",
                        "streamerId", "length", "dateAdded", "name"},
                new String[]{"int", "int", "int", "bigint", "int", "bigint", "bigint", "varchar(255)"});
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
                columnValues[7] = "'" + entry[7].replace("'", "''") + "'";
                String[] columns = new String[]{"streamType", "id", "streamGenre", "noOfStreams",
                        "streamerId", "length", "dateAdded", "name"};
                database.insertInto("streams", columns, columnValues);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

    }
}
