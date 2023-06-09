package model;

public class BicycleFinalRecord {
    private String day;
    private String station;
    private Long startCount;
    private Long stopCount;
    private Double temperatureAverage;

    @Override
    public String toString() {
        return "BicycleFinalRecord{" +
                "day='" + day + '\'' +
                ", station='" + station + '\'' +
                ", startCount=" + startCount +
                ", stopCount=" + stopCount +
                ", temperatureAverage=" + temperatureAverage +
                '}';
    }

    public BicycleFinalRecord() {
    }

    public BicycleFinalRecord(String day, String station, Long startCount, Long stopCount, Double temperatureAverage) {
        this.day = day;
        this.station = station;
        this.startCount = startCount;
        this.stopCount = stopCount;
        this.temperatureAverage = temperatureAverage;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
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
