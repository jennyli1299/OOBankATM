package src;

import java.time.LocalDateTime;

public class Time {

    private static LocalDateTime currentTime = LocalDateTime.now();

    public static LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public static void setCurrentTime(LocalDateTime time) {
        currentTime = time;
    }

}