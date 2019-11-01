package com.inori.music.dao.hbase;

import com.inori.music.pojo.FileImg;
import javafx.util.Pair;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 仅用于存储图片信息，不允许存储其他文件
 */
@Component
public class HImageDao {
    private static final String TABLE_NAME = "tbl_image";
    private static final String DATAINFO = "data_info";
    private static final String METAINFO = "meta_info";

    public static class IMGINFO {

        static final String BINARY = "binary";
        static final String FILE_TYPE = "file_type";
    }

    public HImageDao() {
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
        List<String> columnFamilies = Arrays.asList(DATAINFO, METAINFO);
        boolean table = HBaseUtils.createTable(TABLE_NAME, columnFamilies);
        System.out.println("表创建结果:" + table);
    }



    public void insert(FileImg img) {
        List<Pair<String, byte[]>> dataPairs = Arrays.asList(
                new Pair<>(IMGINFO.BINARY, img.getData())
        );
        List<Pair<String, byte[]>> metaPairs = Arrays.asList(
                new Pair<>(IMGINFO.FILE_TYPE, img.getFiletype().getBytes())
        );

        HBaseUtils.putRowBuffer(TABLE_NAME, img.getFilename(), DATAINFO, dataPairs);
        HBaseUtils.putRowBuffer(TABLE_NAME, img.getFilename(), METAINFO, metaPairs);
    }

    public FileImg getById(String rowkey) {
        Result result = HBaseUtils.getRow(TABLE_NAME, rowkey);
        return parseToFileImg(result);
    }

    public static FileImg parseToFileImg(Result result) {
        String filename = Bytes.toString(result.getRow());
        String fileType = Bytes.toString(result.getValue(METAINFO.getBytes(), IMGINFO.FILE_TYPE.getBytes()));
        byte[] buffer = result.getValue(DATAINFO.getBytes(), IMGINFO.BINARY.getBytes());
        return new FileImg(buffer, filename, fileType);
    }
}
