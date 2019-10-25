package com.inori.music.utils;

import com.inori.music.pojo.TblSong;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v23Frame;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;

import java.io.*;
import java.sql.Time;

/**
 * 首限于api， 只能先转化为文件对象， 为优化性能，往内存虚拟磁盘中写入
 */
public class MusicHelper {
    private File file;
    private static String baseDir = "/dev/shm/";
    private String fileType;

    public MusicHelper(String fileName, String fileType) {
        this.file = new File(baseDir + fileName + fileType);
    }

    public void write(InputStream in) {
        byte[] buffer = new byte[1024];
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file.getAbsolutePath());
            int len = 0;

            while((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
            out.close();
            in.close();

        } catch (FileNotFoundException e) {
            System.out.println("没有找到文件: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("MusicHelper读写异常");
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if(in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMP3MetaInfo(TblSong song) {
        AudioFile audioFile = null;
        try {
            audioFile = AudioFileIO.read(this.file);
        } catch (CannotReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException e) {
            e.printStackTrace();
        } catch (ReadOnlyFileException e) {
            e.printStackTrace();
        } catch (InvalidAudioFrameException e) {
            e.printStackTrace();
        }

        if (audioFile == null) {
            throw new RuntimeException("audioFile is null");
        }
        MP3File mp3File = (MP3File) audioFile;
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

        song.setSongName(songName);
        song.setSongAlbum(album);
        song.setSongAuthor(artist);
        song.setDuration(duration);
    }

    public byte[] getMP3Image() {
        return getMP3Image(this.file);
    }

    /**
     * 获取MP3封面图片
     * @param mp3File
     * @return
     */
    public static byte[] getMP3Image(File mp3File) {
        byte[] imageData = null;
        try {
            MP3File mp3file = new MP3File(mp3File);
            AbstractID3v2Tag tag = mp3file.getID3v2Tag();
            AbstractID3v2Frame frame = (AbstractID3v2Frame) tag.getFrame("APIC");
            FrameBodyAPIC body = (FrameBodyAPIC) frame.getBody();
            imageData = body.getImageData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageData;
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
