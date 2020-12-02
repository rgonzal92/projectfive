package application.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Contains variables and methods used when reading and writing the user's config file.
 * Variables:	instance(Config), videoFormat(String), audioFormat(String), videoResolution(String), audioOnly(boolean), 
 * 				writeDescription(boolean), writeThumbnails(boolean), writeInfoJsons(boolean), noOberwriting(boolean),
 * 				acceptPlayLists(boolean), writeSubtitles(boolean), outputTemplate(String), saveDirectory(String)
 * 
 * @author Richard Gonzalez
 * @author Shejan Shuza
 * @author Juan-Carlos Rodriguez
 * @author Collin Behunin
 * @author Jacob De Hoyos
 */
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

    /**
     * Writes various flags and format information into a config file used in the download execution.
     * 
     * @throws IOException	if the file cannot be found.
     */
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

    /**
     * Reads the config file for flad and format information.
     * 
     * @throws IOException	if the file cannot be found.
     */
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

    /**
     * Returns the location of the config file.
     * 
     * @return		a String representing the location of the config file.
     */
    private String getConfigPath() {
        return System.getProperty("user.dir") + "/youtube-dl.conf";
    }

    /**
     * Returns the Config object, and creates one if one does not exits.
     * 
     * @return		a Config instance.
     */
    public static Config getInstance() {
        if (instance == null)
            instance = new Config();
        return instance;
    }

    /**
     * Returns the String holding the video format information.
     * 
     * @return		a String representing the video format.
     */
    public String getVideoFormat() {
        return videoFormat;
    }

    /**
     * Sets a String into the videoFormat variable.
     *  
     * @param videoFormat	The String for the videoFormat variable.
     */
    public void setVideoFormat(String videoFormat) {
        this.videoFormat = videoFormat;
    }

    /**
     * Returns the String holding the audio format information.
     * 
     * @return		a String representing the audio format.
     */
    public String getAudioFormat() {
        return audioFormat;
    }

    /**
     * Sets a String into the audioFormat variable.
     *  
     * @param audioFormat	The String for the audioFormat variable.
     */
    public void setAudioFormat(String audioFormat) {
        this.audioFormat = audioFormat;
    }

    /**
     * Returns the String holding the video resolution information.
     * 
     * @return		a String representing the video resolution.
     */
    public String getVideoResolution() {
        return videoResolution;
    }

    /**
     * Sets a String into the videoResolution variable.
     *  
     * @param videoResolution	The String for the videoResolution variable.
     */
    public void setVideoResolution(String videoResolution) {
        this.videoResolution = videoResolution;
    }

    /**
     * Returns the boolean representing if the download will be audio only.
     * 
     * @return		a boolean representing if audio only is selected.
     */
    public boolean isAudioOnly() {
        return audioOnly;
    }

    /**
     * Sets a boolean into the audioOnly variable.
     *  
     * @param audioOnly	The boolean for the audioOnly variable.
     */
    public void setAudioOnly(boolean audioOnly) {
        this.audioOnly = audioOnly;
    }

    /**
     * Returns the boolean representing if the download will write a decription.
     * 
     * @return		a boolean representing if a description will be written.
     */
    public boolean isWriteDescription() {
        return writeDescription;
    }

    /**
     * Sets a boolean into the writeDescription variable.
     *  
     * @param writeDescription	The boolean for the writeDescription variable.
     */
    public void setWriteDescription(boolean writeDescription) {
        this.writeDescription = writeDescription;
    }

    /**
     * Returns the boolean representing if the download will write thumbnails.
     * 
     * @return		a boolean representing if thumbnails will be written.
     */
    public boolean isWriteThumbnails() {
        return writeThumbnails;
    }
    
    /**
     * Sets a boolean into the writeThumbnails variable.
     *  
     * @param writeThumbnails	The boolean for the writeThumbnails variable.
     */
    public void setWriteThumbnails(boolean writeThumbnails) {
        this.writeThumbnails = writeThumbnails;
    }

    /**
     * Returns the boolean representing if the download will write infoJsons.
     * 
     * @return		a boolean representing if infoJsons will be written.
     */
    public boolean isWriteInfoJsons() {
        return writeInfoJsons;
    }

    /**
     * Sets a boolean into the writeInfoJsons variable.
     *  
     * @param writeInofJsons	The boolean for the writeInfoJsons variable.
     */
    public void setWriteInfoJsons(boolean writeInfoJsons) {
        this.writeInfoJsons = writeInfoJsons;
    }

    /**
     * Returns the boolean representing if overwriting will be allowed.
     * 
     * @return		a boolean representing the if overwriting is allowed.
     */
    public boolean isNoOverWriting() {
        return noOverWriting;
    }

    /**
     * Sets a boolean into the noOverWriting variable.
     * 
     * @param noOverWriting		The boolean for the noOverWriting variable.
     */
    public void setNoOverWriting(boolean noOverWriting) {
        this.noOverWriting = noOverWriting;
    }

    /**
     * Returns the boolean representing if playlists are accepted.
     * 
     * @return		a boolean representing if playlists are accepted.
     */
    public boolean isAcceptPlayLists() {
        return acceptPlayLists;
    }

    /**
     * Sets a boolean into the acceptPlayLists variable.
     * 
     * @param acceptPlayLists	The boolean for the acceptPlayLists variable.
     */
    public void setAcceptPlayLists(boolean acceptPlayLists) {
        this.acceptPlayLists = acceptPlayLists;
    }

    /**
     * Returns the string holding the directory where videos will be saved.
     * 
     * @return		a String representing the where videos will be saved.
     */
    public String getSaveDirectory() {
        if (saveDirectory == null)
            setSaveDirectory(System.getProperty("user.dir"));
        return saveDirectory;
    }

    /**
     * Sets a String into the saveDirectory variable.
     * 
     * @param saveDirectory		The String for the saveDirectory variable.
     */
    public void setSaveDirectory(String saveDirectory) {
        this.saveDirectory = saveDirectory;
    }

    /**
     * Returns the a boolean representing if subtitles will be written.
     * 
     * @return		a boolean representing if subtitles will be written.
     */
    public boolean isWriteSubtitles() {
        return writeSubtitles;
    }

    /**
     * Sets a boolean into the writeSubtitles variable
     * 
     * @param writeSubtitles	The boolean for the writeSubtitles variable.
     */
    public void setWriteSubtitles(boolean writeSubtitles) {
        this.writeSubtitles = writeSubtitles;
    }

    /**
     * Returns the string holding the output template.
     * 
     * @return		a String representing the outputTemplate.
     */
    public String getOutputTemplate() {
        return outputTemplate;
    }

    /**
     * Sets a boolean into the outputTemplate variable.
     * 
     * @param outputTemplate	The String for the outputTemplate variable.
     */
    public void setOutputTemplate(String outputTemplate) {
        this.outputTemplate = outputTemplate;
    }
}
