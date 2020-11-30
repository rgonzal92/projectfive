package application.model;

import java.io.BufferedReader;
import java.io.FileReader;
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
    private boolean writeInfoJsons;
    private boolean noOverWriting;
    private boolean acceptPlayLists;
    private String saveDirectory;

    public void writeConfig() throws IOException {
        FileWriter fileWriter = new FileWriter(getConfigPath());
        PrintWriter printWriter = new PrintWriter(fileWriter);
        if (getAudioFormat().contains("Disabled"))
            printWriter.printf("--format \"bestvideo[height<=%s][ext=%s]\"\n", getVideoResolution(), getVideoFormat());
        else
            printWriter.printf("--format \"bestvideo[height<=%s][ext=%s]+bestaudio[ext=%s]\"\n", getVideoResolution(), getVideoFormat(), getAudioFormat());
        printWriter.println(getSaveDirectory());
        if (isAudioOnly()) printWriter.println("-x");
        if (isWriteDescription()) printWriter.println("--write-description");
        if (isWriteThumbnails()) printWriter.println("--write-thumbnail");
        if (isNoOverWriting()) printWriter.println("--no-overwrites");
        if (isAcceptPlayLists()) printWriter.println("--yes-playlist");
        else printWriter.println("--no-playlist");
        if (isWriteInfoJsons()) printWriter.println("--write-info-json");

        printWriter.close();
        fileWriter.close();
    }

    public void loadConfig() throws IOException {
        String[] videoFormats = {"3gp", "flv", "webm", "mp4"};
        String[] audioFormats = {"mp3", "wav", "acc", "m4a", "flac"};
        String[] videoResolutions = {"480", "720", "1080", "1440", "2160"};

        BufferedReader bufferedReader = new BufferedReader(new FileReader(getConfigPath()));
        String line = bufferedReader.readLine();
        for (String format : videoFormats)
            if (line.contains(format))
                setVideoFormat(format);
        for (String format : audioFormats)
            if (line.contains(format))
                setAudioFormat(format);
            else
                setAudioFormat("Disabled");
        for (String res : videoResolutions)
            if (line.contains(res))
                setVideoResolution(res);

        line = bufferedReader.readLine();
        setSaveDirectory(line);

        while ((line = bufferedReader.readLine()) != null) {
            if (line.equals("-x"))
                setAudioOnly(true);
            if (line.contains("write-description"))
                setWriteDescription(true);
            if (line.contains("write-thumbnail"))
                setWriteThumbnails(true);
            if (line.contains("no-overwrites"))
                setNoOverWriting(true);
            if (line.contains("yes-playlist"))
                setAcceptPlayLists(true);
            if (line.contains("no-playlist"))
                setAcceptPlayLists(false);
            if (line.contains("write-info-json"))
                setWriteInfoJsons(true);
        }
        bufferedReader.close();
    }

    private String getConfigPath() {
        return System.getProperty("user.dir") + "\\youtube-dl.conf";
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

    public boolean isAudioOnly() {
        return audioOnly;
    }

    public void setAudioOnly(boolean audioOnly) {
        this.audioOnly = audioOnly;
    }

    public boolean isWriteDescription() {
        return writeDescription;
    }

    public void setWriteDescription(boolean writeDescription) {
        this.writeDescription = writeDescription;
    }

    public boolean isWriteThumbnails() {
        return writeThumbnails;
    }

    public void setWriteThumbnails(boolean writeThumbnails) {
        this.writeThumbnails = writeThumbnails;
    }

    public boolean isWriteInfoJsons() {
        return writeInfoJsons;
    }

    public void setWriteInfoJsons(boolean writeInfoJsons) {
        this.writeInfoJsons = writeInfoJsons;
    }

    public boolean isNoOverWriting() {
        return noOverWriting;
    }

    public void setNoOverWriting(boolean noOverWriting) {
        this.noOverWriting = noOverWriting;
    }

    public boolean isAcceptPlayLists() {
        return acceptPlayLists;
    }

    public void setAcceptPlayLists(boolean acceptPlayLists) {
        this.acceptPlayLists = acceptPlayLists;
    }

    public String getSaveDirectory() {
        if (saveDirectory == null)
            setSaveDirectory(System.getProperty("user.home") + "\\Downloads");
        return saveDirectory;
    }

    public void setSaveDirectory(String saveDirectory) {
        this.saveDirectory = saveDirectory;
    }
}
