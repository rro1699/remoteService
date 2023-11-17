package com.example.remoteService;

import com.example.remoteService.DTO.V1DTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentLinkedQueue;

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
    @RequestMapping(value = "/v1/{key}",method = RequestMethod.POST)
    public ResponseEntity<?> executeV1(@PathVariable(name = "key") Integer key,@RequestBody V1DTO body){
        if(customService.v1AddValue(key, body.getValue())){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/get/{key}",method = RequestMethod.GET)
    public ResponseEntity<?> getV1(@PathVariable(name = "key") Integer key){
        ConcurrentLinkedQueue<Double> values = customService.getV1(key);
        if (values == null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.ok().body(values);
        }
    }
}
