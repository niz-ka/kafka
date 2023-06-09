package model;

public class FinalAnomalyRecord {
    private String windowStart;
    private String windowEnd;
    private String stationName;
    private Long stopRedundancy;
    private Long startRedundancy;
    private Long docksInService;
    private Double ratioN;

    public FinalAnomalyRecord() {
    }

    public FinalAnomalyRecord(String windowStart, String windowEnd, String stationName, Long stopRedundancy, Long startRedundancy, Long docksInService, Double ratioN) {
        this.windowStart = windowStart;
        this.windowEnd = windowEnd;
        this.stationName = stationName;
        this.stopRedundancy = stopRedundancy;
        this.startRedundancy = startRedundancy;
        this.docksInService = docksInService;
        this.ratioN = ratioN;
    }

    public String getWindowStart() {
        return windowStart;
    }

    public void setWindowStart(String windowStart) {
        this.windowStart = windowStart;
    }

    public String getWindowEnd() {
        return windowEnd;
    }

    public void setWindowEnd(String windowEnd) {
        this.windowEnd = windowEnd;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Long getStopRedundancy() {
        return stopRedundancy;
    }

    public void setStopRedundancy(Long stopRedundancy) {
        this.stopRedundancy = stopRedundancy;
    }

    public Long getStartRedundancy() {
        return startRedundancy;
    }

    public void setStartRedundancy(Long startRedundancy) {
        this.startRedundancy = startRedundancy;
    }

    public Long getDocksInService() {
        return docksInService;
    }

    public void setDocksInService(Long docksInService) {
        this.docksInService = docksInService;
    }

    public Double getRatioN() {
        return ratioN;
    }

    public void setRatioN(Double ratioN) {
        this.ratioN = ratioN;
    }

    @Override
    public String toString() {
        return "FinalAnomalyRecord{" +
                "windowStart='" + windowStart + '\'' +
                ", windowEnd='" + windowEnd + '\'' +
                ", stationName='" + stationName + '\'' +
                ", stopRedundancy=" + stopRedundancy +
                ", startRedundancy=" + startRedundancy +
                ", docksInService=" + docksInService +
                ", ratioN=" + ratioN +
                '}';
    }
}
