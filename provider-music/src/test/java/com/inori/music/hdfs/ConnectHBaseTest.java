package com.inori.music.hdfs;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.junit.Test;

import java.io.IOException;

@Log4j2
public class ConnectHBaseTest {

    @Test
    public void write2HBase() throws IOException {
        String value = "TEST";
        Configuration config = HBaseConfiguration.create();

        config.set("hbase.zookeeper.quorum","master,slave1,slave2");
        config.set("hbase.zookeeper.property.clientPort","2181");

        System.out.println("开始连接hbase");
        Connection connect = ConnectionFactory.createConnection(config);
        System.out.println(connect.isClosed());
        Admin admin = connect.getAdmin();
        System.out.println("连接成功");

        if(admin !=null){
            System.out.println(admin.getMaster().getServerName());
            try {
                //获取到数据库所有表信息
                HTableDescriptor[] allTable = admin.listTables();
                for (HTableDescriptor hTableDescriptor : allTable) {
                    System.out.println(hTableDescriptor.getNameAsString());
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("admin为空");
        }

//        Table table = connect.getTable(TableName.valueOf("midas_ctr_test"));
//        System.out.println("获取表数据成功");
//        for i :table.getScanner().iterator();

        TableName tableName = TableName.valueOf("RRRR");
        String cf = "cf1";


        if (!admin.tableExists(tableName)) {
            admin.createTable(new HTableDescriptor(tableName).addFamily(new HColumnDescriptor(cf)));
        }
        System.out.println("建表数据成功");
//
//        Table table = connect.getTable(tableName);
//        TimeStamp ts = new TimeStamp(new Date());
//        Date date = ts.getDate();
//        Put put = new Put(Bytes.toBytes(date.getTime()));
//        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("test"), Bytes.toBytes(value));
//        table.put(put);
//        table.close();
//        connect.close();
    }
}
