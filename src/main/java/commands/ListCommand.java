package commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import database.Database;
import models.Stream;
import models.Streamer;
import models.User;
import utils.JsonUtils;

import java.util.List;

public class ListCommand extends Command {
    public ListCommand(String line) {
        super(line);
    }


    @Override
    public void run() {
        Database database = Database.getInstance();
        Streamer streamer = database.getStreamerById(id);
        User user = database.getUserById(id);
        List<Stream> streams;
        JsonArray jsonArray = new JsonArray();

        if (streamer != null) {
            streams = streamer.getStreamList();
            for (Stream s : streams) {
                JsonObject jsonObject = JsonUtils.streamToJsonObject(s, streamer);
                jsonArray.add(jsonObject);
            }
        } else {
            streams = user.getStreamList();
            for (Stream s : streams) {
                Streamer temp = database.getStreamerById(s.getStreamerId());
                JsonObject jsonObject = JsonUtils.streamToJsonObject(s, temp);
                jsonArray.add(jsonObject);
            }
        }
        System.out.println(jsonArray);
    }
}
