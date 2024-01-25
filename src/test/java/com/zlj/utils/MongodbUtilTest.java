package com.zlj.utils;

import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.*;

class MongodbUtilTest {
    @Test
    public void insertTest() {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("id", 1);
        map.put("name", "java");
        map.put("age", 1);
        map.put("birthday", new Date());
        MongodbUtil mongodbUtil = MongodbUtil.getInstance("mongodb://mongo:MongoDB_863*^#@10.1.56.161:27017", "test", "t1");
        mongodbUtil.insert(map);
    }

    @Test
    public void deleteTest() {
        MongodbUtil mongodbUtil = MongodbUtil.getInstance("mongodb://mongo:MongoDB_863*^#@10.1.56.161:27017", "test", "t1");
        mongodbUtil.delete("name", "java");
    }

    @Test
    public void updataoneTest() {
        MongodbUtil mongodbUtil = MongodbUtil.getInstance("mongodb://mongo:MongoDB_863*^#@10.1.56.161:27017", "test", "t1");
        mongodbUtil.updataone("name", "jaxxxx", "name", "jaxxxx1");
    }

    @Test
    public void updateTest() {
        MongodbUtil mongodbUtil = MongodbUtil.getInstance("mongodb://mongo:MongoDB_863*^#@10.1.56.161:27017", "test", "t1");
        mongodbUtil.update("name", "jaxxxx1", "name", "dfdfdfdfdf");
    }

    @Test
    public void selectTest() {
        MongodbUtil mongodbUtil = MongodbUtil.getInstance("mongodb://mongo:MongoDB_863*^#@10.1.56.161:27017", "test", "t1");
        List<Document> documents = mongodbUtil.select("name", "dfdfdfdfdf");
        for (Document document : documents) {
            System.out.println(document);
        }
    }

}