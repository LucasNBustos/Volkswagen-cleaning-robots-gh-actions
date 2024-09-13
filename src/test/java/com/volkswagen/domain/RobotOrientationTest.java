package com.volkswagen.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RobotOrientationTest {
    @Test
    void creates_a_robot_orientation_from_char() {
        RobotOrientation robotOrientation = RobotOrientation.fromChar('N');
        assertEquals(RobotOrientation.NORTH, robotOrientation);
    }

    @Test
    void creates_a_robot_orientation_from_char_with_invalid_char() {
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> RobotOrientation.fromChar('X'));

        assertEquals("Invalid orientation string: X", illegalArgumentException.getMessage());
    }

    @Test
    void creates_a_robot_orientation_from_char_with_null_char() {
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> RobotOrientation.fromChar(null));

        assertEquals("Value cannot be null", illegalArgumentException.getMessage());
    }

}
