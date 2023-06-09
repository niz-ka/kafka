package schema;

// FIXME: Who needs Schema Registry when there are strings :D
public class AggregationSchema {
    private final static String SCHEMA_BEGIN = "{\"schema\":{\"type\":\"struct\",\"optional\":false,\"version\":1,\"fields\":[{\"field\":\"day\",\"type\":\"string\",\"optional\":true},{\"field\":\"station\",\"type\":\"string\",\"optional\":true},{\"field\":\"startCount\",\"type\":\"int64\",\"optional\":true},{\"field\":\"stopCount\",\"type\":\"int64\",\"optional\":true},{\"field\":\"temperatureAverage\",\"type\":\"double\",\"optional\":true}]}, \"payload\":";

    private final static String SCHEMA_END = "}";

    public static String construct(String payload) {
        return SCHEMA_BEGIN + payload + SCHEMA_END;
    }
}
