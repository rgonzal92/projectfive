package application.model;

import com.sapher.youtubedl.*;
import javafx.scene.control.*;

import javax.xml.soap.Text;

public class YouTubeDLExecute implements Runnable{
    //public YoutubeDLRequest request;
    public YoutubeDLRequest[] requests;
    public TextArea OutputText;
    public YoutubeDLResponse response;
    /*public YouTubeDLExecute(YoutubeDLRequest ytreq, TextArea texta){
        setOutputText(texta);
        setRequest(ytreq);
    }*/
    public YouTubeDLExecute(YoutubeDLRequest[] ytreq, TextArea texta){
        setOutputText(texta);
        setRequests(ytreq);
    }

    public TextArea getOutputText() {
        return OutputText;
    }

    public void setOutputText(TextArea outputText) {
        OutputText = outputText;
    }

    public YoutubeDLRequest[] getRequests() {
        return requests;
    }

    public void setRequests(YoutubeDLRequest[] request) {
        this.requests = request;
    }
    /*public YoutubeDLRequest getRequest() {
        return request;
    }

    public void setRequest(YoutubeDLRequest request) {
        this.request = request;
    }*/

   /* @Override
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
    }*/
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
       /*try{
           response=YoutubeDL.execute(request, (progress, etaInSeconds) -> {
               String s = progress + "%" + "," + etaInSeconds + "s";
               System.out.println(s);
               OutputText.appendText(s + "\n");
           });
           OutputText.appendText(response.getOut().replaceAll("\\r", "\n"));
       }catch(YoutubeDLException e){
           e.printStackTrace();
           OutputText.appendText(e.toString());
       }*/

       //notify();
   }
}
