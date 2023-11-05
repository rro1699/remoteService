package com.example.remoteService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @RequestMapping("/test")
    public ResponseEntity<?> execute(){
        return ResponseEntity.ok().body("Success");
    }
}
