package model;

public class BicycleAggregator {
    private Long startCount;
    private Long stopCount;
    private Double temperatureSum;

    @Override
    public String toString() {
        return "BicycleAggregator{" +
                "startCount=" + startCount +
                ", stopCount=" + stopCount +
                ", temperatureSum=" + temperatureSum +
                '}';
    }

    public BicycleAggregator() {
    }

    public BicycleAggregator(Long startCount, Long stopCount, Double temperatureSum) {
        this.startCount = startCount;
        this.stopCount = stopCount;
        this.temperatureSum = temperatureSum;
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

    public Double getTemperatureSum() {
        return temperatureSum;
    }

    public void setTemperatureSum(Double temperatureSum) {
        this.temperatureSum = temperatureSum;
    }
}
