package application.model;

import java.util.HashMap;

public class Video {
    private static Video instance = null;
    HashMap<String, String> videoQuality;

    public static Video getInstance() {
        if (instance == null)
            instance = new Video();
        return instance;
    }

    public HashMap<String, String> getVideoQuality() {
        return videoQuality;
    }

    public void setVideoQuality(HashMap<String, String> videoQuality) {
        this.videoQuality = videoQuality;
    }
}
