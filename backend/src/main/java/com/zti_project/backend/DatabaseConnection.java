package com.zti_project.backend;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.json.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseConnection {

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private MongoCollection<Document> collection;

    DatabaseConnection() {
        this.mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://zti_project:project_zti@projekt.qof9o.mongodb.net/myFirstDatabase?retryWrites=true&w=majority"));
        this.database = mongoClient.getDatabase("projekt");
        this.collection = null;
    }

    public void delete(String collection, Document query){
        this.collection = database.getCollection(collection);
        this.collection.deleteOne(query);
    }

    public void insert_one(String collection, Document query){
        this.collection = database.getCollection(collection);
        this.collection.insertOne(query);
    }

    public List<Document> find_one(String collection, Document query){
        this.collection = database.getCollection(collection);
        List<Document> results = new ArrayList<>();
        this.collection.find(query).into(results);
        return results;
    }

    public JsonArray find_all(String collection){
        this.collection = database.getCollection(collection);
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        FindIterable<Document> docs = this.collection.find();
        for(Document doc : docs) {
            jsonArray.add(doc.toJson());
        }
        return jsonArray.build();
    }

    public void update(String collection, Document query, Bson updates){
        this.collection = database.getCollection(collection);
        UpdateOptions options = new UpdateOptions().upsert(true);
        this.collection.updateOne(query, updates, options);
    }
}