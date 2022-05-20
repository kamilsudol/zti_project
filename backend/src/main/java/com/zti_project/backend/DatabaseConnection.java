package com.zti_project.backend;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class DatabaseConnection {

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    DatabaseConnection() {
        this.mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://zti_project:project_zti@projekt.qof9o.mongodb.net/myFirstDatabase?retryWrites=true&w=majority"));
        this.database = mongoClient.getDatabase("projekt");
        this.collection = database.getCollection("projekt");
    }

    public String getTest(){
        FindIterable<Document> docs = this.collection.find();
        for(Document doc : docs) {
            System.out.println(doc.toJson());
        }
        /*
        try{
            for(Document doc : docs) {
                System.out.println(doc.toJson());
            }
        }catch (Exception e){
            int i = 1;
        }
        * */
        return "ok";
    }
    // TODO getAllBooks [type: book]
    // TODO getSpecificBook [type: book, id, tytul, autor]
    // TODO getReservations [type: reservation, pola: kto, od kiedy, do kiedy, co(id ksiazki), czy trwa nadal]
    // TODO getUser [type:user, login, haslo]
    // TODO getNotification[type: notification, user, kiedy]

    // TODO insertSpecificBook [type: book, id (get max idx), tytul, autor]
    // TODO insertReservation [type: reservation, pola: kto, od kiedy, do kiedy, co(id ksiazki)]
    // TODO insertUser [type:user, login, haslo]
    // TODO insertNotification[type: notification, user, kiedy]

    // TODO updateReservation [type: reservation, pola: kto, od kiedy, do kiedy, co(id ksiazki)]
    // TODO updateNotification[type: notification, user, kiedy]

    // TODO loginStatus

    /*TODO front
    panel logowania
    panel rejestracji?
    interfejs 3 zakladki (moje wypozyczenia, oczekujace i wypozycz) i dzwoneczek powiadomien - info o naliczaniu kar
    moje wypozyczenia - lista itemkow - przyciski oddaj
    wypozycz - przycisk wypozycz albo wejdz do kolejki
    oczekujace - w jakich kolejkach jestem
    * */
}
