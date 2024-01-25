package com.zlj.utils;

import org.influxdb.dto.QueryResult;
import org.junit.jupiter.api.Test;
import org.json.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class InfluxDbUtilTest {
    private static String openurl = "http://10.1.53.65:8086";//连接地址
    private static String username = "zxj2";//用户名
    private static String password = "zh123456";//密码
    private static String database = "Databank";//数据库
    @Test
    void query() {
    }

    @Test
    void insert() {
        InfluxDbUtil influxDB = InfluxDbUtil.getInstance(username, password, openurl, database);

        Map<String, String> tags = new HashMap<>();
        Map<String, Object> fields = new HashMap<>();
        tags.put("TAG_NAME", "abc");
        fields.put("TAG_VALUE", 111);
        try {
            fields.put("timestamp", new Date().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        influxDB.insert(tags, fields, "dm_zlj_usql_kafka2influxdb_dw");
        //查询
        QueryResult result = influxDB.query("select * from dm_zlj_usql_kafka2influxdb_dw; ");
        System.out.println(result);
    }

    @Test
    void deleteMeasurementData() {









    }


}