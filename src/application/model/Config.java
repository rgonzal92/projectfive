package application.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Config {
    private static Config instance;
    private String videoFormat;
    private String audioFormat;
    private String videoResolution;
    private boolean audioOnly;
    private boolean writeDescription;
    private boolean writeThumbnails;
    private boolean writeinfoJsons;
    private boolean noOverWriting;
    private boolean acceptPlayLists;
    private String fileOutput;

    public Config() {
        this.setVideoFormat("mp4");
        this.setAudioFormat("m4a");
        this.setVideoResolution("1080");
        this.setFileOutput("%(title)s");
    }

    public void writeConfig() throws IOException {
        System.out.println("Writing Config");
        FileWriter fileWriter = new FileWriter(getConfigPath());
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.printf("-f \"bestvideo[height<=%s][ext=%s]+bestaudio[ext=%s]\"\n", getVideoResolution(), getVideoFormat(), getAudioFormat());
        if(audioOnly){
            printWriter.println("-x");
        }
        if(writeDescription){
            printWriter.println("--write-description");
        }
        if(writeThumbnails){
            printWriter.println("--write-thumbnail");
        }
        if(noOverWriting){
            printWriter.println("--no-overwrites");
        }
        if(acceptPlayLists){
            printWriter.println("--yes-playlist");
        }else{
            printWriter.println("--no-playlist");
        }

        printWriter.close();
    }

    public void loadConfig(String filePath) throws IOException {
        
    }

    private String getConfigPath() {
        //return System.getProperty("user.home") + "\\youtube-dl.conf";
        //System.err.println(System.getProperty("user.home") + "\\youtube-dl.conf");
        //return System.getProperty("user.home") + "\\youtube-dl.conf";
        System.err.println(System.getProperty("user.dir")+"\\youtube-dl.conf");
        return System.getProperty("user.dir")+"\\youtube-dl.conf";
    }

    public static Config getInstance() {
        if (instance == null)
            instance = new Config();
        return instance;
    }

    public void setWriteDescription(boolean writeDescription) {
        this.writeDescription = writeDescription;
    }

    public void setNoOverWriting(boolean noOverWriting) {
        this.noOverWriting = noOverWriting;
    }

    public void setAudioOnly(boolean audioOnly) {
        this.audioOnly = audioOnly;
    }

    public void setWriteThumbnails(boolean writeThumbnails) {
        this.writeThumbnails = writeThumbnails;
    }

    public void setAcceptPlayLists(boolean acceptPlayLists) {
        this.acceptPlayLists = acceptPlayLists;
    }

    public void setWriteinfoJsons(boolean writeinfoJsons) {
        this.writeinfoJsons = writeinfoJsons;
    }

    public String getVideoFormat() {
        return videoFormat;
    }

    public void setVideoFormat(String videoFormat) {
        this.videoFormat = videoFormat;
    }

    public String getAudioFormat() {
        return audioFormat;
    }

    public void setAudioFormat(String audioFormat) {
        this.audioFormat = audioFormat;
    }

    public String getVideoResolution() {
        return videoResolution;
    }

    public void setVideoResolution(String videoResolution) {
        this.videoResolution = videoResolution;
    }

    public String getFileOutput() {
        return fileOutput;
}

    public void setFileOutput(String fileOutput) {
        this.fileOutput = fileOutput;
    }
}
