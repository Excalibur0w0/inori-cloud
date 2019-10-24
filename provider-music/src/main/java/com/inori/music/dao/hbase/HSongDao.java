package com.inori.music.dao.hbase;

import com.inori.music.pojo.TblSong;
import com.inori.music.utils.MyStringUtils;
import javafx.util.Pair;
import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

@Component
public class HSongDao {
    private static final String TABLE_NAME = "tbl_song";
    private static final String BASEINFO = "base_info";
    private static final String STOREINFO = "store_info";

    public static class HSong {
        static final String SONG_NAME = "SONG_NAME";
        static final String CREATED_AT = "CREATED_AT";
        static final String UPDATED_AT = "UPDATED_AT";
        static final String SONG_AUTHOR = "SONG_AUTHOR";
        static final String SONG_ALBUM = "SONG_ALBUM";
        static final String SONG_UPLOADER = "SONG_UPLOADER";
        static final String STORE_PATH = "STORE_PATH";
    }

    public HSongDao () {
        try {
            if (!HBaseUtils.isTableExist(TABLE_NAME)) {
                create();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create() {
        // 新建表
        List<String> columnFamilies = Arrays.asList(BASEINFO, STOREINFO);
        boolean table = HBaseUtils.createTable(TABLE_NAME, columnFamilies);
        System.out.println("表创建结果:" + table);
    }

    public void insert(TblSong song) {
        if (StringUtils.isEmpty(song.getUuid())) {
            throw new RuntimeException("向Hbase song表插入时，rowKey不能为空!!");
        }

        List<Pair<String, String>> basePairs = this.filterInvalidPair(Arrays.asList(
                new Pair<>(HSong.SONG_NAME, song.getSongName()),
                new Pair<>(HSong.SONG_ALBUM, song.getSongAlbum()),
                new Pair<>(HSong.SONG_AUTHOR, song.getSongAuthor()),
                new Pair<>(HSong.SONG_UPLOADER, song.getSongUploader())
        ));
        List<Pair<String, String>> storePairs = this.filterInvalidPair(Arrays.asList(
                new Pair<>(HSong.CREATED_AT, Long.toString(song.getCreatedAt().getTime())),
                new Pair<>(HSong.UPDATED_AT, Long.toString(song.getUpdatedAt().getTime())),
                new Pair<>(HSong.STORE_PATH, song.getStorePath())
        ));

        HBaseUtils.putRow(TABLE_NAME, song.getUuid(), BASEINFO, basePairs);
        HBaseUtils.putRow(TABLE_NAME, song.getUuid(), STOREINFO, storePairs);
    }

    public TblSong getById(String rowkey) {
        Result result = HBaseUtils.getRow(TABLE_NAME, rowkey);
        TblSong tblSong = parseResultToSong(result);

        return tblSong;
    }


    public List<TblSong> findAllByAuthor(String author) {
        List<TblSong> songs = null;
        SingleColumnValueFilter songNameFilter = new SingleColumnValueFilter(
                BASEINFO.getBytes(),
                HSong.SONG_AUTHOR.getBytes(),
                CompareOperator.EQUAL,
                new SubstringComparator(author)
        );
        songNameFilter.setLatestVersionOnly(true);
        songNameFilter.setFilterIfMissing(true);
        FilterList filterList = new FilterList();
        filterList.addFilter(songNameFilter);

        ResultScanner scanner = HBaseUtils.getScanner(TABLE_NAME, filterList);

        if (scanner != null) {
            songs = new LinkedList<>();
            List<TblSong> finalSongs = songs;

            scanner.forEach(result -> {
                TblSong song = parseResultToSong(result);
                if (song != null) {
                    finalSongs.add(song);
                }
            });
            scanner.close();
        }
        return songs;
    }

    public List<TblSong> findAllByKeyWords(String keywords) {
        List<TblSong> songs = null;
        SubstringComparator subComp = new SubstringComparator(keywords);
        SingleColumnValueFilter songNameFilter = new SingleColumnValueFilter(
                BASEINFO.getBytes(),
                HSong.SONG_NAME.getBytes(),
                CompareOperator.EQUAL,
                subComp
        );
        SingleColumnValueFilter authorNameFilter = new SingleColumnValueFilter(
                BASEINFO.getBytes(),
                HSong.SONG_AUTHOR.getBytes(),
                CompareOperator.EQUAL,
                subComp
        );
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        songNameFilter.setFilterIfMissing(true);
        songNameFilter.setLatestVersionOnly(true);
        authorNameFilter.setFilterIfMissing(true);
        authorNameFilter.setLatestVersionOnly(true);
        filterList.addFilter(songNameFilter);
        filterList.addFilter(authorNameFilter);

        ResultScanner scanner = HBaseUtils.getScanner(TABLE_NAME, filterList);

        if (scanner != null) {
            songs = new LinkedList<>();
            List<TblSong> finalSongs = songs;

            scanner.forEach(result -> {
                TblSong song = parseResultToSong(result);
                if (song != null) {
                    finalSongs.add(song);
                }
            });
            scanner.close();
        }

        return songs;
    }

    public List<TblSong> findAllByUploader(String uploaderId) {
        List<TblSong> songs = null;
        SingleColumnValueFilter filter = new SingleColumnValueFilter(
                BASEINFO.getBytes(),
                HSong.SONG_UPLOADER.getBytes(),
                CompareOperator.EQUAL,
                new BinaryComparator(uploaderId.getBytes())
//                new SubstringComparator(uploaderId)
        );

        filter.setFilterIfMissing(true);
        filter.setLatestVersionOnly(true);
        FilterList filterList = new FilterList();
        filterList.addFilter(filter);
        ResultScanner scanner = HBaseUtils.getScanner(TABLE_NAME, filterList);

        if (scanner != null) {
            songs = new LinkedList<>();
            List<TblSong> finalSongs = songs;

            scanner.forEach(result -> {
                TblSong song = parseResultToSong(result);
                if (song != null) {
                    finalSongs.add(song);
                }
            });
            scanner.close();
        }

        return songs;
    }

    public List<TblSong> findAll() {
        ResultScanner scanner = HBaseUtils.getScanner(TABLE_NAME);
        List<TblSong> songs = null;

        if (scanner != null) {
            songs = new LinkedList<>();
            List<TblSong> finalSongs = songs;

            scanner.forEach(result -> {
                TblSong song = parseResultToSong(result);
                if (song != null) {
                    finalSongs.add(song);
                }
            });
            scanner.close();
        }

        return songs;
    }


    public static List<Pair<String, String>> filterInvalidPair(List<Pair<String, String>> pairs) {
        List<Pair<String, String>> newPair = new LinkedList<>();

        for (Pair<String, String> pair :
                pairs) {
            String key = pair.getKey();
            String value = pair.getValue();
            if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
                newPair.add(pair);
            }
        }
        return newPair;
    }

    public static byte[] getValue(Result result, String family, String qualifier) {
        return result.getValue(Bytes.toBytes(family), Bytes.toBytes(qualifier));
    }

    public static TblSong parseResultToSong(Result result) {
        TblSong tblSong = null;
        if (result != null) {
            tblSong = new TblSong();
            String uuid = Bytes.toString(result.getRow());
            String songName = Bytes.toString(getValue(result, BASEINFO, HSong.SONG_NAME));
            String songAlbum = Bytes.toString(getValue(result, BASEINFO, HSong.SONG_ALBUM));
            String songAuthor = Bytes.toString(getValue(result, BASEINFO, HSong.SONG_AUTHOR));
            String songUploader = Bytes.toString(getValue(result, BASEINFO, HSong.SONG_UPLOADER));
            String storePath = Bytes.toString(getValue(result, STOREINFO, HSong.STORE_PATH));
            Date createdAt, updatedAt;

            try {
                createdAt = new Date(Bytes.toLong(getValue(result, STOREINFO, HSong.CREATED_AT)));
            } catch (Exception e) {
                createdAt = null;
            }
            try {
                updatedAt = new Date(Bytes.toLong(getValue(result, STOREINFO, HSong.UPDATED_AT)));
            } catch (Exception e) {
                updatedAt = null;
            }
            tblSong.setUuid(uuid);
            tblSong.setSongName(songName);
            tblSong.setSongAlbum(songAlbum);
            tblSong.setSongAuthor(songAuthor);
            tblSong.setSongUploader(songUploader);
            tblSong.setStorePath(storePath);
            tblSong.setCreatedAt(createdAt);
            tblSong.setUpdatedAt(updatedAt);
            result = null;
        }

        return tblSong;
    }
}
