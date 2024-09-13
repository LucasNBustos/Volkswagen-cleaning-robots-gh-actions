package com.volkswagen.domain;

public enum RobotOrientation {
    NORTH("N"), EAST("E"), SOUTH("S"), WEST("W");

    private final String rawValue;

    RobotOrientation(String rawValue) {
        this.rawValue = rawValue;
    }

    public String getRawValue() {
        return rawValue;
    }

    public static RobotOrientation fromChar(Character charValue) {
        if (charValue == null) throw new IllegalArgumentException("Value cannot be null");

        for (RobotOrientation orientation : RobotOrientation.values()) {

            if (orientation.rawValue.equals(charValue.toString())) {
                return orientation;
            }
        }
        throw new IllegalArgumentException("Invalid orientation string: " + charValue);
    }
}
