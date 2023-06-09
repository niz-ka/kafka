package model;

public class AnomalyRecord {
    private String windowStart;
    private String windowEnd;
    private Long numberN;

    @Override
    public String toString() {
        return "AnomalyRecord{" +
                "windowStart='" + windowStart + '\'' +
                ", windowEnd='" + windowEnd + '\'' +
                ", numberN=" + numberN +
                '}';
    }

    public AnomalyRecord(String windowStart, String windowEnd, Long numberN) {
        this.windowStart = windowStart;
        this.windowEnd = windowEnd;
        this.numberN = numberN;
    }

    public AnomalyRecord() {
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

    public Long getNumberN() {
        return numberN;
    }

    public void setNumberN(Long numberN) {
        this.numberN = numberN;
    }
}
