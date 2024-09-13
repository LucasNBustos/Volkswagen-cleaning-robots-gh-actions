package com.volkswagen.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorkplaceTest {

    public static final int DEFAULT_WORKPLACE_X = 5;

    public static final int DEFAULT_WORKPLACE_Y = 6;

    private static final String DEFAULT_RAW_WORKPLACE = DEFAULT_WORKPLACE_X + " " + DEFAULT_WORKPLACE_Y;

    private Workplace workplace;

    public static Workplace createDefault() {
        return Workplace.from(DEFAULT_RAW_WORKPLACE);
    }

    @BeforeEach
    void setUp() {
        workplace = createDefault();
    }

    @Test
    void creates_a_workplace() {
        assertEquals(5, workplace.x());
        assertEquals(6, workplace.y());
    }

    @Test
    void creates_a_workplace_with_null_raw_value() {
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> Workplace.from(null));

        assertEquals("Raw workplace input cannot be null", illegalArgumentException.getMessage());
    }

    @Test
    void creates_a_workplace_with_invalid_raw_value() {
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> Workplace.from("5N"));

        assertEquals("Invalid raw workplace input", illegalArgumentException.getMessage());
    }

    @Test
    void creates_a_workplace_with_negative_x() {
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> Workplace.from("-5 6"));

        assertEquals("Invalid raw workplace input", illegalArgumentException.getMessage());
    }

    @Test
    void creates_a_workplace_with_negative_y() {
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> Workplace.from("5 -6"));

        assertEquals("Invalid raw workplace input", illegalArgumentException.getMessage());
    }

    @Test
    void creates_a_workplace_with_negative_x_and_y() {
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> Workplace.from("-5 -6"));

        assertEquals("Invalid raw workplace input", illegalArgumentException.getMessage());
    }

    @Test
    void checks_if_a_position_is_outside_the_workplace() {
        assertFalse(workplace.isOutside(0, 0));
        assertFalse(workplace.isOutside(5, 6));
        assertTrue(workplace.isOutside(6, 6));
        assertTrue(workplace.isOutside(5, 7));
        assertTrue(workplace.isOutside(6, 7));
    }

    @Test
    void checks_if_a_position_is_outside_using_a_robotPosition() {
        assertFalse(workplace.isOutside(RobotPosition.from("0 0N")));
        assertFalse(workplace.isOutside(RobotPosition.from("5 6N")));
        assertTrue(workplace.isOutside(RobotPosition.from("6 6N")));
        assertTrue(workplace.isOutside(RobotPosition.from("5 7N")));
        assertTrue(workplace.isOutside(RobotPosition.from("6 7N")));
    }

    @Test
    void checks_if_a_position_has_an_obstacle() {
        assertFalse(workplace.hasObstacleIn(1, 1));
        workplace.addFinishedRobot(Robot.from(workplace, RobotPosition.from("1 1N")));
        assertTrue(workplace.hasObstacleIn(1, 1));
    }

    @Test
    void workplace_finished_robots_are_empty() {
        assertTrue(workplace.finishedRobots().isEmpty());
    }

    @Test
    void workplace_finished_robots_are_not_empty() {
        workplace.addFinishedRobot(Robot.from(workplace, RobotPosition.from("1 1N")));
        assertFalse(workplace.finishedRobots().isEmpty());
    }

    @Test
    void workplace_finished_robots_are_not_empty_and_has_one_robot() {
        workplace.addFinishedRobot(Robot.from(workplace, RobotPosition.from("1 1N")));
        assertEquals(1, workplace.finishedRobots().size());
    }

    @Test
    void workplace_has_a_finished_robot() {
        workplace.addFinishedRobot(Robot.from(workplace, RobotPosition.from("1 1N")));
        assertTrue(workplace.hasObstacleIn(1, 1));
    }

    @Test
    void workplace_x_cannot_be_negative() {
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> new Workplace(-1, 6));

        assertEquals("Workplace coordinate cannot be negative", illegalArgumentException.getMessage());
    }

    @Test
    void workplace_y_cannot_be_negative() {
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> new Workplace(5, -6));

        assertEquals("Workplace coordinate cannot be negative", illegalArgumentException.getMessage());
    }

}
