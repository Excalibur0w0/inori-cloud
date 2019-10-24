package com.inori.music.test;

import com.inori.music.dao.hbase.HBaseUtils;
import com.inori.music.dao.hbase.HSongDao;
import com.inori.music.pojo.TblSong;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class HSongTest {
    HSongDao hSongDao = new HSongDao();

    @Test
    public void insertTest() {
        Date now = new Date(System.currentTimeMillis());

        TblSong song = new TblSong();
        song.setSongName("dragon");
        song.setSongAuthor("bakabka");
        song.setSongUploader("aaaaaaaaaaaaaaaaa");
        song.setUuid(UUID.randomUUID().toString().substring(0, 16));
        song.setCreatedAt(now);
        song.setUpdatedAt(now);

        hSongDao.insert(song);
    }

    @Test
    public void getAll() {
        hSongDao.findAll().forEach(song -> {
            System.out.println(song.toString());
        });
    }

    @Test
    public void getAllByUploader() {
        for (TblSong tblSong : this.hSongDao.findAllByUploader("inori")) {
            System.out.println(tblSong);
        }
    }

    @Test
    public void searchByKeyWords() {
        String keywords = "dragon";
        this.hSongDao.findAllByKeyWords(keywords).forEach(song -> {
            System.out.println(song);
        });
    }

    @Test
    public void testRandom() {
        List<String> strs = new LinkedList<>();

        for (int i = 0; i < 1000; i++) {
            strs.add(UUID.randomUUID().toString().substring(0, 16));
        }

        strs.forEach(mem -> {
            System.out.println(mem);
        });
    }
}
