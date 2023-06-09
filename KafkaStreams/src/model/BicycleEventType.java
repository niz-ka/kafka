package model;

public enum BicycleEventType {
    START(0), STOP(1);

    private final int value;

    BicycleEventType(int value) {
        this.value = value;
    }

    public static BicycleEventType fromInteger(Integer value) {
        switch (value) {
            case 0:
                return BicycleEventType.START;
            case 1:
                return BicycleEventType.STOP;
            default:
                throw new IllegalArgumentException("Invalid enum argument: " + value);
        }
    }

    public int getValue() {
        return value;
    }
}
