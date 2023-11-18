package com.example.remoteService;

import com.example.remoteService.DTO.V2DTO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class CustomService {
    private final Map<Integer, ConcurrentLinkedQueue<Double>> numberToValuesMap;

    private final Map<Integer, ConcurrentLinkedQueue<V2DTO>> numberToTypeMap;
    private Integer maxValues;
    private final static int MAX_VALUES = 50;

    private static CustomService instance;

    public static CustomService getInstance(){
        CustomService localInstance = instance;
        if(localInstance == null){
            localInstance = instance = new CustomService();
        }
        return localInstance;
    }
    private CustomService() {
        maxValues = 10;
        numberToValuesMap = new HashMap<>();
        numberToTypeMap = new HashMap<>();
        for(int i=0;i<15;i++){
            numberToValuesMap.put(i+1,new ConcurrentLinkedQueue<>());
            numberToTypeMap.put(i+1,new ConcurrentLinkedQueue<>());
        }
    }

    public ConcurrentLinkedQueue<Double> getValues(Integer key) {
        if(numberToValuesMap.containsKey(key)){
            return numberToValuesMap.get(key);
        }
        return null;
    }
    public ConcurrentLinkedQueue<Double> getValues2(Integer key) {
        if(numberToTypeMap.containsKey(key)){
            ConcurrentLinkedQueue<V2DTO> v2DTOS = numberToTypeMap.get(key);
            ConcurrentLinkedQueue<Double> result = new ConcurrentLinkedQueue<>();
            v2DTOS.forEach(e->result.add(e.getValue()));
            return result;
        }
        return null;
    }

    public Set<Integer> getKeys(){
        numberToValuesMap.keySet().forEach(System.out::println);
        return numberToValuesMap.keySet();
    }

    public void setMaxValues(Integer maxValues) {
        if(maxValues<MAX_VALUES) {
            this.maxValues = maxValues;
        }else{
            this.maxValues = MAX_VALUES;
        }
    }

    public Integer getMaxValues() {
        return maxValues;
    }

    public synchronized boolean v1AddValue(Integer key, Double value){
        if(!numberToValuesMap.containsKey(key)){
            return false;
        }else{
            ConcurrentLinkedQueue<Double> values = numberToValuesMap.get(key);
            if(values.size()>=maxValues){
                int differentSize = values.size() - maxValues;
                for(int x = 0; x <= differentSize; x++){
                    values.poll();
                }
            }
            if(values.size()<maxValues){
                values.add(value);
            }else{
                values.poll();
                values.add(value);
            }
        }
        return true;
    }
    public synchronized boolean v2AddValue(Integer key, Double value, String type){
        if(!numberToValuesMap.containsKey(key)){
            return false;
        }else{
            ConcurrentLinkedQueue<V2DTO> dtos = numberToTypeMap.get(key);
            if(dtos.size()>=maxValues){
                int differentSize = dtos.size() - maxValues;
                for(int x = 0; x <= differentSize; x++){
                    dtos.poll();
                }
            }
            if(dtos.size()<maxValues){
                dtos.add(new V2DTO(value, type));
            }else{
                dtos.poll();
                dtos.add(new V2DTO(value, type));
            }
        }
        return true;
    }
    public synchronized ConcurrentLinkedQueue<Double> getV1(Integer key){
        return numberToValuesMap.getOrDefault(key, null);
    }

    public synchronized String getV2(Integer key){
        ConcurrentLinkedQueue<V2DTO> dtos = numberToTypeMap.get(key);
        if(dtos == null || dtos.isEmpty()){
            return null;
        }else{
            StringBuilder sb = new StringBuilder();
            dtos.forEach(e-> sb.append("\n").append("value: ").append(e.getValue()).append("; type: ").append(e.getSensorName()).append("\n"));
            return sb.toString();
        }
    }
}
