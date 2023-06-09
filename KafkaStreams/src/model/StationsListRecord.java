package model;

public class StationsListRecord {
    //ID,Station Name,Total Docks,Docks in Service,Status,Latitude,Longitude,Location
    private Long id;
    private String stationName;
    private Long totalDocks;
    private Long docksInService;
    private String status;
    private Double latitude;
    private Double longitude;
    private String location;

    public StationsListRecord() {
    }

    public StationsListRecord(
            Long id,
            String stationName,
            Long totalDocks,
            Long docksInService,
            String status,
            Double latitude,
            Double longitude,
            String location) {
        this.id = id;
        this.stationName = stationName;
        this.totalDocks = totalDocks;
        this.docksInService = docksInService;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Long getTotalDocks() {
        return totalDocks;
    }

    public void setTotalDocks(Long totalDocks) {
        this.totalDocks = totalDocks;
    }

    public Long getDocksInService() {
        return docksInService;
    }

    public void setDocksInService(Long docksInService) {
        this.docksInService = docksInService;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "StationsListRecord{" +
                "id=" + id +
                ", stationName='" + stationName + '\'' +
                ", totalDocks=" + totalDocks +
                ", docksInService=" + docksInService +
                ", status='" + status + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", location=" + location +
                '}';
    }

    public static StationsListRecord fromString(String record) {
        // FIXME: Unreliable split
        String[] values = record.split(",", 8);
        if (values.length != 8) {
            throw new RuntimeException("Unexpected commas in: " + record);
        }

        Long id = Long.parseLong(values[0]);
        String stationName = values[1];
        Long totalDocks = Long.parseLong(values[2]);
        Long docksInService = Long.parseLong(values[3]);
        String status = values[4];
        Double latitude = Double.parseDouble(values[5]);
        Double longitude = Double.parseDouble(values[6]);
        String location = values[7];

        return new StationsListRecord(
                id,
                stationName,
                totalDocks,
                docksInService,
                status,
                latitude,
                longitude,
                location
        );
    }
}
