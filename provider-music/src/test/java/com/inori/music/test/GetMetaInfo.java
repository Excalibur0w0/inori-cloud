package com.inori.music.test;

import com.inori.music.dao.hbase.HSongChunkDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v23Frame;
import org.jaudiotagger.tag.id3.ID3v23Tag;
import org.junit.Test;

import java.io.*;
import java.sql.Time;

public class GetMetaInfo {
    private HSongChunkDao hSongChunkDao = new HSongChunkDao();

    @Test
    public void readLyric() throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        String filePath = "/home/inori0w0/音乐/CloudMusic/Sawako碎花 - 帝国少女（Cover 初音ミク）.mp3";
        MP3File f = (MP3File) AudioFileIO.read(new File(filePath));
        ID3v23Tag tag;
        AbstractID3v2Tag tagV2=f.getID3v2Tag();
        if(tagV2 instanceof ID3v23Tag){
            tag=(ID3v23Tag)tagV2;
        }else{
            tag = new ID3v23Tag(tagV2);
        }
        TagField tagField = tag.getFirstField(FieldKey.LYRICIST);


        System.out.println( tagField.toString());


    }

    /**
     *设置ID3标签进入MP3文件中
     *copyrightInfoSave是一个实体
     */
    public void structAudioTag(String filePath,String lyricPath,String coverPath) throws Exception{
        MP3File f = (MP3File) AudioFileIO.read(new File(filePath));
        ID3v23Tag tag;
        AbstractID3v2Tag tagV2=f.getID3v2Tag();
        if(tagV2 instanceof ID3v23Tag){
            tag=(ID3v23Tag)tagV2;
        }else{
            tag = new ID3v23Tag(tagV2);
        }
        tag.setField(FieldKey.ARTIST, "ffff");
        tag.setField(FieldKey.ALBUM, "fafdas");
        tag.setField(FieldKey.TITLE, "asdf");
        tag.setField(FieldKey.COMMENT, "");
        String lyric = readToString(lyricPath);
        tag.setField(FieldKey.LYRICS,lyric);

        // 设置歌曲封面图片
        tag.deleteArtworkField();
        Artwork newArtwork= Artwork.createArtworkFromFile(new File(coverPath));
        tag.setField(newArtwork);
        tag.addField(newArtwork);
        f.setID3v2Tag(tag);
        f.save();
    }

    /**
     *读取歌词文件，并返回string类型
     */
    public String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
            return new String(filecontent, encoding);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *读取MP3文件的ID3标签信息
     */
    @Test
    public void readId3Tag() throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        String url = "/home/inori0w0/音乐/CloudMusic/EMI MARIA - この街で出逢って.mp3";
        File sourceFile = new File(url);
        MP3File file = (MP3File) AudioFileIO.read(sourceFile);
        Tag tag = file.getTag();
        MP3AudioHeader mp3AudioHeader = file.getMP3AudioHeader();
        //歌曲码率
        String bitRate = mp3AudioHeader.getBitRate();
        //获取MP3时长
        int trackLength = mp3AudioHeader.getTrackLength();
        int min = trackLength / 60;
        int second = trackLength % 60;
        String length = min + ":" + second;
        System.out.println(bitRate +" -- "+length);
    }


    @Test
    public void getMetaInfoByStream() throws IOException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException {
        String md5 = "53eca629b56d3aa45831eaaed1f3a7f6";
        InputStream in = hSongChunkDao.getAllChunksMergeInStream(md5);
        File file = new File("/dev/shm/" + md5 + ".mp3");
        FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
        byte[] buffer = new byte[1024];
        int len = 0;

        while((len = in.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        fos.flush();
        fos.close();
        in.close();

        MP3File mp3File = (MP3File) AudioFileIO.read(file);

        //歌名
        ID3v23Frame songnameFrame = (ID3v23Frame) mp3File.getID3v2Tag().frameMap.get("TIT2");
        String songName = songnameFrame.getContent();

        System.out.println(songName);


    }


    @Test
    public void checkNewMD5() throws IOException {
        String md5 = "53eca629b56d3aa45831eaaed1f3a7f6";
        InputStream in = hSongChunkDao.getAllChunksMergeInStream(md5);
        String newMd5 = DigestUtils.md5Hex(in);

        System.out.println(newMd5);
    }

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
