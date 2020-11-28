package application.model;

import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;

public class YTDL {
    private static YTDL instance;
    private String outputDirectory;

    public YTDL() {
        this.setOutputDirectory("user.dir");
    }

    public static YTDL getInstance() {
        if (instance == null)
            instance = new YTDL();
        return instance;
    }

    public void setExecPath() {
        YoutubeDL.setExecutablePath("lib/youtube-dl.exe");
    }

    public void getRequest(String videoLink) throws YoutubeDLException {
        YoutubeDLRequest request = new YoutubeDLRequest(videoLink, this.getOutputDirectory());

        System.out.println(getResponse(request));
    }

    public String getResponse(YoutubeDLRequest request) throws YoutubeDLException {
        YoutubeDLResponse response = YoutubeDL.execute(request);
        return response.getOut();
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = System.getProperty(outputDirectory);
    }
}
