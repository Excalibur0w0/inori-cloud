package com.inori.music.test;

import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

public class DateTest {

    @Test
    public void test() {
        Date now = new Date(System.currentTimeMillis());
        String timestamp = Long.toString(now.getTime());

        Date date = new Date(Long.parseLong(timestamp));

        System.out.println(date);
    }

    @Test
    public void test2() {
        System.out.println(Bytes.toLong(null));
    }

    @Test
    public void testUUID() {
        String uuid = UUID.randomUUID().toString().substring(0, 16);
        System.out.println(uuid.length());
    }
}
