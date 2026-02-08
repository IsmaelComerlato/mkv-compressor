package com.mkvcompressor.example;

import com.mkvcompressor.model.CompressionSettings;
import com.mkvcompressor.model.VideoFile;
import com.mkvcompressor.service.VideoAnalyzer;
import com.mkvcompressor.service.VideoCompressor;

/**
 * Examples of programmatic library usage
 * This class demonstrates how to use the project classes without the interactive menu
 */
public class UsageExamples {

    public static void main(String[] args) {
        System.out.println("=== MKV Compressor Usage Examples ===\n");

        // Example 1: Basic compression
        example1_BasicCompression();

        // Example 2: Compression with custom settings
        example2_CustomSettings();

        // Example 3: Video analysis without compression
        example3_AnalysisOnly();
    }

    /**
     * Example 1: Basic compression with default settings
     */
    private static void example1_BasicCompression() {
        System.out.println("--- Example 1: Basic Compression ---");

        try {
            // Create default settings
            CompressionSettings settings = new CompressionSettings();

            // Create compressor
            VideoCompressor compressor = new VideoCompressor(settings);

            // Analyze video
            VideoAnalyzer analyzer = new VideoAnalyzer();
            VideoFile video = analyzer.analyzeVideo("input/my_video.mkv");

            // Compress
            compressor.compressVideo(video, "output/my_video_compressed.mkv");

            System.out.println("✓ Compression completed!\n");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Example 2: Compression with custom settings
     */
    private static void example2_CustomSettings() {
        System.out.println("--- Example 2: Custom Settings ---");

        try {
            // Create custom settings
            CompressionSettings settings = new CompressionSettings();
            settings.setQuality("high");              // High quality
            settings.setCrf(18);                      // Lower CRF = better quality
            settings.setPreset("slow");               // Slow preset = better compression
            settings.setKeepAllAudioTracks(true);     // Keep all audio tracks
            settings.setKeepAllSubtitles(true);       // Keep all subtitles

            System.out.println("Settings: " + settings);

            // Create compressor with custom settings
            VideoCompressor compressor = new VideoCompressor(settings);

            // Analyze and compress
            VideoAnalyzer analyzer = new VideoAnalyzer();
            VideoFile video = analyzer.analyzeVideo("input/movie_hd.mkv");
            compressor.compressVideo(video, "output/movie_hd_compressed.mkv");

            System.out.println("✓ Compression completed with high quality!\n");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Example 3: Video analysis only (without compression)
     */
    private static void example3_AnalysisOnly() {
        System.out.println("--- Example 3: Video Analysis ---");

        try {
            VideoAnalyzer analyzer = new VideoAnalyzer();
            VideoFile video = analyzer.analyzeVideo("input/series_s01e01.mkv");

            // Display video information
            System.out.println("\nVideo information:");
            System.out.println("File: " + video.getFilePath());
            System.out.println("Size: " + VideoFile.formatFileSize(video.getOriginalSize()));
            System.out.println("Codec: " + video.getVideoCodec());
            System.out.println("Resolution: " + video.getResolution());
            System.out.println("Audio tracks: " + video.getAudioTracks().size());
            System.out.println("Subtitles: " + video.getSubtitleTracks().size());

            // List audio tracks
            System.out.println("\nAudio tracks:");
            for (String audio : video.getAudioTracks()) {
                System.out.println("  - " + audio);
            }

            // List subtitles
            System.out.println("\nSubtitles:");
            for (String subtitle : video.getSubtitleTracks()) {
                System.out.println("  - " + subtitle);
            }

            System.out.println("\n✓ Analysis completed!\n");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Example 4: Batch compression (commented for reference)
     */
    private static void example4_BatchCompression() {
        System.out.println("--- Example 4: Batch Compression ---");

        try {
            CompressionSettings settings = new CompressionSettings();
            settings.setQuality("medium");

            VideoCompressor compressor = new VideoCompressor(settings);
            compressor.compressBatch("input/series", "output/series_compressed");

            System.out.println("✓ Batch completed!\n");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
