# Guia de Instalação do FFmpeg

Este projeto requer FFmpeg para funcionar. Siga as instruções para o seu sistema operacional:

## 🐧 Linux

### Ubuntu/Debian
```bash
sudo apt update
sudo apt install ffmpeg
```

### Fedora
```bash
sudo dnf install ffmpeg
```

### Arch Linux
```bash
sudo pacman -S ffmpeg
```

### Verificar instalação
```bash
ffmpeg -version
```

## 🍎 macOS

### Usando Homebrew (recomendado)
```bash
brew install ffmpeg
```

### Verificar instalação
```bash
ffmpeg -version
```

## 🪟 Windows

### Método 1: Instalação Manual

1. Acesse https://ffmpeg.org/download.html
2. Baixe a versão "Windows builds from gyan.dev"
3. Extraia o arquivo ZIP para `C:\ffmpeg`
4. Adicione ao PATH:
   - Abra "Painel de Controle" → "Sistema" → "Configurações avançadas do sistema"
   - Clique em "Variáveis de Ambiente"
   - Em "Variáveis do sistema", encontre "Path" e clique em "Editar"
   - Clique em "Novo" e adicione: `C:\ffmpeg\bin`
   - Clique em "OK" em todas as janelas

5. Reinicie o terminal/prompt de comando

### Método 2: Usando Chocolatey

```powershell
choco install ffmpeg
```

### Verificar instalação

Abra um novo terminal/CMD e execute:
```cmd
ffmpeg -version
```

## ✅ Teste Final

Após a instalação, teste com:

```bash
ffmpeg -version
ffprobe -version
```

Ambos devem exibir informações de versão sem erros.

## 🆘 Problemas Comuns

### "ffmpeg não é reconhecido como comando"

**Windows**: O PATH não foi configurado corretamente. Reinicie o terminal após adicionar ao PATH.

**Linux/Mac**: FFmpeg não foi instalado corretamente. Tente reinstalar.

### Permissões negadas

**Linux/Mac**: 
```bash
sudo apt install ffmpeg  # Use sudo
```

## 📚 Mais Informações

- Documentação oficial: https://ffmpeg.org/documentation.html
- Wiki: https://trac.ffmpeg.org/wiki
