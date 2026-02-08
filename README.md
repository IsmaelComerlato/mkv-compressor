# 🎬 MKV Video Compressor

[![Java](https://img.shields.io/badge/Java-11+-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

An object-oriented Java application to compress MKV files without losing quality, while preserving audio tracks, subtitles, and metadata.

## 📌 Portfolio Highlights

- **Clean OOP architecture** with clear separation of responsibilities
- **System process integration** using FFmpeg/FFprobe
- **Progress indicator (0–100%)** based on real video duration
- **Batch processing** for multiple files
- **Quality presets** using CRF for balanced size and quality
- **CLI UX** with guided prompts and validation

## 📋 Table of Contents

- [About](#about)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [OOP Concepts](#oop-concepts)
- [Examples](#examples)
- [Contributing](#contributing)
- [License](#license)

## 🎯 About

This project was built as a portfolio piece to demonstrate:
- Object-Oriented Programming in Java
- Process execution and parsing
- Maven-based project structure
- Clean code practices

It compresses MKV videos using H.265 (HEVC), often achieving up to 50% size reduction while preserving visual quality.

## ✨ Features

- ✅ High-quality compression with H.265
- ✅ Keeps all audio tracks
- ✅ Preserves subtitles (SRT, ASS, etc.)
- ✅ Preserves metadata
- ✅ Single-file compression
- ✅ Batch compression
- ✅ Quality presets (CRF)
- ✅ Size reduction summary
- ✅ Progress indicator (0–100%)
- ✅ Interactive CLI

## 🛠️ Tech Stack

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

## 💻 Usage

### Run with Maven

```bash
mvn exec:java -Dexec.mainClass="com.mkvcompressor.Main"
```

### Run the JAR

```bash
java -jar target/mkv-compressor-1.0.0.jar
```

### Interactive Menu

```
╔════════════════════════════════════════╗
║   MKV Video Compressor v1.0.0         ║
║   Compress videos without losing quality ║
╚════════════════════════════════════════╝

Select an option:
1 - Compress a single file
2 - Compress multiple files (batch)
3 - Exit

Option: _
```

### Quality Presets

- **High (CRF 20)**: best quality, larger file (~15–20% reduction)
- **Medium (CRF 23)**: recommended balance (~30–40% reduction)
- **Low (CRF 28)**: smaller file (~50%+ reduction)

*CRF = Constant Rate Factor (0–51, lower = better quality)*

## 📁 Project Structure

```
MkvProject/
├── pom.xml                          # Maven config
├── README.md                        # This file
├── .gitignore
└── src/
     └── main/
          └── java/
                └── com/
                     └── mkvcompressor/
                          ├── Main.java                    # Entry point
                          ├── model/                       # Domain models
                          │   ├── VideoFile.java
                          │   └── CompressionSettings.java
                          └── service/                     # Business logic
                                ├── VideoAnalyzer.java
                                └── VideoCompressor.java
```

## 🎓 OOP Concepts

### 1. Encapsulation
```java
private String filePath;
private long originalSize;

public String getFilePath() {
     return filePath;
}
```

### 2. Single Responsibility
- `VideoFile` holds data
- `VideoAnalyzer` analyzes files
- `VideoCompressor` compresses files
- `Main` handles user interaction

### 3. Composition
```java
private CompressionSettings settings;

public VideoCompressor(CompressionSettings settings) {
     this.settings = settings;
}
```

## 📊 Examples

### Example 1: Single file

```bash
MKV file path: /home/user/videos/movie.mkv
Output path: /home/user/compressed/movie_compressed.mkv

=== Compression Settings ===
Choose quality [2]: 2
Keep all audio tracks? (Y/n): Y
Keep all subtitles? (Y/n): Y

=== Starting Compression ===
Original size: 4.50 GB
Compressed size: 2.80 GB
Reduction: 37.78%
```

### Example 2: Batch compression

```bash
Directory with MKV files: /home/user/series/season1
Output directory: /home/user/compressed

Found 10 MKV files

[1/10] Processing: episode01.mkv
...
[10/10] Processing: episode10.mkv

✓ Batch compression completed!
```

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

**ISmael COmerlato**

- GitHub: [@IsmaelComerlato](https://github.com/IsmaelComerlato)
- Email: ismacomerlato@gmail.com

## 🙏 Acknowledgements

- [FFmpeg](https://ffmpeg.org/) - video processing toolkit
- Java community - docs and support

---

⭐ If this project helped you, consider giving it a star on GitHub!
