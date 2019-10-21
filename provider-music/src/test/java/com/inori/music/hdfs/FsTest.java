package com.inori.music.hdfs;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class FsTest {
    private static final String HDFS_PATH = "hdfs://master:9000";
    private static final String HDFS_USER = "root";
    private static FileSystem fileSystem;
    @Before
    public void prepare() {
        try {
            Configuration configuration = new Configuration();
            configuration.set("dfs.replication", "3");
            fileSystem = FileSystem.get(new URI(HDFS_PATH), configuration, HDFS_USER);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @After
    public void destory() {
        fileSystem = null;
    }

    @Test
    public void mkDir() throws IOException {
        fileSystem.mkdirs(new Path("/hdfs-api/"));
    }

    @Test
    public void testPort() {
        Socket connect = new Socket();
        try {
            connect.connect(new InetSocketAddress("localhost", 9000),100);//建立连接
            boolean res = connect.isConnected();//通过现有方法查看连通状态
            System.out.println(res);//true为连通
        } catch (IOException e) {
            System.out.println("false");//当连不通时，直接抛异常，异常捕获即可
        }finally{
            try {
                connect.close();
            } catch (IOException e) {
                System.out.println("false");
            }
        }
    }

}
