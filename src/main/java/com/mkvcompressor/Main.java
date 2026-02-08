package com.mkvcompressor;

import java.util.Scanner;

import com.mkvcompressor.model.CompressionSettings;
import com.mkvcompressor.model.VideoFile;
import com.mkvcompressor.service.VideoAnalyzer;
import com.mkvcompressor.service.VideoCompressor;

/**
 * Main class for MKV video compression application
 * 
 * @author Your Name
 * @version 1.0.0
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   MKV Video Compressor v1.0.0         ║");
        System.out.println("║   Compress videos without losing quality ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        // Check if FFmpeg is installed
        VideoAnalyzer analyzer = new VideoAnalyzer();
        if (!analyzer.isFFmpegInstalled()) {
            System.err.println("ERROR: FFmpeg is not installed!");
            System.err.println("Please install FFmpeg to use this application.");
            System.err.println("Visit: https://ffmpeg.org/download.html");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        try {
            // Main menu
            System.out.println("Select an option:");
            System.out.println("1 - Compress a single file");
            System.out.println("2 - Compress multiple files (batch)");
            System.out.println("3 - Exit");
            System.out.print("\nOption: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    compressSingleFile(scanner);
                    break;
                case 2:
                    compressBatch(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option!");
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    /**
     * Compresses a single MKV file
     */
    private static void compressSingleFile(Scanner scanner) throws Exception {
        System.out.print("\nMKV file path: ");
        String inputPath = scanner.nextLine().trim();

        System.out.print("Output path (leave empty for 'output' folder): ");
        String outputPath = scanner.nextLine().trim();

        if (outputPath.isEmpty()) {
            String filename = inputPath.substring(inputPath.lastIndexOf('/') + 1);
            outputPath = "output/" + filename.replace(".mkv", "_compressed.mkv");
        }

        // Compression settings
        CompressionSettings settings = configureCompression(scanner);

        // Analyze video
        VideoAnalyzer analyzer = new VideoAnalyzer();
        VideoFile videoFile = analyzer.analyzeVideo(inputPath);

        // Compress
        VideoCompressor compressor = new VideoCompressor(settings);
        compressor.compressVideo(videoFile, outputPath);

        System.out.println("\n✓ Compression completed successfully!");
        System.out.println("File saved at: " + outputPath);
    }

    /**
     * Compresses multiple files in batch mode
     */
    private static void compressBatch(Scanner scanner) throws Exception {
        System.out.print("\nDirectory with MKV files: ");
        String inputDir = scanner.nextLine().trim();

        System.out.print("Output directory (leave empty for 'output'): ");
        String outputDir = scanner.nextLine().trim();

        if (outputDir.isEmpty()) {
            outputDir = "output";
        }

        // Compression settings
        CompressionSettings settings = configureCompression(scanner);

        // Compress batch
        VideoCompressor compressor = new VideoCompressor(settings);
        compressor.compressBatch(inputDir, outputDir);

        System.out.println("\n✓ Batch compression completed!");
    }

    /**
     * Allows user to configure compression options
     */
    private static CompressionSettings configureCompression(Scanner scanner) {
        CompressionSettings settings = new CompressionSettings();

        System.out.println("\n=== Compression Settings ===");
        System.out.println("1 - High quality (CRF 20, larger file)");
        System.out.println("2 - Medium quality (CRF 23, recommended)");
        System.out.println("3 - Low quality (CRF 28, smaller file)");
        System.out.print("Choose quality [2]: ");

        String qualityInput = scanner.nextLine().trim();
        int quality = qualityInput.isEmpty() ? 2 : Integer.parseInt(qualityInput);

        switch (quality) {
            case 1:
                settings.setQuality("high");
                break;
            case 3:
                settings.setQuality("low");
                break;
            default:
                settings.setQuality("medium");
        }

        System.out.print("Keep all audio tracks? (Y/n): ");
        String keepAudio = scanner.nextLine().trim().toLowerCase();
        settings.setKeepAllAudioTracks(!keepAudio.equals("n"));

        System.out.print("Keep all subtitles? (Y/n): ");
        String keepSubtitles = scanner.nextLine().trim().toLowerCase();
        settings.setKeepAllSubtitles(!keepSubtitles.equals("n"));

        return settings;
    }
}
