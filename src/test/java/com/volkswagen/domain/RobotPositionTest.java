package com.volkswagen.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.volkswagen.domain.RobotOrientation.NORTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RobotPositionTest {

    public static final int DEFAULT_ROBOT_POSITION_X = 1;

    public static final int DEFAULT_ROBOT_POSITION_Y = 2;

    public static final RobotOrientation DEFAULT_ROBOT_POSITION_ORIENTATION = NORTH;

    public static final String DEFAULT_RAW_ROBOT_POSITION = DEFAULT_ROBOT_POSITION_X + " " + DEFAULT_ROBOT_POSITION_Y + DEFAULT_ROBOT_POSITION_ORIENTATION.getRawValue();

    private RobotPosition robotPosition;

    public static RobotPosition createDefault() {
        return RobotPosition.from(DEFAULT_RAW_ROBOT_POSITION);
    }

    @BeforeEach
    void setUp() {
        robotPosition = createDefault();
    }

    @Test
    void creates_a_robot_position() {
        assertThat(robotPosition.y()).isEqualTo(DEFAULT_ROBOT_POSITION_Y);
        assertThat(robotPosition.x()).isEqualTo(DEFAULT_ROBOT_POSITION_X);
        assertThat(robotPosition.orientation()).isEqualTo(DEFAULT_ROBOT_POSITION_ORIENTATION);
    }

    @Test
    void creates_a_robot_position_with_invalid_raw_value() {
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> RobotPosition.from("N2N"));

        assertThat(illegalArgumentException.getMessage()).isEqualTo("Invalid raw robot position");
    }

    @Test
    void creates_a_robot_position_with_null_raw_value() {
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> RobotPosition.from(null));

        assertThat(illegalArgumentException.getMessage()).isEqualTo("Raw robot position cannot be null");
    }

    @Test
    void creates_a_robot_position_with_x_negative() {
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> new RobotPosition(-1, 0, NORTH));

        assertThat(illegalArgumentException.getMessage()).isEqualTo("Robot position cannot be negative");
    }

    @Test
    void creates_a_robot_position_with_y_negative() {
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> new RobotPosition(0, -1, NORTH));

        assertThat(illegalArgumentException.getMessage()).isEqualTo("Robot position cannot be negative");
    }

    @Test
    void creates_a_robot_position_with_null_orientation() {
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> new RobotPosition(0, 0, null));

        assertThat(illegalArgumentException.getMessage()).isEqualTo("Robot orientation cannot be null");
    }

    @Test
    void test_equals() {
        RobotPosition robotPosition1 = new RobotPosition(0, 0, NORTH);
        RobotPosition robotPosition2 = new RobotPosition(0, 0, NORTH);

        assertThat(robotPosition1).isEqualTo(robotPosition2);
    }

    @Test
    void testHashCode() {
        RobotPosition robotPosition1 = new RobotPosition(0, 0, NORTH);
        RobotPosition robotPosition2 = new RobotPosition(0, 0, NORTH);

        assertThat(robotPosition1.hashCode()).isEqualTo(robotPosition2.hashCode());
    }
}
