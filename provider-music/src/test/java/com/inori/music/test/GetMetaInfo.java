package com.inori.music.test;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.flac.FlacFileReader;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.id3.ID3v23Frame;
import org.jaudiotagger.audio.AudioFile;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Time;

public class GetMetaInfo {
    @Test
    public void getSongInfo() throws FileNotFoundException {
        String filePath = "/home/inori0w0/音乐/CloudMusic/凑诗 - 故人叹 (王朝再编曲版).mp3";

        File file = new File(filePath);
//
//        FlacFileReader fileReader=new FlacFileReader();
//        AudioFile read = fileReader.read(new File(flacpath));

        try {

//         读取文件信息
            MP3File mp3File = (MP3File) AudioFileIO.read(new File(filePath));
//            获取头
            MP3AudioHeader audioHeader = (MP3AudioHeader) mp3File.getAudioHeader();

            //歌名
            ID3v23Frame songnameFrame = (ID3v23Frame) mp3File.getID3v2Tag().frameMap.get("TIT2");
            String songName = songnameFrame.getContent();
            //歌手
            ID3v23Frame artistFrame = (ID3v23Frame) mp3File.getID3v2Tag().frameMap.get("TPE1");
            String artist = artistFrame.getContent();
            //专辑
            ID3v23Frame albumFrame = (ID3v23Frame) mp3File.getID3v2Tag().frameMap.get("TALB");
            String album = albumFrame.getContent();
            //时长
            int duration = audioHeader.getTrackLength();

            System.out.println(songName + " " + artist + " " + album + " " + secondToDate(duration));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 秒转换为指定格式的日期
     *
     * @param second
     * @return
     */
    public static Time secondToDate(int second) {
        //转换为毫秒,但需要减去最基础的8小时
        Time time = new Time(second * 1000- 8 * 60 * 60 * 1000);
        return time;
    }
}
