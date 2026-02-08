package com.mkvcompressor.service;

import java.io.File;

import com.mkvcompressor.model.VideoFile;

/**
 * Service for analyzing MKV file information
 */
public class VideoAnalyzer {

    /**
     * Analyzes a video file and extracts its information
     */
    public VideoFile analyzeVideo(String filePath) throws Exception {
        File file = new File(filePath);
        
        if (!file.exists()) {
            throw new Exception("File not found: " + filePath);
        }

        if (!filePath.toLowerCase().endsWith(".mkv")) {
            throw new Exception("File is not MKV: " + filePath);
        }

        VideoFile videoFile = new VideoFile(filePath);
        videoFile.setOriginalSize(file.length());

        System.out.println("Analyzing file: " + file.getName());
        System.out.println("Original size: " + VideoFile.formatFileSize(file.length()));

        // Here FFmpeg/FFprobe analysis would be performed
        // For this educational example, we'll simulate it
        analyzeWithFFprobe(videoFile);

        return videoFile;
    }

    /**
     * Uses FFprobe to extract video information
     * NOTE: Requires FFmpeg installed on the system
     */
    private void analyzeWithFFprobe(VideoFile videoFile) {
        try {
            // Command to get file information
            String command = "ffprobe -v quiet -print_format json -show_format -show_streams \"" 
                           + videoFile.getFilePath() + "\"";
            
            // TODO: Execute command and parse JSON
            // For now, example values for demonstration
            videoFile.setVideoCodec("H.264");
            videoFile.setResolution("1920x1080");
            videoFile.addAudioTrack("AAC - English");
            videoFile.addSubtitleTrack("SRT - English");
            
            System.out.println("Codec: " + videoFile.getVideoCodec());
            System.out.println("Resolution: " + videoFile.getResolution());
            System.out.println("Audio tracks: " + videoFile.getAudioTracks().size());
            System.out.println("Subtitles: " + videoFile.getSubtitleTracks().size());

        } catch (Exception e) {
            System.err.println("Warning: Could not analyze details with FFprobe");
            System.err.println("Make sure FFmpeg is installed");
        }
    }

    /**
     * Checks if FFmpeg is installed
     */
    public boolean isFFmpegInstalled() {
        try {
            ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-version");
            Process process = pb.start();
            process.waitFor();
            return process.exitValue() == 0;
        } catch (Exception e) {
            return false;
        }
    }
}
