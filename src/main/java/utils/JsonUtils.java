package utils;

import com.google.gson.JsonObject;
import models.Stream;
import models.Streamer;

public final class JsonUtils {

    private JsonUtils() {

    }

    public static JsonObject streamToJsonObject(Stream stream, Streamer streamer) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", Integer.toString(stream.getId()));
        jsonObject.addProperty("name", stream.getName());
        jsonObject.addProperty("streamerName", streamer.getName());
        jsonObject.addProperty("noOfListenings", Long.toString(stream.getNoOfStreams()));
        jsonObject.addProperty("length", DateUtils.secondsToLength(stream.getLength()));
        jsonObject.addProperty("dateAdded", DateUtils.secondsToDate(stream.getDateAdded()));

        return jsonObject;
    }
}
