package com.zti_project.backend;
import org.springframework.web.bind.annotation.*;
import javax.json.*;
import java.io.StringReader;

@RestController
public class Endpoints {

    DataParser data_parser = new DataParser();

    @GetMapping("/getAllBooks")
    public JsonObject getAllBooks() {
        JsonArray result = data_parser.getAllBooks();
        return Json.createObjectBuilder().add("result", result).build();
    }

    @RequestMapping(
            value = "/user",
            method = RequestMethod.POST)
    public JsonObject user(@RequestBody String user_data)
            throws Exception {
        JsonReader jsonReader = Json.createReader(new StringReader(user_data));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();
        return data_parser.user(object);
    }

    @RequestMapping(
            value = "/notification",
            method = RequestMethod.POST)
    public JsonObject notification(@RequestBody String user_id)
            throws Exception {
        JsonReader jsonReader = Json.createReader(new StringReader(user_id));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();
        JsonArray result = data_parser.notification(object);
        return Json.createObjectBuilder().add("result", result).build();
    }

    @RequestMapping(
            value = "/borrow",
            method = RequestMethod.POST)
    public JsonObject borrow(@RequestBody String data_string)
            throws Exception {
        JsonReader jsonReader = Json.createReader(new StringReader(data_string));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();
        Boolean result = data_parser.borrow(object);
        return Json.createObjectBuilder().add("result", result).build();
    }

    @RequestMapping(
            value = "/reserve",
            method = RequestMethod.POST)
    public JsonObject reserve(@RequestBody String data_string)
            throws Exception {
        JsonReader jsonReader = Json.createReader(new StringReader(data_string));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();
        Boolean result = data_parser.reserve(object);
        return Json.createObjectBuilder().add("result", result).build();
    }

    @RequestMapping(
            value = "/yourPositions",
            method = RequestMethod.POST)
    public JsonObject yourPositions(@RequestBody String user_id)
            throws Exception {
        JsonReader jsonReader = Json.createReader(new StringReader(user_id));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();
        JsonArray result = data_parser.yourPositions(object);
        return Json.createObjectBuilder().add("result", result).build();
    }

    @RequestMapping(
            value = "/yourReservations",
            method = RequestMethod.POST)
    public JsonObject yourReservations(@RequestBody String user_id)
            throws Exception {
        JsonReader jsonReader = Json.createReader(new StringReader(user_id));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();
        JsonArray result = data_parser.yourReservations(object);
        return Json.createObjectBuilder().add("result", result).build();
    }

    @RequestMapping(
            value = "/returnBook",
            method = RequestMethod.POST)
    public JsonObject returnBook(@RequestBody String data_string)
            throws Exception {
        JsonReader jsonReader = Json.createReader(new StringReader(data_string));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();
        Boolean result = data_parser.returnBook(object);
        return Json.createObjectBuilder().add("result", result).build();
    }

    @RequestMapping(
            value = "/cancelBook",
            method = RequestMethod.POST)
    public JsonObject cancelBook(@RequestBody String data_string)
            throws Exception {
        JsonReader jsonReader = Json.createReader(new StringReader(data_string));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();
        Boolean result = data_parser.cancelBook(object);
        return Json.createObjectBuilder().add("result", result).build();
    }

}
