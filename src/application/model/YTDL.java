package application.model;

import com.sapher.youtubedl.*;
import com.sapher.youtubedl.utils.*;

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
        //System.out.println(getResponse(request));
        request.setOption("config-location",System.getProperty("user.dir")+"\\youtube-dl.conf");
        YoutubeDLResponse response=getResponse(request);
        System.out.println("Command:"+response.getCommand()+"\nWas saved to:"+response.getDirectory()+"\nIn Elapsed time:"+response.getElapsedTime()/1000+"s");
        System.out.println(response.getOut());
    }

    public YoutubeDLResponse getResponse(YoutubeDLRequest request) throws YoutubeDLException {
        YoutubeDLResponse response = /*YoutubeDL.execute(request);*/YoutubeDL.execute(request, new DownloadProgressCallback() {
            @Override
            public void onProgressUpdate(float progress, long etaInSeconds) {
                System.out.println(progress+"%"+","+etaInSeconds+"s");
            }

        });

        return response;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = System.getProperty(outputDirectory);
    }
}
