package com.zti_project.backend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.zti_project.backend.DatabaseConnection;

@RestController
public class Endpoints {

    @GetMapping("/test")
    public String getTest() {
        System.out.println("kurwa dzialam");
        return "Test";
    }

    @GetMapping("/getAllBooks")
    public String getAllBooks() {
        DatabaseConnection connection = new DatabaseConnection();
        connection.getTest();
        return "lonczonko";
    }

//    @GetMapping("/test")
//    public String getTest() {
//        return "Test";
//    }
//
//    @GetMapping("/test")
//    public String getTest() {
//        return "Test";
//    }
}
