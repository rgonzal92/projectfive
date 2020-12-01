package application.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

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
    private boolean writeSubtitles;
    private String outputTemplate;
    private String saveDirectory;

    public void writeConfig() throws IOException {
        FileWriter fileWriter = new FileWriter(getConfigPath());
        PrintWriter printWriter = new PrintWriter(fileWriter);

        // format template
        if (getAudioFormat().contains("Auto"))
            printWriter.printf("--format \"bestvideo[height<=%s][ext=%s]\"\n", getVideoResolution(), getVideoFormat());
        else
            printWriter.printf("--format \"bestvideo[height<=%s][ext=%s]+bestaudio[ext=%s]\"\n", getVideoResolution(), getVideoFormat(), getAudioFormat());

        // flags
        if (isAudioOnly()) printWriter.println("-x");
        if (isWriteDescription()) printWriter.println("--write-description");
        if (isWriteThumbnails()) printWriter.println("--write-thumbnail");
        if (isNoOverWriting()) printWriter.println("--no-overwrites");
        if (isAcceptPlayLists()) printWriter.println("--yes-playlist");
        else printWriter.println("--no-playlist");
        if (isWriteInfoJsons()) printWriter.println("--write-info-json");
        if (isWriteSubtitles()) printWriter.println("--write-sub");

        // output template, set default as 'Title.ext' if null, else get config output
        if (getOutputTemplate() == null) printWriter.println("-o \"%(title)s.%(ext)s\"");
        else printWriter.printf("-o \"%s\"\n", getOutputTemplate());

        // add # to ignore line read from config (else causes error)
        // set config saved path
        printWriter.printf("# %s\n", getSaveDirectory());

        // close
        printWriter.close();
        fileWriter.close();
    }

    public void loadConfig() throws IOException {
        // list dropdown options
        String[] videoFormats = {"3gp", "flv", "webm", "mp4"};
        String[] audioFormats = {"mp3", "wav", "acc", "m4a", "flac"};
        String[] videoResolutions = {"480", "720", "1080", "1440", "2160"};

        // if config does not exist, create
        File config = new File(getConfigPath());
        config.createNewFile();

        // search config lines for matching string
        Scanner scan = new Scanner(config);
        String line;
        if (scan.hasNextLine()) {
            line = scan.nextLine();
            for (String format : videoFormats)
                if (line.contains(format))
                    setVideoFormat(format);
            for (String format : audioFormats)
                if (line.contains(format))
                    setAudioFormat(format);
                else
                    setAudioFormat("Auto");
            for (String res : videoResolutions)
                if (line.contains(res))
                    setVideoResolution(res);
            while (scan.hasNextLine()) {
                line = scan.nextLine();
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
                if (line.contains("write-sub"))
                    setWriteSubtitles(true);
                if (line.contains("-o "))
                    setOutputTemplate(line.substring(3)); // cut off '-o' and whitespace
                if (line.contains("#"))
                    setSaveDirectory(line.substring(2)); // cut off # and whitespace
            }
        }
    }

    private String getConfigPath() {
        return System.getProperty("user.dir") + "/youtube-dl.conf";
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
            setSaveDirectory(System.getProperty("user.dir"));
        return saveDirectory;
    }

    public void setSaveDirectory(String saveDirectory) {
        this.saveDirectory = saveDirectory;
    }

    public boolean isWriteSubtitles() {
        return writeSubtitles;
    }

    public void setWriteSubtitles(boolean writeSubtitles) {
        this.writeSubtitles = writeSubtitles;
    }

    public String getOutputTemplate() {
        return outputTemplate;
    }

    public void setOutputTemplate(String outputTemplate) {
        this.outputTemplate = outputTemplate;
    }
}
