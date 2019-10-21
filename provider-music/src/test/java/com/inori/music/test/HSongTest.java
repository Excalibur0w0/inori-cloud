package com.inori.music.test;

import com.inori.music.dao.hbase.HBaseUtils;
import com.inori.music.dao.hbase.HSongDao;
import com.inori.music.pojo.TblSong;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

public class HSongTest {
    HSongDao hSongDao = new HSongDao();

    @Test
    public void insertTest() {
        Date now = new Date(System.currentTimeMillis());

        TblSong song = new TblSong();
        song.setSongName("喜欢你");
        song.setSongAuthor("Beyond");
        song.setSongUploader("inori");
        song.setUuid(UUID.randomUUID().toString().substring(0, 16));
        song.setCreatedAt(now);
        song.setUpdatedAt(now);

        hSongDao.insert(song);
    }

    @Test
    public void getAll() {
        hSongDao.findAllByUploader("inori");
        ResultScanner scanner = HBaseUtils.getScanner("tbl_song");

//        scanner.forEach(mem -> {
//            System.out.println(mem.getRow().toString());
//        });
    }
}
