package commands;

import database.Database;

public class ListenCommand extends Command{

    public ListenCommand(String line) {
        super(line);
    }

    @Override
    public void run() {
        Database database = Database.getInstance();
        database.insertInto("usersAndStreams", new String[]{"userId", "streamId"},
                new String[]{Integer.toString(id), args[0]});
        database.updateStream(Integer.parseInt(args[0]));
    }
}
