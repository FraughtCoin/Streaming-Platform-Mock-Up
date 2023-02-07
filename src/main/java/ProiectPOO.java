import commands.*;
import database.Database;
import database.DatabaseInitializer;

import java.io.*;


public class ProiectPOO {

    public static void main(String[] args) {
        if (args == null) {
            System.out.println("Nothing to read here");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        DatabaseInitializer initializer = new DatabaseInitializer();
        Database database = initializer.initializeDatabase(args);

        try (FileReader fr = new FileReader("src/main/resources/" + args[3]);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            while ((line = br.readLine()) != null) {

                Command commandToRun;
                switch (line.split(" ")[1]) {
                    case "LIST": {
                        commandToRun = new ListCommand(line);
                        break;
                    } case "ADD": {
                        commandToRun = new AddCommand(line);
                        break;
                    } case "DELETE": {
                        commandToRun = new DeleteCommand(line);
                        break;
                    } case "LISTEN": {
                        commandToRun = new ListenCommand(line);
                        break;
                    } case "RECOMMEND": {
                        commandToRun = new RecommendCommand(line);
                        break;
                    } case "SURPRISE": {
                        commandToRun = new SurpriseCommand(line);
                        break;
                    }
                    default: {
                        throw new RuntimeException("Command does not exist");
                    }
                }

                commandToRun.run();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        ListCommand l1 = new ListCommand(137);
//        l1.run();
    }

}

