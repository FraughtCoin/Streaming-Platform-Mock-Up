package commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import comparators.StreamComparatorByDateAndViews;
import database.Database;
import models.Stream;
import models.Streamer;
import models.User;
import utils.JsonUtils;

import java.util.*;

public class SurpriseCommand extends  Command{

    public SurpriseCommand(String line) {
        super(line);
    }

    @Override
    public void run() {
        Database database = Database.getInstance();
        int streamType;
        switch (args[0]) {
            case "SONG": {
                streamType = 1;
                break;
            }
            case "PODCAST": {
                streamType = 2;
                break;
            }
            case "AUDIOBOOK": {
                streamType = 3;
                break;
            }
            default:
                throw new RuntimeException("Invalid stream type");
        }

        User user = database.getUserById(id);
        List<Streamer> allStreamers = database.getAllStreamers();

        Set<Integer> listenedStreamerIdSet = new TreeSet<>();
        for (Stream s : user.getStreamList()) {
            listenedStreamerIdSet.add(s.getStreamerId());
        }

        List<Stream> unwatchedStreams = new ArrayList<>();
        for (Streamer s : allStreamers) {
            if (!listenedStreamerIdSet.contains(s.getId())) {
                List<Stream> temp = database.getStreamsByStreamerIdAndType(s.getId(), streamType);
                unwatchedStreams.addAll(temp);
            }
        }

        unwatchedStreams.sort(new StreamComparatorByDateAndViews());

        int i = 0;
        JsonArray jsonArray = new JsonArray();
        for (Stream s : unwatchedStreams) {
            if (i == 3) {
                break;
            }
            JsonObject jsonObject = JsonUtils.streamToJsonObject(s, database.getStreamerById(s.getStreamerId()));
            jsonArray.add(jsonObject);
            i++;
        }

        System.out.println(jsonArray);
    }
}
