package application.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Config {
    private static Config instance;
    private String videoFormat;
    private String audioFormat;
    private String videoResolution;
    private String fileOutput;

    public Config() {
        this.setVideoFormat("mp4");
        this.setAudioFormat("m4a");
        this.setVideoResolution("1080");
        this.setFileOutput("%(title)s");
    }

    public void writeConfig() throws IOException {
        FileWriter fileWriter = new FileWriter(getConfigPath());
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.printf("-f \"bestvideo[height<=%s][ext=%s]+bestaudio[ext=%s]\"%n", getVideoResolution(), getVideoFormat(), getAudioFormat());
        printWriter.printf("-o ~/Downloads/youtube-dl/%s", getFileOutput());
        printWriter.close();
    }

    public void loadConfig(String filePath) throws IOException {
        ;
    }

    private String getConfigPath() {
        return System.getProperty("user.home") + "\\youtube-dl.conf";
    }

    public static Config getInstance() {
        if (instance == null)
            instance = new Config();
        return instance;
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
