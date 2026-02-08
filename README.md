# 🎬 MKV Video Compressor

[![Java](https://img.shields.io/badge/Java-11+-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Active-brightgreen.svg)]()

> **My problem:** I collect movies and TV series in 4K locally (because I love that cinematic experience), but my hard drives keep filling up. My solution? Build a tool that compresses them without losing quality.

---

## Why?

I'm someone who loves having a curated collection of high-quality movies stored locally—no streaming lag, no subscription nonsense. But here's the catch: a single 4K movie takes 15–40GB. My external drives were crying.

So instead of paying for more storage, I decided to build this compressor in Java to:
1. **Solve my real problem** (compress videos intelligently)
2. **Master Java fundamentals** (OOP, system integration, file handling)
3. **Create something useful** (and useful things get attention)

## 🎯 What This Project Shows

- **OOP Done Right**: Separation of concerns (Model, Service, Main controller)
- **System Integration**: Executing external processes (FFmpeg) cleanly with ProcessBuilder
- **Real-world Problem Solving**: Progress tracking, error handling, batch processing
- **Clean Code**: Readable, maintainable Java with meaningful variable names and structure

## 📋 Table of Contents

- [The Problem & Solution](#the-problem--solution)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Architecture](#architecture)
- [What I Learned](#what-i-learned)
- [Contributing](#contributing)
- [License](#license)

## 🔧 The Problem & Solution

**Problem:** 
```
My Movie Collection:
├── 4K Blu-ray rips (20GB each) ❌ Too big
├── External drives (getting full) 😢
└── Streaming alternatives (no thanks)
```

**Solution:**
```
MKV Compressor
├── H.265 compression (50%+ size reduction) ✅
├── Quality still amazing (CRF 23) ✅
├── All audio tracks preserved ✅
├── Subtitles intact ✅
└── Real-time progress (0–100%) ✅
```

## ✨ Features

- ✅ **H.265 Compression** - Modern codec, incredible compression
- ✅ **Keeps ALL audio tracks** - No quality loss on sound
- ✅ **Preserves subtitles** - SRT, ASS, everything stays
- ✅ **Batch processing** - Compress entire folders at once
- ✅ **Real-time progress (0–100%)** - Exactly how long it'll take
- ✅ **Quality presets** - High/Medium/Low, you choose the balance
- ✅ **Size reduction report** - See how much you saved
- ✅ **Interactive CLI** - User-friendly, not scary

## 🛠️ Tech Stack

- **Java 11+** - Clean, modern Java with proper OOP
- **Maven** - Dependency management and build automation
- **FFmpeg/FFprobe** - Industry-standard video processing
- **ProcessBuilder** - Safe system process execution
- **Regex** - Real-time progress parsing

- **Java 11+**
- **Maven**
- **FFmpeg / FFprobe** (external dependency)

## 📦 Requirements

1. **Java 11+**
    ```bash
    java -version
    ```

2. **Maven 3.6+**
    ```bash
    mvn -version
    ```

3. **FFmpeg**

    **Linux (Ubuntu/Debian):**
    ```bash
    sudo apt update
    sudo apt install ffmpeg
    ```

    **macOS:**
    ```bash
    brew install ffmpeg
    ```

    **Windows:**
    - Download: https://ffmpeg.org/download.html
    - Add to PATH

    Verify:
    ```bash
    ffmpeg -version
    ```

## 🚀 Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/your-user/mkv-compressor.git
    cd mkv-compressor
    ```

2. Compile:
    ```bash
    mvn clean compile
    ```

3. (Optional) Build a runnable JAR:
    ```bash
    mvn package
    ```

## � Installation & Usage

### Quick Start

1. **Make sure you have the tools:**
   ```bash
   java -version      # Need Java 11+
   mvn -version       # Need Maven 3.6+
   ffmpeg -version    # Need FFmpeg installed
   ```

2. **Clone this repo:**
   ```bash
   git clone https://github.com/IsmaelComerlato/mkv-compressor.git
   cd mkv-compressor
   ```

3. **Run it:**
   ```bash
   mvn exec:java -Dexec.mainClass="com.mkvcompressor.Main"
   ```

4. **Follow the prompts** and select a video to compress

### Real Example: My First Compression

```bash
$ mvn exec:java -Dexec.mainClass="com.mkvcompressor.Main"

╔════════════════════════════════════════╗
║   MKV Video Compressor v1.0.0         ║
║   Making my movie collection fit again ║
╚════════════════════════════════════════╝

Enter MKV file path: /media/movies/Dune.mkv
Enter output path: /media/compressed/Dune_compressed.mkv

📊 Video Analysis:
   Codec: H.264
   Duration: 2h 46m
   Size: 45.2 GB
   Audio: English (5.1), Portuguese (5.1), Spanish (2.0)
   Subtitles: 8 languages

Quality preset (1=high, 2=medium, 3=low)? [2]: 2

🔄 Starting compression...
█████████████░░░░░░░░░░░░░░ 45% | ETA: 1h 23m

✅ Compression complete!
   Original: 45.2 GB
   Compressed: 18.7 GB
   Saved: 26.5 GB (58.6% reduction)
   Time: 3h 47m
```

## 📁 Architecture

```
MkvProject/
├── pom.xml                     # Dependencies
├── src/main/java/com/mkvcompressor/
│   ├── Main.java              # CLI entry point
│   ├── model/                 # Data models (VideoFile, CompressionSettings)
│   └── service/               # Business logic (VideoAnalyzer, VideoCompressor)
```

**Design pattern used:** Clean separation of concerns
- **Model** = Data representation
- **Service** = Business logic
- **Main** = User interface

## 📚 What I Learned Building This

### 1. OOP in Practice
Instead of learning OOP from textbooks, I lived it:
- Why separate `VideoFile` (data) from `VideoCompressor` (logic)?
- Why does `CompressionSettings` make sense as a dedicated class?
- How composition (`VideoCompressor` uses `CompressionSettings`) prevents code duplication

### 2. System Integration (The Hard Part)
FFmpeg doesn't have a Java library. So I learned:
- **ProcessBuilder** - How to safely execute external commands
- **Stream handling** - Reading real-time output from FFmpeg
- **Regex parsing** - Extracting progress from raw text: `time=00:45:23.50`
- **Error handling** - What happens when FFmpeg crashes or video is corrupted?

### 3. Real-Time Progress Tracking
This was the most challenging feature:
```java
// Getting video duration first
Double durationSeconds = getVideoDurationSeconds(inputFile);

// Then parsing FFmpeg output in real-time
Pattern pattern = Pattern.compile("time=(\\d{2}):(\\d{2}):(\\d{2}\\.\\d+)");
// Converting to percentage: (currentSeconds / durationSeconds) * 100
```

### 4. Maven & Build Tools
Why Maven matters:
- Dependency management (we don't download FFmpeg in Java—we call the system binary)
- Build lifecycle (`mvn clean compile` vs `mvn package`)
- Plugins (exec plugin to run the app directly)

### 5. Practical Problem Solving
- How do I verify FFmpeg is installed before running?
- What if the user's file doesn't exist?
- How do I handle files with special characters in their names?
- Should I ask the user 100 questions or sensible defaults?

## � Use Cases

**Why I built this for myself:**

| Scenario | Before | After |
|----------|--------|-------|
| 4K movie (Dune) | 45 GB | 18 GB |
| 10-episode series | 250 GB | 110 GB |
| Anime collection | 80 GB | 30 GB |
| Storage freed | Need external drive | All local, still fast |

**Video quality:** Literally imperceptible to the human eye with CRF 23. You're watching H.265 compressed, which is what Netflix/Prime use anyway.

## 🚀 Why This Project Matters (For Me)

This isn't a "Hello World" or a tutorial clone. This is:
- **Real problem:** I actually needed this (and still use it)
- **Real learning:** I had to debug regex patterns, FFmpeg integration, CLI UX
- **Real code:** Clean OOP, not overengineered, not undersimplified
- **Real portfolio piece:** Shows I can think, code, and solve problems

For recrutadores: This demonstrates that I don't just follow tutorials. I identify problems and build solutions.

## 🤝 Contributing

Contributions are welcome:

1. Fork the project
2. Create a branch (`git checkout -b feature/MyFeature`)
3. Commit changes (`git commit -m 'Add MyFeature'`)
4. Push (`git push origin feature/MyFeature`)
5. Open a Pull Request

## 📝 Roadmap

- [ ] GUI (JavaFX or Swing)
- [ ] Support for other formats (MP4, AVI)
- [ ] Parallel compression
- [ ] Progress ETA (time remaining)
- [ ] Config file support
- [ ] Detailed logging
- [ ] Unit tests (JUnit)
- [ ] Preset profiles

## 📄 License

MIT License. See [LICENSE](LICENSE).

## 👨‍💻 Author

**Ismael Comerlato**

- GitHub: [@IsmaelComerlato](https://github.com/IsmaelComerlato)
- Email: ismacomerlato@gmail.com

## 🙏 Acknowledgements

- [FFmpeg](https://ffmpeg.org/) - video processing toolkit
- Java community - docs and support

---

⭐ If this project helped you, consider giving it a star!
