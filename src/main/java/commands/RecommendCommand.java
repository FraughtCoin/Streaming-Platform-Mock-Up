package commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import comparators.StreamComparatorByViews;
import database.Database;
import models.Stream;
import models.User;
import utils.JsonUtils;

import java.util.*;

public class RecommendCommand extends Command {

    public RecommendCommand(String line) {
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
        Set<Integer> listenedStreamersIdSet = new TreeSet<>();

        for (Stream s : user.getStreamList()) {
            listenedStreamersIdSet.add(s.getStreamerId());
        }

        List<Stream> streamsByListenedStreamers = new ArrayList<>();

        for (Integer i : listenedStreamersIdSet) {
            List<Stream> temp = database.getStreamsByStreamerIdAndType(i, streamType);
            streamsByListenedStreamers.addAll(temp);
        }

        for (Stream s : user.getStreamList()) {
            streamsByListenedStreamers.remove(s);
        }

        streamsByListenedStreamers.sort(new StreamComparatorByViews());

        int i = 0;
        JsonArray jsonArray = new JsonArray();
        for (Stream s : streamsByListenedStreamers) {
            if (i == 5) {
                break;
            }
            JsonObject jsonObject = JsonUtils.streamToJsonObject(s, database.getStreamerById(s.getStreamerId()));
            jsonArray.add(jsonObject);
            i++;
        }

        System.out.println(jsonArray);
    }
}
