package com.mmr.mongodb;

import com.mongodb.Cursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.mongodb.client.model.DeleteOptions;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * 入门案例
 */
public class EntranceDemo {

    public static void main(String[] args) throws Exception{
        EntranceDemo demo = new EntranceDemo();

        //连接Mongo服务
        MongoClient mongoClient = demo.generateDBConnection();

        //连接到数据库
        MongoDatabase database = demo.getDatabase(mongoClient);

        //获取集合
        MongoCollection<Document> collection = demo.getCollection(database, "test");

        //写入数据
        //demo.writeDocument(collection);

        //查询数据
        demo.queryDocument(collection);
    }


    /*
        连接Mongo服务
     */
    private MongoClient generateDBConnection(){
/*
           连接Mongo服务
         */
        //MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);

        /*
           使用连接池 连接到Mongo服务
         */
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectTimeout(5000); //连接超时
        builder.socketTimeout(5000); //读写超时
        builder.connectionsPerHost(30); //每个地址的最大连接数 [这些连接将被放入池中，如果连接被耗尽，那么任何对地址的请求都将被阻塞，直到有连接被归还]

        /*
           用户校验
         */
        // 提供用户名和密码
        // MongoCredential credential = MongoCredential.createScramSha1Credential("username", "databaseName", "password".toCharArray());
        // 连接时校验用户
        // MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("192.168.0.103", 27017)), credential, builder.build());

        /*
           集群访问时，请求地址可以传入多个
         */
        MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("192.168.50.175", 27017)), builder.build());
        //System.out.println(mongoClient);

        return mongoClient;
    }

    /*
        连接到数据库
     */
    private MongoDatabase getDatabase(MongoClient mongoClient){
        /*
           连接到数据库
         */
        MongoDatabase db = mongoClient.getDatabase("test");
        //System.out.println(db);

        return db;
    }

    /*
        创建集合
     */
    private void createCollection(MongoDatabase db, String collectionName){
        db.createCollection(collectionName);
    }


    /*
        获取集合
     */
    private MongoCollection<Document> getCollection(MongoDatabase db, String collectionName){
        return db.getCollection(collectionName);
    }

    /*
        删除集合
     */
    private void deleteCollection(MongoCollection<Document> mongoCollection){
        mongoCollection.drop();
    }

    /*
        写入文档
     */
    private void writeDocument(MongoCollection<Document> mongoCollection){
/*
           写入文档
         */
        Random random = new Random();
        List<Document> docs = new ArrayList<>();
        for(int i=0;i<10;i++){
            Document doc = new Document();
            doc.append("name", "name" + i);
            doc.append("age", random.nextInt(50));
            docs.add(doc);
            //插入单个文档
            //mongoCollection.insertOne(doc);
        }
        //批量插入多个文档
        mongoCollection.insertMany(docs);
    }

    /**
     * 查询文档
     */
    private void queryDocument(MongoCollection<Document> collection){
        FindIterable<Document> result = null;
        MongoCursor<Document> cursor = null;

        //查询所有
        result = collection.find(new Document()); //对应着db.test.find({})
        cursor = result.iterator();
        while(cursor.hasNext()){
            Document document = cursor.next();
            System.out.println("find all: [ { " + document + " } ]");
            System.out.println("name: " + document.getString("name"));
            System.out.println("age: " + document.getInteger("age", 20));
        }

        // 条件查询
        // 查询name = name3的
        result = collection.find(Filters.eq("name", "name3"));
        cursor = result.iterator();
        while(cursor.hasNext()){
            Document doc = cursor.next();
            System.out.print("eq : [ { doc : " + doc + " }, ");
            System.out.print("{ name : " + doc.getString("name") + " }, ");
            System.out.println("{ age : " + doc.get("age") + " } ]");
        }
        // 查询age > 30的
        result = collection.find(Filters.gt("age", 30));
        // result = collection.find(new Document("age", new Document("$gt", 30)));
        cursor = result.iterator();
        while(cursor.hasNext()){
            Document doc = cursor.next();
            System.out.print("gt : [ { doc : " + doc + " }, ");
            System.out.print("{ name : " + doc.getString("name") + " }, ");
            System.out.println("{ age : " + doc.get("age") + " } ]");
        }
        // 查询name以n开头，以9结尾的
        result = collection.find(Filters.regex("name", Pattern.compile("^n.*9$")));
        cursor = result.iterator();
        while(cursor.hasNext()){
            Document doc = cursor.next();
            System.out.print("regex : [ { doc : " + doc + " }, ");
            System.out.print("{ name : " + doc.getString("name") + " }, ");
            System.out.println("{ age : " + doc.get("age") + " } ]");
        }

        // 查询name = name0 并且 age < 50的
        result = collection.find(Filters.and(Filters.eq("name", "name0"), Filters.lt("age", 50)));
        cursor = result.iterator();
        while(cursor.hasNext()){
            Document doc = cursor.next();
            System.out.print("and query : [ { doc : " + doc + " }, ");
            System.out.print("{ name : " + doc.getString("name") + " }, ");
            System.out.println("{ age : " + doc.get("age") + " } ]");
        }

        // 查询name = name0 或者 age < 50的
        result = collection.find(Filters.or(Filters.eq("name", "name0"), Filters.lt("age", 50)));
        cursor = result.iterator();
        while(cursor.hasNext()){
            Document doc = cursor.next();
            System.out.print("or query : [ { doc : " + doc + " }, ");
            System.out.print("{ name : " + doc.getString("name") + " }, ");
            System.out.println("{ age : " + doc.get("age") + " } ]");
        }

        //排序和分页
        //db.coll.find().limit().skip().sort()
        FindIterable<Document> pageResult = collection.find().limit(2).skip(2).sort(new Document("age", 1));

        //聚合查询 比如db.coll.aggregate([{}, {$group: {_id:null, count: {$sum: 1}}}, {$project: {'count': 1, '_id': 0}}])
        Document param = new Document();
        param.append("_id", null);
        param.append("count", 1);

        Document options = new Document();
        options.append("$project", new Document("count", 1).append("_id", 0));

        AggregateIterable<Document> aggregateResult = collection.aggregate(Arrays.asList(new Document("$group", param), options));
        MongoCursor<Document> mongoCursor = aggregateResult.iterator();
        while(mongoCursor.hasNext()){
            System.out.println(mongoCursor.next());
        }
    }

    /**
     * 更新文档 db.test.update({}, {$set: {'name': '更新后的名称'}})
     */
    private void updateDocument(MongoCollection<Document> collection){
        collection.updateMany(Filters.eq("name", "name0"), new Document("$set", new Document("name", "name1000")));
    }

    /**
     * 删除文档 db.test.remove({'name': '待删除的文档'})
     */
    private void removeDocument(MongoCollection<Document> collection){
        collection.deleteOne(Filters.eq("name", "name1000"));
        collection.deleteMany(Filters.gte("age", 20));
    }
}
