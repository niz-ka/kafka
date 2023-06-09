package serde;

import model.*;

public class CustomSerdes {

    public static JsonPOJOSerde<BicycleResultRecord> BicycleResultRecord() {
        return new JsonPOJOSerde<>(BicycleResultRecord.class);
    }

    public static JsonPOJOSerde<StationsListRecord> StationsListRecord() {
        return new JsonPOJOSerde<>(StationsListRecord.class);
    }

    public static JsonPOJOSerde<DayStationIdKey> DayStationIdKey() {
        return new JsonPOJOSerde<>(DayStationIdKey.class);
    }

    public static JsonPOJOSerde<BicycleAggregator> BicycleAggregator() {
        return new JsonPOJOSerde<>(BicycleAggregator.class);
    }

    public static JsonPOJOSerde<BicycleOutputRecord> BicycleOutputRecord() {
        return new JsonPOJOSerde<>(BicycleOutputRecord.class);
    }

    public static JsonPOJOSerde<AnomalyRecord> AnomalyRecord() {
        return new JsonPOJOSerde<>(AnomalyRecord.class);
    }
}
