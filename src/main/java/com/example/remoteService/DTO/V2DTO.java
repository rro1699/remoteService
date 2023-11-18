package com.example.remoteService.DTO;

public class V2DTO extends V1DTO{
    private String sensorName;

    public V2DTO(Double value, String sensorName) {
        super(value);
        this.sensorName = sensorName;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("V2DTO{");
        sb.append("sensorName='").append(sensorName).append('\'');
        sb.append("\n value=").append(this.getValue());
        sb.append('}');
        return sb.toString();
    }
}
