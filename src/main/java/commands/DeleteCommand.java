package commands;

import database.Database;

public class DeleteCommand extends Command{

    public DeleteCommand(String line) {
        super(line);
    }

    @Override
    public void run() {
        Database database = Database.getInstance();
        database.deleteStream(id, Integer.parseInt(args[0]));
    }
}
