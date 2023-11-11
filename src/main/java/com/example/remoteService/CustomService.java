package com.example.remoteService;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class CustomService {
    private final Map<Integer, ConcurrentLinkedQueue<Double>> numberToValuesMap;
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
        for(int i=0;i<15;i++){
            numberToValuesMap.put(i+1,new ConcurrentLinkedQueue<>());
        }
    }

    public ConcurrentLinkedQueue<Double> getValues(Integer key) {
        if(numberToValuesMap.containsKey(key)){
            return numberToValuesMap.get(key);
        }
        return null;
    }

    public Set<Integer> getKeys(){
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

    public boolean v1AddValue(Integer key, Double value){
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
}
