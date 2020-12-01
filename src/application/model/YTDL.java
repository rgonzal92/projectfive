package application.model;

import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;
import javafx.scene.control.TextArea;

public class YTDL {
    private static YTDL instance;
    public TextArea OutputText;

    public static YTDL getInstance() {
        if (instance == null)
            instance = new YTDL();
        return instance;
    }

    public void setExecPath() {
        YoutubeDL.setExecutablePath(System.getProperty("user.dir") + "/lib/youtube-dl.exe");
    }

    public String getRequest(String videoLink, String saveDirectory) throws YoutubeDLException {
        YoutubeDLRequest request = new YoutubeDLRequest(videoLink, saveDirectory);
        request.setOption("config-location", System.getProperty("user.dir"));
        YoutubeDLResponse response = getResponse(request);
        return response.getOut();
    }

    public YoutubeDLResponse getResponse(YoutubeDLRequest request) throws YoutubeDLException {
        return YoutubeDL.execute(request, (progress, etaInSeconds) -> {
            String s = progress + "%" + "," + etaInSeconds + "s";
            //System.out.println(s);
            OutputText.appendText(s + "\n");
        });
    }
}
