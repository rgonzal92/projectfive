package application.model;

import com.sapher.youtubedl.*;
import javafx.scene.control.*;

import javax.xml.soap.Text;

public class YouTubeDLExecute implements Runnable{
    public YoutubeDLRequest request;
    public TextArea OutputText;
    public YoutubeDLResponse response;
    public YouTubeDLExecute(YoutubeDLRequest ytreq, TextArea texta){
        setOutputText(texta);
        setRequest(ytreq);
    }
    public TextArea getOutputText() {
        return OutputText;
    }

    public void setOutputText(TextArea outputText) {
        OutputText = outputText;
    }

    public YoutubeDLRequest getRequest() {
        return request;
    }

    public void setRequest(YoutubeDLRequest request) {
        this.request = request;
    }

    @Override
    public void run() {
        try{
            response=YoutubeDL.execute(request, (progress, etaInSeconds) -> {
                String s = progress + "%" + "," + etaInSeconds + "s";
                System.out.println(s);
                OutputText.appendText(s + "\n");
            });
            OutputText.appendText(response.getOut().replaceAll("\\r", "\n"));
        }catch(YoutubeDLException e){
            e.printStackTrace();
            OutputText.appendText(e.toString());
        }

       //notify();
    }
}
