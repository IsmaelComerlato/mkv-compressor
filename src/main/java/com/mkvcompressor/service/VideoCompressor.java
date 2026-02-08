package com.mkvcompressor.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mkvcompressor.model.CompressionSettings;
import com.mkvcompressor.model.VideoFile;

/**
 * Service responsible for compressing MKV videos
 */
public class VideoCompressor {

    private CompressionSettings settings;

    public VideoCompressor(CompressionSettings settings) {
        this.settings = settings;
    }

    /**
     * Compresses a video file
     */
    public void compressVideo(VideoFile videoFile, String outputPath) throws Exception {
        System.out.println("\n=== Starting Compression ===");
        System.out.println("Input file: " + videoFile.getFilePath());
        System.out.println("Output file: " + outputPath);
        System.out.println("Settings: " + settings);

        // Create output directory if it doesn't exist
        File outputFile = new File(outputPath);
        File outputDir = outputFile.getParentFile();
        if (outputDir != null && !outputDir.exists()) {
            outputDir.mkdirs();
        }

        // Build FFmpeg command
        String command = buildFFmpegCommand(videoFile, outputPath);
        
        System.out.println("\nExecuting compression...");
        System.out.println("Command: " + command);
        
        // Execute compression with progress
        Double durationSeconds = getVideoDurationSeconds(videoFile.getFilePath());
        executeCompression(command, durationSeconds);

        // Update compressed file information
        if (outputFile.exists()) {
            videoFile.setCompressedSize(outputFile.length());
            
            System.out.println("\n=== Compression Completed ===");
            System.out.println("Original size: " + VideoFile.formatFileSize(videoFile.getOriginalSize()));
            System.out.println("Compressed size: " + VideoFile.formatFileSize(videoFile.getCompressedSize()));
            System.out.printf("Reduction: %.2f%%\n", videoFile.getCompressionPercentage());
        } else {
            throw new Exception("Compression failed - output file was not created");
        }
    }

    /**
     * Builds FFmpeg command based on settings
     */
    private String buildFFmpegCommand(VideoFile videoFile, String outputPath) {
        StringBuilder cmd = new StringBuilder("ffmpeg -i \"");
        cmd.append(videoFile.getFilePath()).append("\"");

        // Video settings
        cmd.append(" -c:v ").append(settings.getOutputCodec());
        cmd.append(" -crf ").append(settings.getCrf());
        cmd.append(" -preset ").append(settings.getPreset());

        // Keep all audio tracks
        if (settings.isKeepAllAudioTracks()) {
            cmd.append(" -c:a copy"); // Copy audio without re-encoding
        } else {
            cmd.append(" -c:a aac -b:a 128k"); // Re-encode to AAC
        }

        // Keep all subtitles
        if (settings.isKeepAllSubtitles()) {
            cmd.append(" -c:s copy"); // Copy subtitles
        }

        // Keep metadata
        cmd.append(" -map 0"); // Map all streams
        cmd.append(" -map_metadata 0"); // Preserve metadata

        cmd.append(" \"").append(outputPath).append("\"");
        cmd.append(" -y"); // Overwrite output file if it exists

        return cmd.toString();
    }

    /**
     * Executes the compression command
     */
    private void executeCompression(String command, Double durationSeconds) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();
        
        // Split command into parts for ProcessBuilder
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            processBuilder.command("cmd.exe", "/c", command);
        } else {
            processBuilder.command("bash", "-c", command);
        }

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        // Read and display progress
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            int lastPercent = -1;
            while ((line = reader.readLine()) != null) {
                if (durationSeconds != null) {
                    Double currentSeconds = extractTimeSeconds(line);
                    if (currentSeconds != null) {
                        int percent = (int) Math.min(100, Math.max(0,
                                Math.round((currentSeconds / durationSeconds) * 100)));
                        if (percent != lastPercent) {
                            System.out.print("\rProgress: " + percent + "%");
                            lastPercent = percent;
                        }
                    }
                } else {
                    // Fallback: show FFmpeg status lines when duration is unknown
                    if (line.contains("frame=") || line.contains("time=") || 
                        line.contains("speed=") || line.contains("error")) {
                        System.out.println(line);
                    }
                }
            }
        }

        if (durationSeconds != null) {
            System.out.println();
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new Exception("Compression error. Exit code: " + exitCode);
        }
    }

    /**
     * Reads video duration in seconds using FFprobe
     */
    private Double getVideoDurationSeconds(String filePath) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "ffprobe",
                    "-v", "error",
                    "-show_entries", "format=duration",
                    "-of", "default=noprint_wrappers=1:nokey=1",
                    filePath
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line = reader.readLine();
                process.waitFor();
                if (line != null && !line.trim().isEmpty()) {
                    return Double.parseDouble(line.trim());
                }
            }
        } catch (Exception ignored) {
            // If duration cannot be detected, return null to use fallback output
        }
        return null;
    }

    /**
     * Extracts the current time (in seconds) from an FFmpeg output line
     */
    private Double extractTimeSeconds(String line) {
        Matcher matcher = Pattern.compile("time=(\\d{2}):(\\d{2}):(\\d{2}\\.\\d+)").matcher(line);
        if (!matcher.find()) {
            return null;
        }
        int hours = Integer.parseInt(matcher.group(1));
        int minutes = Integer.parseInt(matcher.group(2));
        double seconds = Double.parseDouble(matcher.group(3));
        return (hours * 3600) + (minutes * 60) + seconds;
    }

    /**
     * Compresses multiple files in batch mode
     */
    public void compressBatch(String inputDirectory, String outputDirectory) throws Exception {
        File dir = new File(inputDirectory);
        File[] mkvFiles = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".mkv"));

        if (mkvFiles == null || mkvFiles.length == 0) {
            System.out.println("No MKV files found in: " + inputDirectory);
            return;
        }

        System.out.println("Found " + mkvFiles.length + " MKV files");
        
        VideoAnalyzer analyzer = new VideoAnalyzer();
        int count = 1;

        for (File file : mkvFiles) {
            System.out.println("\n[" + count + "/" + mkvFiles.length + "] Processing: " + file.getName());
            
            VideoFile videoFile = analyzer.analyzeVideo(file.getAbsolutePath());
            String outputPath = outputDirectory + File.separator + file.getName();
            
            compressVideo(videoFile, outputPath);
            count++;
        }

        System.out.println("\n=== Batch compression completed ===");
    }

    // Getters and Setters
    public CompressionSettings getSettings() {
        return settings;
    }

    public void setSettings(CompressionSettings settings) {
        this.settings = settings;
    }
}
