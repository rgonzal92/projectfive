package application.model;

import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;
import javafx.scene.control.TextArea;

/**
 * Contains variables and methods for handling the execution in the youtub-dl.exe
 * Variable: requests(YoutubeDLRequest[]), OutputText(TextArea), response(YoutubeDLResponse)
 * 
 * @author Richard Gonzalez
 * @author Shejan Shuza
 * @author Juan-Carlos Rodriguez
 * @author Collin Behunin
 * @author Jacob De Hoyos
 */
public class YouTubeDLExecute implements Runnable{
    public YoutubeDLRequest[] requests;
    public TextArea OutputText;
    public YoutubeDLResponse response;
    
    /**
     * Takes two variables of YoutubeDLExecute information.
     * 
     * @param ytreq		an array of video download requests.
     * @param texta		the textArea the output will be written to.
     */
    public YouTubeDLExecute(YoutubeDLRequest[] ytreq, TextArea texta){
        setOutputText(texta);
        setRequests(ytreq);
    }

    /**
     * Returns the TextArea output will be written to.
     * 
     * @return		the TextArea the output will be written to.
     */
    public TextArea getOutputText() {
        return OutputText;
    }

    /**
     * Sets a TextArea into the OutputText variable.
     * 
     * @param outputText	The TextArea for the OutputText variable.
     */
    public void setOutputText(TextArea outputText) {
        OutputText = outputText;
    }

    /**
     * Returns the array of video download requests.
     * 
     * @return		an Array of YoutubeDLRequests.
     */
    public YoutubeDLRequest[] getRequests() {
        return requests;
    }

    /**
     * Sets an array of YoutubeDLRequests into the request array.
     * 
     * @param request	The array for the request variable. 
     */
    public void setRequests(YoutubeDLRequest[] request) {
        this.requests = request;
    }
    
    /**
     * Executes the youtube-dl.exe with the array of YoutubeDLRequests.
     */
    @Override
    public void run() {
        for (YoutubeDLRequest request : requests) {
            OutputText.appendText("Downloading " + request.getUrl() + "\n");
            try {
                response = YoutubeDL.execute(request, (progress, etaInSeconds) -> {
                    String s = progress + "%" + "," + etaInSeconds + "s";
                    System.out.println(s);
                    OutputText.appendText(s + "\n");
                });
                OutputText.appendText("OUTPUT OF YOUTUBE-DL EXE:\n");
                OutputText.appendText(response.getOut().replaceAll("\\r", "\n")); //Java can't use \r as a method of storing enter
                System.out.println(response.getOut());
            } catch (YoutubeDLException e) {
                e.printStackTrace();
                OutputText.appendText(e.toString() + "\n");
            }
        }
    }
}
