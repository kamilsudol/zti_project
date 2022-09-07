package com.zti_project.backend;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

public class DataParser {
    private DatabaseConnection db = new DatabaseConnection();

    public JsonArray getAllBooks(){
        return db.find_all("book");
    }

    public JsonObject user(JsonObject user_data) {
        Document query = new Document("login", user_data.get("user").toString().replace("\"", ""));
        var result = db.find_one("user", query);
        if (user_data.get("type").toString().replace("\"", "").equals("login")) {
            if (result.size() != 0 && result.get(0).get("password").equals(user_data.get("password").toString().replace("\"", ""))){
                return Json.createObjectBuilder().add("result", true).add("id", result.get(0).get("_id").toString()).build();
            }else{
                return Json.createObjectBuilder().add("result", false).build();
            }
        } else {
            if (result.size() == 0){
                query.append("password", user_data.get("password").toString().replace("\"", ""));
                query.append("email", user_data.get("email").toString().replace("\"", ""));
                db.insert_one("user", query);
                Document new_query = new Document("login", user_data.get("user").toString().replace("\"", ""));
                var new_result = db.find_one("user", new_query);
                return Json.createObjectBuilder().add("result", true).add("id", new_result.get(0).get("_id").toString()).build();
            }else {
                return Json.createObjectBuilder().add("result", false).build();
            }
        }
    }

    public JsonArray notification(JsonObject user_id){
        Document query = new Document("user_id", user_id.get("id").toString().replace("\"", ""));
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        var result = db.find_one("notification", query);
        for(Document doc : result) {
            jsonArray.add(doc.toJson());
        }
        return jsonArray.build();
    }

    public Boolean borrow(JsonObject data_dict) {
        Document borrowed_query = new Document("user_id", data_dict.get("user_id").toString().replace("\"", ""));
        borrowed_query.append("book_id", data_dict.get("book_id").toString().replace("\"", ""));
        db.insert_one("borrowed", borrowed_query);

        Document book_query = new Document("_id", new ObjectId(data_dict.get("book_id").toString().replace("\"", "")));
        var book = db.find_one("book", book_query);
        String message = "Wypożyczyłeś " + book.get(0).get("title").toString().replace("\"", "");

        Document notification_query = new Document("user_id", data_dict.get("user_id").toString().replace("\"", ""));
        notification_query.append("message", message);
        db.insert_one("notification", notification_query);

        Document update_query = new Document("_id", new ObjectId(data_dict.get("book_id").toString().replace("\"", "")));
        Bson updates = Updates.combine(Updates.set("availability", false), Updates.set("owner", data_dict.get("user_id").toString().replace("\"", "")));
        db.update("book", update_query, updates);

        return true;
    }

    public Boolean reserve(JsonObject data_dict) {

        Document book_query = new Document("_id", new ObjectId(data_dict.get("book_id").toString().replace("\"", "")));
        var book = db.find_one("book", book_query);

        Document queue_query = new Document("book_id", data_dict.get("book_id").toString().replace("\"", ""));
        var queue = db.find_one("reservation", queue_query).size();
        String message = "Rezerwujesz " + book.get(0).get("title").toString().replace("\"", "") + ", jesteś " + (queue + 1) + " w kolejce.";

        Document reserve_query = new Document("user_id", data_dict.get("user_id").toString().replace("\"", ""));
        reserve_query.append("book_id", data_dict.get("book_id").toString().replace("\"", ""));
        reserve_query.append("queue", queue + 1);
        db.insert_one("reservation", reserve_query);

        Document notification_query = new Document("user_id", data_dict.get("user_id").toString().replace("\"", ""));
        notification_query.append("message", message);
        db.insert_one("notification", notification_query);

        return true;
    }

    public JsonArray yourPositions(JsonObject data_dict){
        Document query = new Document("owner", data_dict.get("user_id").toString().replace("\"", ""));
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        var result = db.find_one("book", query);
        for(Document doc : result) {
            jsonArray.add(doc.toJson());
        }
        return jsonArray.build();
    }

