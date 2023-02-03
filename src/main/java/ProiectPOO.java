import database.Database;
import database.DatabaseInitializer;

import java.io.File;


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

        File inputDir = new File("src/main/resources/" + args[0]);
        File commandsFile = new File("src/main/resources/" + args[1] + "commands.txt");

        DatabaseInitializer initializer = new DatabaseInitializer();
        Database database = initializer.initializeDatabase(inputDir);
    }

}

