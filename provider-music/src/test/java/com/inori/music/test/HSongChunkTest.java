package com.inori.music.test;

import com.inori.music.dao.hbase.HSongChunkDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class HSongChunkTest {
    HSongChunkDao hSongChunkDao = new HSongChunkDao();

    @Test
    public void testMd5() {
        try {
            FileInputStream fis = new FileInputStream(new File("/home/inori0w0/下载/邓桥阳简历【BOSS直聘】.docx"));
            String md5 = DigestUtils.md5Hex(fis);

            System.out.println(md5);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertChunkTest() {
        File file = new File("/home/inori0w0/下载/邓桥阳简历【BOSS直聘】.docx");
        InputStream in = null;
        String md5;
        try {
            MultipartFile multipartFile = new MockMultipartFile("copy"+file.getName(),file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(),new FileInputStream(file));
            in = multipartFile.getInputStream();

            byte[] result = readInputStream(in);

            in = multipartFile.getInputStream();
            md5 = DigestUtils.md5Hex(in);
            System.out.println(md5);

            // d41d8cd98f00b204e9800998ecf8427e
            // 660439dcc3b292c44c6ff07d0130a61b
            hSongChunkDao.insert(md5, result, 0L, 1L);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getChunkTest() {

    }

    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
