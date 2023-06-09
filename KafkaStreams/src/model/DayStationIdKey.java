package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DayStationIdKey {
    private String day;
    private Long stationId;

    public DayStationIdKey() {
    }

    public DayStationIdKey(String day, Long stationId) {
        this.day = day;
        this.stationId = stationId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "DayStationIdKey{" +
                "day=" + day +
                ", stationId=" + stationId +
                '}';
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }
}
