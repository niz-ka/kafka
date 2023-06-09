package schema;

public class AnomalySchema {
    private final static String SCHEMA_BEGIN = "{\"schema\":{\"type\":\"struct\",\"optional\":false,\"version\":1,\"fields\":[{\"field\":\"windowStart\",\"type\":\"string\",\"optional\":true},{\"field\":\"windowEnd\",\"type\":\"string\",\"optional\":true},{\"field\":\"stationName\",\"type\":\"string\",\"optional\":true},{\"field\":\"stopRedundancy\",\"type\":\"int64\",\"optional\":true},{\"field\":\"startRedundancy\",\"type\":\"int64\",\"optional\":true},{\"field\":\"docksInService\",\"type\":\"int64\",\"optional\":true},{\"field\":\"ratioN\",\"type\":\"double\",\"optional\":true}]}, \"payload\":";

    private final static String SCHEMA_END = "}";

    public static String construct(String payload) {
        return SCHEMA_BEGIN + payload + SCHEMA_END;
    }
}
