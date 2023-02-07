package commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import database.Database;
import models.Stream;
import models.Streamer;
import models.User;
import utils.DateUtils;
import utils.JsonUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListCommand extends Command {
    public ListCommand(String line) {
        super(line);
    }

    //    [{"id":"53873","name":"Shape of You","streamerName":"Ed Sheeran","noOfListenings":"294","length":"01:36","dateAdded":"22-11-2017"}]

    @Override
    public void run() {
        Database database = Database.getInstance();
        Streamer streamer = database.getStreamerById(id);
        User user = database.getUserById(id);
        if (streamer != null) {
            List<Stream> streams = streamer.getStreamList();
            JsonArray jsonArray = new JsonArray();
            for (Stream s : streams) {
                JsonObject jsonObject = JsonUtils.streamToJsonObject(s, streamer);
                jsonArray.add(jsonObject);
            }
            System.out.println(jsonArray);
        } else {
            List<Stream> streams = user.getStreamList();
            JsonArray jsonArray = new JsonArray();
            for (Stream s : streams) {
                Streamer temp = database.getStreamerById(s.getStreamerId());
                JsonObject jsonObject = JsonUtils.streamToJsonObject(s, temp);
                jsonArray.add(jsonObject);
            }
            System.out.println(jsonArray);
        }
    }
}
