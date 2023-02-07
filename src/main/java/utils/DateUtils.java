package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    public static String secondsToLength(long seconds) {
        String time = "";
        if (seconds / 3600 > 0) {
            time = time + (seconds / 3600) + ":";
        }
        if (seconds / 60 % 60 <= 9) {
            time = time + "0" + (seconds / 60 % 60) + ":";
        } else {
            time = time + (seconds / 60 % 60) + ":";
        }
        if (seconds % 60 <= 9) {
            time = time + "0";
        }
        return time + (seconds % 60);

//        return new SimpleDateFormat("mm:ss").format(new Date(seconds * 1000));
    }

    public static String secondsToDate(long seconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new Date(seconds * 1000));
    }
}
