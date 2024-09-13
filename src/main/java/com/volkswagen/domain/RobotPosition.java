package com.volkswagen.domain;


import com.volkswagen.utils.string.StringUtils;

import static com.volkswagen.domain.RobotOrientation.fromChar;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

public class RobotPosition {

    private int x;

    private int y;

    private RobotOrientation orientation;

    RobotPosition(int x, int y, RobotOrientation orientation) {

        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Robot position cannot be negative");
        }

        if (orientation == null) {
            throw new IllegalArgumentException("Robot orientation cannot be null");
        }
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    private static final String ROBOT_POSITION_RAW_PATTERN = "[0-9]{2}[NESW]";

    public static RobotPosition from(String rawValue) {
        if (rawValue == null) {
            throw new IllegalArgumentException("Raw robot position cannot be null");
        }

        String trimmed = StringUtils.removeBlanks(rawValue.trim());

        if (!machesPattern(trimmed)) {
            throw new IllegalArgumentException("Invalid raw robot position");
        }

        int x = parseInt(valueOf(trimmed.charAt(0)));
        int y = parseInt(valueOf(trimmed.charAt(1)));
        RobotOrientation orientation = fromChar(trimmed.charAt(2));

        return new RobotPosition(
            x,
            y,
            orientation
        );
    }

    private static boolean machesPattern(String rawValue) {
        return rawValue.matches(ROBOT_POSITION_RAW_PATTERN);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public RobotOrientation orientation() {
        return orientation;
    }

    public void x(int x) {
        this.x = x;
    }

    public void y(int y) {
        this.y = y;
    }

    public void orientation(RobotOrientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RobotPosition that = (RobotPosition) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        return orientation == that.orientation;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + orientation.hashCode();
        return result;
    }
}
