package com.zlj.utils;


import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.Map;

/**
 * * 时序数据库 InfluxDB 连接
 * * @author Dai_LW
 * *
 */
public class InfluxDbUtil {


    private static String database;
    private static InfluxDB influxDB;
    private String measurement;//表名

    private InfluxDbUtil(String database) {

        this.database = database;
    }

    public static InfluxDbUtil getInstance(String username, String password, String openurl, String database) {
//创建 连接
        InfluxDbUtil influxDbUtil = new InfluxDbUtil(database);
        if (influxDB == null) {
            influxDB = InfluxDBFactory.connect(openurl, username, password);
            influxDB.createDatabase(database);
        }
        try {
            Pong pong = influxDB.ping();
            if (pong != null) {
                System.out.println("连接上了");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        influxDbUtil.createRetentionPolicy();

        influxDB.deleteDatabase(database);
        influxDB.createDatabase(database);
        return influxDbUtil;
    }


    /**
     * * 设置数据保存策略
     * * defalut 策略名 /database 数据库名/ 30d 数据保存时限30天/ 1 副本个数为1/ 结尾DEFAULT 表示 设为默认的策略
     */
    public void createRetentionPolicy() {
        String command = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s DEFAULT",
                "defalut", database, "30d", 1);
        this.query(command);
    }

    /**
     * * 查询
     * * @param command 查询语句
     * * @return
     */
    public QueryResult query(String command) {
        return influxDB.query(new Query(command, database));
    }

    /**
     * * 插入
     * * @param tags 标签
     * * @param fields 字段
     */
    public void insert(Map<String, String> tags, Map<String, Object> fields, String measurement) {
        Builder builder = Point.measurement(measurement);
        builder.tag(tags);
        builder.fields(fields);

        influxDB.write(database, "", builder.build());
    }

    /**
     * * 删除
     * * @param command 删除语句
     * * @return 返回错误信息
     */
    public String deleteMeasurementData(String command) {
        QueryResult result = influxDB.query(new Query(command, database));
        return result.getError();
    }

    /**
     * * 创建数据库
     * * @param dbName
     */
    @SuppressWarnings("deprecation")
    public void createDB(String dbName) {
        influxDB.createDatabase(dbName);
    }

    /**
     * * 删除数据库
     * * @param dbName
     */
    public void deleteDB(String dbName) {
        influxDB.deleteDatabase(dbName);
    }

}