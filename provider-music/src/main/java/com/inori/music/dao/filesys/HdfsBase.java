package com.inori.music.dao.filesys;


import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


@Log4j2
public class HdfsBase {
    private static Configuration configuration;
    private static final String HDFS_PATH = "hdfs://master:9000";
    private static final String HDFS_USER = "root";

    static {
        configuration = new Configuration();
        configuration.set("dfs.replication", "3");

    }

    public FileSystem getFileSystem() throws IOException, InterruptedException {
        try {
            return FileSystem.get(new URI(HDFS_PATH), configuration, HDFS_USER);
        } catch (URISyntaxException e) {
            log.error("创建FileSystem时， URI的参数不正确");
            return null;
        }
    }

    public void mkDir() throws InterruptedException, IOException, URISyntaxException {
        FileSystem fileSystem = getFileSystem();
        fileSystem.mkdirs(new Path("/hdfs-api/test0"));
    }


    public static long upload2HDFSinOffset(String encryptfilename) throws Exception {
        return 0;
    }
}
