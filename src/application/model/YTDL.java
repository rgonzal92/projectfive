package application.model;

import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;

public class YTDL {
    private static YTDL instance;

    public static YTDL getInstance() {
        if (instance == null)
            instance = new YTDL();
        return instance;
    }

    public void setExecPath() {
        YoutubeDL.setExecutablePath("lib\\youtube-dl");
    }

    public String getRequest(String videoLink, String saveDirectory) throws YoutubeDLException {
        YoutubeDLRequest request = new YoutubeDLRequest(videoLink, saveDirectory);
        YoutubeDLResponse response = getResponse(request);
        return response.getOut();
    }

    public YoutubeDLResponse getResponse(YoutubeDLRequest request) throws YoutubeDLException {
        return YoutubeDL.execute(request, (progress, etaInSeconds) -> System.out.println(progress + "%" + "," + etaInSeconds + "s"));
    }
}
