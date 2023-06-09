package model;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class BicycleResultRecord {
    private Long tripId;
    private BicycleEventType startStop;
    private Date eventTime;
    private Long stationId;
    private Double tripDuration;
    private String userType;
    private String gender;
    private Long week;
    private Double temperature;
    private String events;

    public BicycleResultRecord() {}

    public BicycleResultRecord(
            Long tripId,
            BicycleEventType startStop,
            Date eventTime,
            Long stationId,
            Double tripDuration,
            String userType,
            String gender,
            Long week,
            Double temperature,
            String events) {
        this.tripId = tripId;
        this.startStop = startStop;
        this.eventTime = eventTime;
        this.stationId = stationId;
        this.tripDuration = tripDuration;
        this.userType = userType;
        this.gender = gender;
        this.week = week;
        this.temperature = temperature;
        this.events = events;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public BicycleEventType getStartStop() {
        return startStop;
    }

    public void setStartStop(BicycleEventType startStop) {
        this.startStop = startStop;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Double getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(Double tripDuration) {
        this.tripDuration = tripDuration;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getWeek() {
        return week;
    }

    public void setWeek(Long week) {
        this.week = week;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "BicycleResultRecord{" +
                "tripId=" + tripId +
                ", startStop=" + startStop +
                ", eventTime=" + new SimpleDateFormat("yyyy-MM-dd").format(eventTime) +
                ", stationId=" + stationId +
                ", tripDuration=" + tripDuration +
                ", userType='" + userType + '\'' +
                ", gender='" + gender + '\'' +
                ", week=" + week +
                ", temperature=" + temperature +
                ", events='" + events + '\'' +
                '}';
    }

    public static BicycleResultRecord fromString(String record) {
        String[] values = record.split(",");
        if(values.length != 10) {
            throw new RuntimeException("Unexpected commas in: " + record);
        }

        Long tripId = Long.parseLong(values[0]);
        BicycleEventType startStop = BicycleEventType.fromInteger(Integer.parseInt(values[1]));
        Date eventTime = Date.from(Instant.from(DateTimeFormatter.ISO_INSTANT.parse(values[2])));
        Long stationId = Long.parseLong(values[3]);
        Double tripDuration = Double.parseDouble(values[4]);
        String userType = values[5];
        String gender = values[6];
        Long week = Long.parseLong(values[7]);
        Double temperature = Double.parseDouble(values[8]);
        String events = values[9];

        return new BicycleResultRecord(
                tripId,
                startStop,
                eventTime,
                stationId,
                tripDuration,
                userType,
                gender,
                week,
                temperature,
                events
        );
    }
}
