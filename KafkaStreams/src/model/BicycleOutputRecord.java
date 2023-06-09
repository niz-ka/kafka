package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BicycleOutputRecord {
    private String day;
    private Long stationId;
    private Long startCount;
    private Long stopCount;
    private Double temperatureAverage;

    public BicycleOutputRecord() {
    }

    public BicycleOutputRecord(String day, Long stationId, Long startCount, Long stopCount, Double temperatureAverage) {
        this.day = day;
        this.stationId = stationId;
        this.startCount = startCount;
        this.stopCount = stopCount;
        this.temperatureAverage = temperatureAverage;
    }

    @Override
    public String toString() {
        return "BicycleOutputRecord{" +
                "day=" + day +
                ", stationId=" + stationId +
                ", startCount=" + startCount +
                ", stopCount=" + stopCount +
                ", temperatureAverage=" + temperatureAverage +
                '}';
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Long getStartCount() {
        return startCount;
    }

    public void setStartCount(Long startCount) {
        this.startCount = startCount;
    }

    public Long getStopCount() {
        return stopCount;
    }

    public void setStopCount(Long stopCount) {
        this.stopCount = stopCount;
    }

    public Double getTemperatureAverage() {
        return temperatureAverage;
    }

    public void setTemperatureAverage(Double temperatureAverage) {
        this.temperatureAverage = temperatureAverage;
    }
}
