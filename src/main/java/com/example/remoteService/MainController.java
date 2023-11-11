package com.example.remoteService;

import com.example.remoteService.DTO.V1DTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private final CustomService customService;

    public MainController() {
        this.customService = CustomService.getInstance();
    }

    @RequestMapping("/test")
    public ResponseEntity<?> execute(){
        return ResponseEntity.ok().body("Success");
    }
    @RequestMapping("/setup/maxValues/{size}")
    public ResponseEntity<?> setupMaxValues(@PathVariable(name = "size") Integer size){
        customService.setMaxValues(size);
        return ResponseEntity.ok().build();
    }
    @RequestMapping("/v1/{key}")
    public ResponseEntity<?> executeV1(@PathVariable(name = "key") Integer key,@RequestBody V1DTO body){
        if(customService.v1AddValue(key, body.getValue())){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
}
