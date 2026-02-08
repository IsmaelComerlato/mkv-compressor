package com.mkvcompressor.model;

/**
 * Compression settings for video
 */
public class CompressionSettings {
    private String outputCodec;
    private String quality;
    private boolean keepAllAudioTracks;
    private boolean keepAllSubtitles;
    private String preset;
    private int crf; // Constant Rate Factor (0-51, lower = better quality)

    public CompressionSettings() {
        // Default values for high quality compression
        this.outputCodec = "libx265"; // H.265 for better compression
        this.quality = "high";
        this.keepAllAudioTracks = true;
        this.keepAllSubtitles = true;
        this.preset = "medium";
        this.crf = 23; // Balance between quality and size
    }

    // Getters and Setters
    public String getOutputCodec() {
        return outputCodec;
    }

    public void setOutputCodec(String outputCodec) {
        this.outputCodec = outputCodec;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
        // Adjust CRF based on quality
        switch (quality.toLowerCase()) {
            case "high":
                this.crf = 20;
                break;
            case "medium":
                this.crf = 23;
                break;
            case "low":
                this.crf = 28;
                break;
        }
    }

    public boolean isKeepAllAudioTracks() {
        return keepAllAudioTracks;
    }

    public void setKeepAllAudioTracks(boolean keepAllAudioTracks) {
        this.keepAllAudioTracks = keepAllAudioTracks;
    }

    public boolean isKeepAllSubtitles() {
        return keepAllSubtitles;
    }

    public void setKeepAllSubtitles(boolean keepAllSubtitles) {
        this.keepAllSubtitles = keepAllSubtitles;
    }

    public String getPreset() {
        return preset;
    }

    public void setPreset(String preset) {
        this.preset = preset;
    }

    public int getCrf() {
        return crf;
    }

    public void setCrf(int crf) {
        if (crf < 0 || crf > 51) {
            throw new IllegalArgumentException("CRF must be between 0 and 51");
        }
        this.crf = crf;
    }

    @Override
    public String toString() {
        return "CompressionSettings{" +
                "outputCodec='" + outputCodec + '\'' +
                ", quality='" + quality + '\'' +
                ", keepAllAudioTracks=" + keepAllAudioTracks +
                ", keepAllSubtitles=" + keepAllSubtitles +
                ", preset='" + preset + '\'' +
                ", crf=" + crf +
                '}';
    }
}
