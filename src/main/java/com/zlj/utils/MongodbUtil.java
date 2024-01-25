package com.zlj.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MongodbUtil {
    private static MongodbUtil mongodbUtil;
    private static MongoCollection collection;
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    private MongodbUtil() {
    }

    public static synchronized MongodbUtil getInstance(String url, String databaseName, String collectionName) {
        mongoClient = MongoClients.create(url);
        database = mongoClient.getDatabase(databaseName);
        collection = database.getCollection(collectionName);
        mongodbUtil = new MongodbUtil();
        return mongodbUtil;
    }

    public void insert(Map<String, Object> valueMap) {
        Document document = new Document(valueMap);
        collection.insertOne(document);
    }

    public void delete(String column_name, String regex_value) {
        Document document = new Document(column_name, new Document("$regex", regex_value));
        collection.deleteMany(document);
    }

    public void updataone(String old_column_name, String old_regex_value, String new_column_name, String new_value) {
        Bson oldBson = Filters.regex(old_column_name, old_regex_value);
        Bson newBson = new Document("$set", new Document(new_column_name, new_value));
        collection.updateOne(oldBson, newBson);
    }

    public void update(String old_column_name, String old_regex_value, String new_column_name, String new_value) {
        BasicDBObject query = new BasicDBObject();
        String pattern = old_regex_value;
        Pattern r = Pattern.compile(pattern);
        query.put(old_column_name, old_regex_value);
        collection.updateMany(query, new Document("$set", new Document(new_column_name, new_value)));
    }

    public List<Document> select(String column_name, String regex_value) {
        MongoCursor cursor = collection.find(new Document(column_name, regex_value)).iterator();
        List<Document> documentList=new ArrayList<>();
        while (cursor.hasNext()) {
            Document document = (Document) cursor.next();
            documentList.add(document);
        }
        return documentList;
    }
}
