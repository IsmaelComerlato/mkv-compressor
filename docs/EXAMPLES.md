# Exemplos de Uso

## 📖 Índice

1. [Compressão Básica](#1-compressão-básica)
2. [Configurações Personalizadas](#2-configurações-personalizadas)
3. [Compressão em Lote](#3-compressão-em-lote)
4. [Análise de Vídeo](#4-análise-de-vídeo)
5. [Uso Programático](#5-uso-programático)

## 1. Compressão Básica

### Via Menu Interativo

```bash
mvn exec:java -Dexec.mainClass="com.mkvcompressor.Main"

# Escolha opção 1
# Digite o caminho do arquivo
# Aceite configurações padrão (Enter)
```

### Resultado Esperado

```
Analisando arquivo: filme.mkv
Tamanho original: 4.50 GB

=== Compressão Concluída ===
Tamanho original: 4.50 GB
Tamanho comprimido: 2.80 GB
Redução: 37.78%
```

## 2. Configurações Personalizadas

### Alta Qualidade (para arquivos importantes)

```
Escolha a qualidade [2]: 1
Manter todos os áudios? (S/n): S
Manter todas as legendas? (S/n): S
```

**Resultado**: ~15-25% de redução, qualidade máxima

### Compressão Agressiva (para arquivos grandes)

```
Escolha a qualidade [2]: 3
Manter todos os áudios? (S/n): n
Manter todas as legendas? (S/n): S
```

**Resultado**: ~50-60% de redução, qualidade ainda boa

## 3. Compressão em Lote

### Exemplo: Comprimindo uma série completa

```bash
# Estrutura de pastas
series/
  temporada1/
    episodio01.mkv (1.2 GB)
    episodio02.mkv (1.1 GB)
    episodio03.mkv (1.3 GB)
    ...

# Execute
mvn exec:java -Dexec.mainClass="com.mkvcompressor.Main"

# Escolha opção 2
Diretório com arquivos MKV: series/temporada1
Diretório de saída: output/temporada1

# Resultado
Encontrados 10 arquivos MKV
[1/10] Processando: episodio01.mkv
  Original: 1.20 GB → Comprimido: 750 MB (37.5% redução)
[2/10] Processando: episodio02.mkv
  Original: 1.10 GB → Comprimido: 680 MB (38.2% redução)
...
```

## 4. Análise de Vídeo

### Código Java

```java
VideoAnalyzer analyzer = new VideoAnalyzer();
VideoFile video = analyzer.analyzeVideo("filme.mkv");

System.out.println("Tamanho: " + VideoFile.formatFileSize(video.getOriginalSize()));
System.out.println("Codec: " + video.getVideoCodec());
System.out.println("Resolução: " + video.getResolution());
System.out.println("Áudios: " + video.getAudioTracks().size());
System.out.println("Legendas: " + video.getSubtitleTracks().size());
```

### Saída

```
Tamanho: 4.50 GB
Codec: H.264
Resolução: 1920x1080
Áudios: 3
Legendas: 5
```

## 5. Uso Programático

### Exemplo Completo

```java
import com.mkvcompressor.model.CompressionSettings;
import com.mkvcompressor.model.VideoFile;
import com.mkvcompressor.service.VideoAnalyzer;
import com.mkvcompressor.service.VideoCompressor;

public class MeuApp {
    public static void main(String[] args) {
        try {
            // 1. Configurar compressão
            CompressionSettings settings = new CompressionSettings();
            settings.setQuality("high");
            settings.setCrf(20);
            settings.setPreset("slow");
            
            // 2. Analisar vídeo
            VideoAnalyzer analyzer = new VideoAnalyzer();
            VideoFile video = analyzer.analyzeVideo("input/filme.mkv");
            
            // 3. Comprimir
            VideoCompressor compressor = new VideoCompressor(settings);
            compressor.compressVideo(video, "output/filme_compressed.mkv");
            
            // 4. Exibir resultado
            System.out.printf("Redução: %.2f%%\n", 
                video.getCompressionPercentage());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## 📊 Tabela de Qualidade vs Tamanho

| CRF | Qualidade | Redução Típica | Uso Recomendado |
|-----|-----------|----------------|-----------------|
| 18-20 | Excelente | 15-25% | Arquivamento, blu-ray |
| 21-23 | Muito Boa | 30-40% | Uso geral (recomendado) |
| 24-26 | Boa | 45-55% | Streaming, backup |
| 27-30 | Aceitável | 55-65% | Espaço limitado |

## 🎯 Casos de Uso Reais

### Caso 1: Biblioteca de Filmes

**Objetivo**: Economizar espaço mantendo qualidade

```
Configuração:
- Quality: medium
- CRF: 23
- Preset: medium

Resultado:
100 filmes × 4.5 GB = 450 GB
→ 100 filmes × 2.8 GB = 280 GB
Economizou: 170 GB (37.8%)
```

### Caso 2: Séries para Backup

**Objetivo**: Máxima compressão, qualidade aceitável

```
Configuração:
- Quality: low
- CRF: 28
- Preset: fast

Resultado:
10 temporadas × 10 episódios × 1.2 GB = 120 GB
→ 10 temporadas × 10 episódios × 550 MB = 55 GB
Economizou: 65 GB (54.2%)
```

### Caso 3: Arquivamento de Vídeos Pessoais

**Objetivo**: Preservar qualidade máxima

```
Configuração:
- Quality: high
- CRF: 18
- Preset: slow

Resultado:
Vídeos familiares: 50 GB
→ Vídeos comprimidos: 40 GB
Economizou: 10 GB (20%)
Qualidade: Visualmente idêntica
```

## ⚙️ Parâmetros Avançados

### Entendendo os Presets

| Preset | Velocidade | Compressão | Quando Usar |
|--------|------------|------------|-------------|
| ultrafast | ⚡⚡⚡⚡⚡ | ⭐ | Testes rápidos |
| fast | ⚡⚡⚡⚡ | ⭐⭐ | Muitos arquivos |
| medium | ⚡⚡⚡ | ⭐⭐⭐ | Uso geral |
| slow | ⚡⚡ | ⭐⭐⭐⭐ | Melhor compressão |
| veryslow | ⚡ | ⭐⭐⭐⭐⭐ | Máxima qualidade |

### Configuração Personalizada

```java
CompressionSettings settings = new CompressionSettings();

// Para arquivos 4K
settings.setCrf(20);        // Qualidade alta
settings.setPreset("slow"); // Melhor compressão

// Para arquivos SD (480p/720p)
settings.setCrf(24);         // Pode ser maior
settings.setPreset("medium"); // Balanço

// Para compressão rápida
settings.setCrf(23);
settings.setPreset("fast");
```

## 🚨 Dicas Importantes

1. **Sempre faça backup** antes de deletar os originais
2. **Teste com um arquivo** antes de comprimir em lote
3. **CRF menor = melhor qualidade** mas arquivo maior
4. **Preset slower = melhor compressão** mas leva mais tempo
5. **H.265 é 30-50% mais eficiente** que H.264

## 🔍 Solução de Problemas

### Compressão muito lenta

```java
settings.setPreset("fast"); // Use preset mais rápido
```

### Arquivo ficou maior

```java
// O vídeo já estava bem comprimido
// Tente CRF menor ou pule este arquivo
if (compressedSize > originalSize) {
    System.out.println("Mantendo arquivo original");
}
```

### Qualidade insatisfatória

```java
settings.setCrf(18);        // Reduza CRF
settings.setPreset("slow"); // Use preset melhor
```
