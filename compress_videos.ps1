# Create output directory if it doesn't exist
$outputDir = "app/src/main/assets/mp4_compressed"
New-Item -ItemType Directory -Force -Path $outputDir

# Extract FFmpeg
Expand-Archive -Path ffmpeg.zip -DestinationPath ffmpeg -Force
$ffmpegPath = (Get-ChildItem -Path "ffmpeg" -Recurse -Filter "ffmpeg.exe").FullName
$env:Path = "$env:Path;$(Split-Path $ffmpegPath)"

# Get all MP4 files
$videos = Get-ChildItem -Path "app/src/main/assets/mp4" -Filter "*.mp4"

foreach ($video in $videos) {
    $outputFile = Join-Path $outputDir $video.Name
    
    # Modified compression settings for ~40% reduction:
    # -c:v libx264: Use H.264 codec
    # -crf 32: Higher compression (still acceptable quality for sign language)
    # -preset veryslow: Maximum compression efficiency
    # -vf "scale=iw*0.6:ih*0.6": Reduce resolution by 40%
    # -maxrate 500k -bufsize 1000k: Limit bitrate
    # -movflags +faststart: Optimize for web playback
    # -an: Remove audio (since these are sign language videos)
    # -y: Overwrite output file if it exists
    & $ffmpegPath -y -i $video.FullName -c:v libx264 -crf 32 -preset veryslow -vf "scale=iw*0.6:ih*0.6" -maxrate 500k -bufsize 1000k -movflags +faststart -an $outputFile
    
    # Only show results if the output file exists and has content
    if (Test-Path $outputFile) {
        $originalSize = (Get-Item $video.FullName).Length
        $newSize = (Get-Item $outputFile).Length
        
        if ($newSize -gt 1000) {  # Only show if file is larger than 1KB
            $savings = [math]::Round(100 * (1 - $newSize/$originalSize), 2)
            
            Write-Host "Compressed $($video.Name)"
            Write-Host "Original size: $([math]::Round($originalSize/1KB, 2)) KB"
            Write-Host "New size: $([math]::Round($newSize/1KB, 2)) KB"
            Write-Host "Space saved: $savings%`n"
        } else {
            Write-Host "Error compressing $($video.Name) - output file too small"
            Remove-Item $outputFile -Force  # Remove the invalid file
        }
    } else {
        Write-Host "Error compressing $($video.Name) - no output file created"
    }
}
