package com.mkvcompressor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an MKV video file with its properties
 */
public class VideoFile {
    private String filePath;
    private long originalSize;
    private long compressedSize;
    private List<String> audioTracks;
    private List<String> subtitleTracks;
    private String videoCodec;
    private String resolution;

    public VideoFile(String filePath) {
        this.filePath = filePath;
        this.audioTracks = new ArrayList<>();
        this.subtitleTracks = new ArrayList<>();
    }

    // Getters and Setters
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getOriginalSize() {
        return originalSize;
    }

    public void setOriginalSize(long originalSize) {
        this.originalSize = originalSize;
    }

    public long getCompressedSize() {
        return compressedSize;
    }

    public void setCompressedSize(long compressedSize) {
        this.compressedSize = compressedSize;
    }

    public List<String> getAudioTracks() {
        return audioTracks;
    }

    public void addAudioTrack(String audioTrack) {
        this.audioTracks.add(audioTrack);
    }

    public List<String> getSubtitleTracks() {
        return subtitleTracks;
    }

    public void addSubtitleTrack(String subtitleTrack) {
        this.subtitleTracks.add(subtitleTrack);
    }

    public String getVideoCodec() {
        return videoCodec;
    }

    public void setVideoCodec(String videoCodec) {
        this.videoCodec = videoCodec;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    /**
     * Calculates the size reduction percentage
     */
    public double getCompressionPercentage() {
        if (originalSize == 0) return 0;
        return ((originalSize - compressedSize) / (double) originalSize) * 100;
    }

    /**
     * Returns the size in human-readable format (MB, GB)
     */
    public static String formatFileSize(long size) {
        if (size < 1024) return size + " B";
        if (size < 1024 * 1024) return String.format("%.2f KB", size / 1024.0);
        if (size < 1024 * 1024 * 1024) return String.format("%.2f MB", size / (1024.0 * 1024));
        return String.format("%.2f GB", size / (1024.0 * 1024 * 1024));
    }

    @Override
    public String toString() {
        return "VideoFile{" +
                "filePath='" + filePath + '\'' +
                ", originalSize=" + formatFileSize(originalSize) +
                ", audioTracks=" + audioTracks.size() +
                ", subtitleTracks=" + subtitleTracks.size() +
                ", videoCodec='" + videoCodec + '\'' +
                ", resolution='" + resolution + '\'' +
                '}';
    }
}
