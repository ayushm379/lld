package io.at.learn.lld04;


import lombok.AllArgsConstructor;
import lombok.Getter;

public class Adapter {

    public static void main(String[] args) {
        MediaPlayer mediaPlayer = new AdvancedMediaPlayer();
        mediaPlayer.play(MediaFileType.MP4, "video.mp4");
        mediaPlayer.play(MediaFileType.MP3, "music.mp3");
        mediaPlayer.play(MediaFileType.HLF, "iphoneVideo.hlf");
    }

}

enum MediaFileType {
    MP4, MP3, HLF;
}

@Getter
@AllArgsConstructor
enum ErrorMessages {
    MEDIA_TYPE_NOT_SUPPORTED("Media Type Not Supported");

    private final String error;

}

interface MediaPlayer {
    void play(MediaFileType audioType, String filePath);
}

class LegacyPlayer {

    public void playMp4(String filePath) {
        System.out.printf("Playing MP4 %s \n", filePath);
    }

    public void playHlf(String filePath) {
        System.out.printf("Playing HLF %s \n", filePath);
    }

}

class LegacyAdapter implements MediaPlayer {
    
    private final LegacyPlayer legacyPlayer;
    
    public LegacyAdapter() {
        this.legacyPlayer = new LegacyPlayer();
    }

    @Override
    public void play(MediaFileType audioType, String filePath) {
        if(MediaFileType.MP4 == audioType) {
           legacyPlayer.playMp4(filePath);
        } else {
            legacyPlayer.playHlf(filePath);
        }
    }
}

class Mp3MediaPlayer implements MediaPlayer {

    @Override
    public void play(MediaFileType audioType, String filePath) {
        System.out.printf("Playing MP3 %s \n", filePath);
    }
}

// This below is the most important part of Adapter Design Pattern
class AdvancedMediaPlayer implements MediaPlayer {

    private final MediaPlayer mp3MediaPlayer;
    private final MediaPlayer legacyAdapter;

    public AdvancedMediaPlayer() {
        this.mp3MediaPlayer = new Mp3MediaPlayer();
        this.legacyAdapter = new LegacyAdapter();
    }

    @Override
    public void play(MediaFileType audioType, String filePath) {
        switch (audioType) {
            case MP3 -> mp3MediaPlayer.play(audioType, filePath);
            case MP4, HLF -> legacyAdapter.play(audioType, filePath);
            default -> throw new RuntimeException(ErrorMessages.MEDIA_TYPE_NOT_SUPPORTED.getError());
        }
    }

}