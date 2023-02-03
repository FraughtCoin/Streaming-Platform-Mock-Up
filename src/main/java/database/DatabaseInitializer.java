package database;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DatabaseInitializer {

    private static final String[] streamersColumns = new String[]{"streamerType", "id", "name"};
    private static final String[] streamsColumns = new String[]{"streamType", "id", "streamGenre", "noOfStreams",
            "streamerId", "length", "dateAdded", "name"};
    private static final String[] usersColumns = new String[]{"id", "name"};

    public Database initializeDatabase(File inputDir) {
        Database database = Database.getInstance();
        database.dropAllTables();

        database.createTable("streamers",
                streamersColumns,
                new String[]{"int", "int", "varchar(255)"});

        database.createTable("streams",
                streamsColumns,
                new String[]{"int", "int", "int", "bigint", "int", "bigint", "bigint", "varchar(255)"});

        database.createTable("users",
                usersColumns,
                new String[]{"int", "varchar(255)"});

        database.createTable("usersAndStreams",
                new String[]{"userId", "streamId"},
                new String[]{"int", "int"});

        for (File f : inputDir.listFiles()) {
            try (BufferedReader reader = Files.newBufferedReader(Path.of(f.getPath()));
                 CSVReader csvReader = new CSVReader(reader)) {

                String fileName = f.getName();
                String tableName = fileName.split("\\.")[0];
                csvReader.readNext();

                String[] entry;
                while ((entry = csvReader.readNext()) != null) {
                    String[] columns = new String[0];
                    String[] columnValues = entry;
                    switch (fileName) {
                        case "streamers.csv": {
                            columnValues[2] = "'" + entry[2] + "'";
                            columns = streamersColumns;
                            break;
                        }
                        case "streams.csv": {
                            columnValues[7] = "'" + entry[7] + "'";
                            columns = streamsColumns;
                            break;
                        }
                        case "users.csv": {
                            columnValues[1] = "'" + entry[1] + "'";
                            String[] temp = columnValues;
                            columnValues = new String[2];
                            columnValues[0] = temp[0];
                            columnValues[1] = temp[1];
                            columns = usersColumns;
                            break;
                        }
                        default: {
                            break;
                        }
                    }

                    database.insertInto(tableName, columns, columnValues);
                    if (fileName.equals("users.csv")) {
                        String[] streamIds = entry[2].split(" ");
                        for (String s : streamIds) {
                            database.insertInto("usersAndStreams",
                                    new String[]{"userId", "streamId"},
                                    new String[]{entry[0], s});
                        }
                    }

                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (CsvValidationException e) {
                throw new RuntimeException(e);
            }
        }


        return database;
    }
}