    public JsonArray yourReservations(JsonObject data_dict){
        Document query = new Document("user_id", data_dict.get("user_id").toString().replace("\"", ""));
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        var result = db.find_one("reservation", query);
        for(Document doc : result) {
            Document tmp_query = new Document("_id", new ObjectId(doc.get("book_id").toString().replace("\"", "")));
            var res = db.find_one("book", tmp_query);
            for(Document d : res) {
                System.out.println(d.toJson());
                jsonArray.add(d.toJson());
            }
        }
        return jsonArray.build();
    }

    public Boolean returnBook(JsonObject data_dict) {
        Document book_query = new Document("_id", new ObjectId(data_dict.get("book_id").toString().replace("\"", "")));
        var book = db.find_one("book", book_query);
        String message = "Oddałeś " + book.get(0).get("title").toString().replace("\"", "");

        Document notification_query = new Document("user_id", data_dict.get("user_id").toString().replace("\"", ""));
        notification_query.append("message", message);
        db.insert_one("notification", notification_query);

        Document update_query = new Document("_id", new ObjectId(data_dict.get("book_id").toString().replace("\"", "")));
        Document reservation_query = new Document("book_id", data_dict.get("book_id").toString().replace("\"", ""));
        var reservation_lit = db.find_one("reservation", reservation_query);
        if (reservation_lit.size() == 0){
            Bson updates = Updates.combine(Updates.set("availability", true), Updates.set("owner", ""));
            db.update("book", update_query, updates);
        } else {
            this.putFirstPersonOnBorrow(data_dict.get("book_id").toString().replace("\"", ""));
        }


        return true;
    }

    public Boolean cancelBook(JsonObject data_dict) {
        Document book_query = new Document("_id", new ObjectId(data_dict.get("book_id").toString().replace("\"", "")));
        var book = db.find_one("book", book_query);
        String message = "Anulowałeś rezerwacje " + book.get(0).get("title").toString().replace("\"", "");

        Document notification_query = new Document("user_id", data_dict.get("user_id").toString().replace("\"", ""));
        notification_query.append("message", message);
        db.insert_one("notification", notification_query);

        Document reservation_query = new Document("user_id", data_dict.get("user_id").toString().replace("\"", ""));
        reservation_query.append("book_id", data_dict.get("book_id").toString().replace("\"", ""));
        var queue_info = db.find_one("reservation", reservation_query); //info o numerze kolejki
        db.delete("reservation", reservation_query);

        this.repairQueue(queue_info.get(0).get("queue").toString(), data_dict.get("book_id").toString().replace("\"", ""));
        return true;
    }

    public void putFirstPersonOnBorrow(String book_id){
        Document reservation_query = new Document("queue", 1);
        reservation_query.append("book_id", book_id);
        var user = db.find_one("reservation", reservation_query);
        String user_id = user.get(0).get("user_id").toString();

        Document book_query = new Document("_id", new ObjectId(book_id));
        Bson book_update_query = Updates.combine(Updates.set("availability", true), Updates.set("owner", user_id));
        db.update("book", book_query, book_update_query);

        var book_info = db.find_one("book", book_query);
        String message = "Właśnie wypożyczyłeś " + book_info.get(0).get("title").toString();

        Document notification_query = new Document("user_id", user_id);
        notification_query.append("message", message);
        db.insert_one("notification", notification_query);
        this.repairQueue("1", book_id);
    }

    public void repairQueue(String queue_number, String book_id) {
        int queue_number_int = Integer.parseInt(queue_number);
        Document delete_query = new Document("queue", queue_number).append("book_id", book_id);
        db.delete("reservation", delete_query);

        var next_queue = queue_number_int;
        while (true){
            next_queue = next_queue + 1;
            Document tmp_query = new Document("queue", next_queue);
            var tmp_res = db.find_one("reservation", tmp_query);
            if (tmp_res.size() == 0) {
                break;
            }
            var prev_queue = next_queue - 1;
            Bson update_query = Updates.combine(Updates.set("queue", prev_queue));
            db.update("reservation", tmp_query, update_query);

            Document book_query = new Document("_id", new ObjectId(book_id));
            var book_info = db.find_one("book", book_query);

            String message = "W rezerwacji " + book_info.get(0).get("title") + " jesteś " + prev_queue + " w kolejce.";
            Document notification_query = new Document("user_id", tmp_res.get(0).get("user_id"));
            notification_query.append("message", message);
            db.insert_one("notification", notification_query);
        }
    }
}
