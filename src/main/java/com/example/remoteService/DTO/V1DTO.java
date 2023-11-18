package com.example.remoteService.DTO;

public class V1DTO {
    private Double value;

    public V1DTO() {
    }

    public V1DTO(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
