package com.volkswagen.application.port.in;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RobotCommandTest {
    @Test
    void createLCommand() {
        RobotCommand robotCommand = RobotCommand.fromChar('L');
        assertThat(robotCommand).isEqualTo(RobotCommand.L);
    }

    @Test
    void createRCommand() {
        RobotCommand robotCommand = RobotCommand.fromChar('R');
        assertThat(robotCommand).isEqualTo(RobotCommand.R);
    }

    @Test
    void createMCommand() {
        RobotCommand robotCommand = RobotCommand.fromChar('M');
        assertThat(robotCommand).isEqualTo(RobotCommand.M);
    }

    @Test
    void createCommandWithInvalidChar() {
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> RobotCommand.fromChar('X'));

        assertThat(illegalArgumentException.getMessage()).isEqualTo("Invalid command: X");
    }
}
