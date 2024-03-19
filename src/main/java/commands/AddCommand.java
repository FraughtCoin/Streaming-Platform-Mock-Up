package commands;

import database.Database;

import java.util.Calendar;

public class AddCommand extends Command {

    public AddCommand(String line) {
        super(line);
    }

    @Override
    public void run() {
        Database database = Database.getInstance();
        StringBuilder streamName = new StringBuilder("'");

        for (int i = 4; i < args.length; i++) {
            streamName.append(args[i]).append(" ");
        }

        streamName = new StringBuilder(streamName.substring(0, streamName.length() - 1));
        streamName.append("'");
        database.insertInto("streams",
                new String[]{"streamType", "id", "streamGenre", "noOfStreams",
                        "streamerId", "length", "dateAdded", "name"},
                new String[]{args[0], args[1], args[2], Integer.toString(0), Integer.toString(id), args[3],
                        Long.toString(Calendar.getInstance().getTime().getTime()), streamName.toString()});
    }
}
