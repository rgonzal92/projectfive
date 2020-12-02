package application.model;

import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;
import javafx.scene.control.TextArea;

public class YTDL{
    private static YTDL instance;
    public TextArea OutputText;
    private Thread thread;

    public static YTDL getInstance() {
        if (instance == null)
            instance = new YTDL();
        return instance;
    }

    public void setExecPath() {
        YoutubeDL.setExecutablePath(System.getProperty("user.dir") + "/lib/youtube-dl.exe");
    }

    public void getRequest(String[] videoLink, String saveDirectory) {
        YoutubeDLRequest[] requests = new YoutubeDLRequest[videoLink.length];
        for(int n=0;n<videoLink.length;n++){
            requests[n] = new YoutubeDLRequest(videoLink[n], saveDirectory);
            requests[n].setOption("config-location", System.getProperty("user.dir"));
        }
        RunExecute(requests);
        //YoutubeDLResponse response = getResponse(request);
        //return response.getOut();

    }
    public void RunExecute(YoutubeDLRequest[] requests){
        synchronized (this){
            if(thread!=null&&thread.isAlive()){
                System.err.println("Already downloading other videos");
                OutputText.appendText("Already downloading other videos");
                System.err.println(thread.toString());
                return;
            }
            YouTubeDLExecute execute = new YouTubeDLExecute(requests,OutputText);
            thread = new Thread(execute);
            thread.start();
        }
    }
    /*public void getRequest(String videoLink, String saveDirectory) {
        YoutubeDLRequest request = new YoutubeDLRequest(videoLink, saveDirectory);
        request.setOption("config-location", System.getProperty("user.dir"));
        //YoutubeDLResponse response = getResponse(request);
        //return response.getOut();
        getResponse(request);
    }

    public void getResponse(YoutubeDLRequest request) {
        synchronized (this){
            if(thread!=null&&thread.isAlive()){
                //System.err.println("Bruh moment");
                System.err.println("Already downloading another video");
                OutputText.appendText("Already downloading another video");
                System.err.println(thread.toString());
                return;
            }
            YouTubeDLExecute execute = new YouTubeDLExecute(request,OutputText);
            thread = new Thread(execute);
            thread.start();
        }


    }
    */

}
