package com.inori.music.dao.hbase;

import com.google.common.primitives.Longs;
import com.inori.music.pojo.FileChunk;
import javafx.util.Pair;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class HSongChunkDao {
    private static final String TABLE_NAME = "tbl_song_chunk";
    private static final String DATAINFO = "data_info";
//    private static final String CHUNKINFO = "chunk_info";


    public static class CHUNKDATA {
        static final String BINARY = "binary";      // datainfo
//        static final String CHUNKPOS = "chunk_pos"; // chunk_info 标记块属于第几块
//        static final String CHUNKTOTAL = "chunk_total"; // chunk_info 标记总快数
//        static final String MARKER = "marker";      // chunk_info 属于哪一个文件的标记 ps: (md5)

    }

    public HSongChunkDao () {
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
        List<String> columnFamilies = Arrays.asList(DATAINFO);
        boolean table = HBaseUtils.createTable(TABLE_NAME, columnFamilies);
        System.out.println("表创建结果:" + table);
    }


    public void insert(String md5, byte[] buffer, Long curIndex, Long totalChunks) {
        StringBuffer stringBuffer = new StringBuffer(md5);
        stringBuffer.append("-");
        stringBuffer.append(fillZero(curIndex, totalChunks));
        stringBuffer.append("-");
        stringBuffer.append(totalChunks);
        String marker = stringBuffer.toString();
        List<Pair<String, byte[]>> dataPairs = Arrays.asList(
                new Pair<>(CHUNKDATA.BINARY, buffer)
        );

        HBaseUtils.putRowBuffer(TABLE_NAME, marker, DATAINFO, dataPairs);
    }

    public List<FileChunk> getAllChunksByMd5(String md5) {
        RowFilter rowFilter = new RowFilter(
                CompareOperator.EQUAL,
                new SubstringComparator(md5)
        );

        FilterList filterList = new FilterList();
        filterList.addFilter(rowFilter);

        ResultScanner scanner = HBaseUtils.getScanner(TABLE_NAME, filterList);
        List<FileChunk> chunks = null;

        if (scanner != null) {
            chunks = new ArrayList<>();
            List<FileChunk> finalChunks = chunks;

            scanner.forEach(result -> {
                FileChunk chunk = parseResultToChunk(result);
                if (chunk != null) {
                    finalChunks.add(chunk);
                }
            });
            scanner.close();
        }

        return chunks;
    }

    public byte[] getChunkByMd5(String md5, Long curIndex, Long totalChunks) {
        StringBuffer stringBuffer = new StringBuffer(md5);
        stringBuffer.append("-");
        stringBuffer.append(fillZero(curIndex, totalChunks));
        stringBuffer.append("-");
        stringBuffer.append(totalChunks);
        String marker = stringBuffer.toString();
        Result result = HBaseUtils.getRow(TABLE_NAME, marker);

        if (result != null) {
            byte[] buf = result.getValue(Bytes.toBytes(DATAINFO), Bytes.toBytes(CHUNKDATA.BINARY));

            return buf;
        }

        return null;
    }

    public InputStream getInputStreamByMd5(String md5, Long curIndex, Long totalChunks) {
        byte[] bytes = this.getChunkByMd5(md5, curIndex, totalChunks);

        if (bytes != null) {
            return new ByteArrayInputStream(bytes);
        }

        return null;
    }

    // 保证rowkey的字典序
    public String fillZero(Long num, Long total) {
        int digitNum = getDigit(num);
        int digitTotal = getDigit(total);

        if (digitNum < digitTotal) {
            StringBuffer stringBuffer = new StringBuffer();
            int subDigit = digitTotal - digitNum;
            for (int i = 0; i < subDigit; i++) {
                stringBuffer.append("0");
            }
            stringBuffer.append(num);
            return stringBuffer.toString();
        } else {
            return num.toString();
        }
    }

    public static Integer getDigit(Long dig) {
        int count = 1;

        while(dig / 10 != 0) {
            count++;
            dig /= 10;
        }

        return count;
    }


    public static FileChunk parseResultToChunk(Result result) {
        try {
            String rowkey = Bytes.toString(result.getRow());
            String[] msgs = rowkey.split("-");
            if (msgs.length == 3) {
                String marker = msgs[0];
                Long index = Long.parseLong(msgs[1]);
                Long total = Long.parseLong(msgs[2]);
                byte[] data = result.getValue(DATAINFO.getBytes(), CHUNKDATA.BINARY.getBytes());

                return new FileChunk(marker, index, total, data);
            } else {
                throw new RuntimeException("Error： try to parse chunk to FileChunk");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
