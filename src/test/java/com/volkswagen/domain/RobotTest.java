package com.volkswagen.domain;

import com.volkswagen.application.port.in.RobotCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.volkswagen.domain.RobotPositionTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RobotTest {

    private Workplace workplace;

    private RobotPosition robotPosition;

    private Robot robot;

    public Robot defaultRobot() {
        return Robot.from(workplace, robotPosition);
    }

    @BeforeEach
    void setUp() {
        workplace = WorkplaceTest.createDefault();
        robotPosition = RobotPositionTest.createDefault();
        robot = defaultRobot();
    }


    @Test
    void creates_a_robot() {
        assertThat(robot.position().y()).isEqualTo(DEFAULT_ROBOT_POSITION_Y);
        assertThat(robot.position().x()).isEqualTo(DEFAULT_ROBOT_POSITION_X);
        assertThat(robot.position().orientation()).isEqualTo(DEFAULT_ROBOT_POSITION_ORIENTATION);
    }

    @Test
    void creates_a_robot_outside_the_workplace() {
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> Robot.from(workplace, RobotPosition.from("6 6N")));

        assertThat(illegalArgumentException.getMessage()).isEqualTo("Invalid coordinates");
    }

    @Test
    void moves_forward_outside_the_workplace() {
        Workplace workplace = Workplace.from("55");
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> Robot.from(workplace, RobotPosition.from("5 5N")).moveForward());

        assertThat(illegalArgumentException.getMessage()).isEqualTo("Robot is outside the workplace");
    }

    @Test
    void robot_found_an_obstacle() {
        Robot finishedRobot = Robot.from(workplace, RobotPosition.from("1 3N"));
        workplace.addFinishedRobot(finishedRobot);

        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> Robot.from(workplace, RobotPosition.from("1 2N")).moveForward());

        assertThat(illegalArgumentException.getMessage()).isEqualTo("Robot has found an obstacle");
    }

    @Test
    void robot_workplace_is_null() {
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> Robot.from(null, RobotPosition.from("1 2N")));

        assertThat(illegalArgumentException.getMessage()).isEqualTo("Workplace cannot be null");
    }

    @Test
    void robot_position_is_null() {
        IllegalArgumentException illegalArgumentException = assertThrows(
            IllegalArgumentException.class,
            () -> Robot.from(workplace, null));

        assertThat(illegalArgumentException.getMessage()).isEqualTo("Position cannot be null");
    }

    @Test
    void turns_right() {
        robot.turnRight();
        assertThat(robot.position().orientation()).isEqualTo(RobotOrientation.EAST);
    }

    @Test
    void moves_forward() {
        robot.moveForward();
        assertThat(robot.position().y()).isEqualTo(DEFAULT_ROBOT_POSITION_Y + 1);
        assertThat(robot.position().x()).isEqualTo(DEFAULT_ROBOT_POSITION_X);
        assertThat(robot.position().orientation()).isEqualTo(DEFAULT_ROBOT_POSITION_ORIENTATION);
    }

    @Test
    void turns_left() {
        robot.turnLeft();
        assertThat(robot.position().orientation()).isEqualTo(RobotOrientation.WEST);
    }

    @Test
    void testToString() {
        String expected = DEFAULT_ROBOT_POSITION_X + " " + DEFAULT_ROBOT_POSITION_Y + " " + DEFAULT_ROBOT_POSITION_ORIENTATION.getRawValue();

        assertThat(robot.toString()).hasToString(expected);
    }

    @Test
    void execute_command_M_works() {
        robot.executeCommand(RobotCommand.M);
        assertThat(robot.position().y()).isEqualTo(DEFAULT_ROBOT_POSITION_Y + 1);
        assertThat(robot.position().x()).isEqualTo(DEFAULT_ROBOT_POSITION_X);
        assertThat(robot.position().orientation()).isEqualTo(DEFAULT_ROBOT_POSITION_ORIENTATION);
    }

    @Test
    void execute_command_L_works() {
        robot.executeCommand(RobotCommand.L);
        assertThat(robot.position().y()).isEqualTo(DEFAULT_ROBOT_POSITION_Y);
        assertThat(robot.position().x()).isEqualTo(DEFAULT_ROBOT_POSITION_X);
        assertThat(robot.position().orientation()).isEqualTo(RobotOrientation.WEST);
    }

    @Test
    void execute_command_R_works() {
        robot.executeCommand(RobotCommand.R);
        assertThat(robot.position().y()).isEqualTo(DEFAULT_ROBOT_POSITION_Y);
        assertThat(robot.position().x()).isEqualTo(DEFAULT_ROBOT_POSITION_X);
        assertThat(robot.position().orientation()).isEqualTo(RobotOrientation.EAST);
    }

    @Test
    void move_forward_when_north_orientation_works() {
        robot.executeCommand(RobotCommand.M);
        assertThat(robot.position().y()).isEqualTo(DEFAULT_ROBOT_POSITION_Y + 1);
        assertThat(robot.position().x()).isEqualTo(DEFAULT_ROBOT_POSITION_X);
        assertThat(robot.position().orientation()).isEqualTo(DEFAULT_ROBOT_POSITION_ORIENTATION);
    }

    @Test
    void move_forward_when_south_orientation_works() {
        robot.executeCommand(RobotCommand.L);
        robot.executeCommand(RobotCommand.L);
        robot.executeCommand(RobotCommand.M);
        assertThat(robot.position().y()).isEqualTo(DEFAULT_ROBOT_POSITION_Y - 1);
        assertThat(robot.position().x()).isEqualTo(DEFAULT_ROBOT_POSITION_X);
        assertThat(robot.position().orientation()).isEqualTo(RobotOrientation.SOUTH);
    }

    @Test
    void move_forward_when_east_orientation_works() {
        robot.executeCommand(RobotCommand.R);
        robot.executeCommand(RobotCommand.M);
        assertThat(robot.position().y()).isEqualTo(DEFAULT_ROBOT_POSITION_Y);
        assertThat(robot.position().x()).isEqualTo(DEFAULT_ROBOT_POSITION_X + 1);
        assertThat(robot.position().orientation()).isEqualTo(RobotOrientation.EAST);
    }

    @Test
    void move_forward_when_west_orientation_works() {
        robot.executeCommand(RobotCommand.L);
        robot.executeCommand(RobotCommand.M);
        assertThat(robot.position().y()).isEqualTo(DEFAULT_ROBOT_POSITION_Y);
        assertThat(robot.position().x()).isEqualTo(DEFAULT_ROBOT_POSITION_X - 1);
        assertThat(robot.position().orientation()).isEqualTo(RobotOrientation.WEST);
    }

    @Test
    void turn_left_when_north_orientation_works() {
        robot.executeCommand(RobotCommand.L);
        assertThat(robot.position().y()).isEqualTo(DEFAULT_ROBOT_POSITION_Y);
        assertThat(robot.position().x()).isEqualTo(DEFAULT_ROBOT_POSITION_X);
        assertThat(robot.position().orientation()).isEqualTo(RobotOrientation.WEST);
    }

    @Test
    void turn_left_when_south_orientation_works() {
        robot.executeCommand(RobotCommand.L);
        robot.executeCommand(RobotCommand.L);
        assertThat(robot.position().y()).isEqualTo(DEFAULT_ROBOT_POSITION_Y);
        assertThat(robot.position().x()).isEqualTo(DEFAULT_ROBOT_POSITION_X);
        assertThat(robot.position().orientation()).isEqualTo(RobotOrientation.SOUTH);
    }

    @Test
    void turn_left_when_east_orientation_works() {
        robot.executeCommand(RobotCommand.R);
        robot.executeCommand(RobotCommand.L);
        assertThat(robot.position().y()).isEqualTo(DEFAULT_ROBOT_POSITION_Y);
        assertThat(robot.position().x()).isEqualTo(DEFAULT_ROBOT_POSITION_X);
        assertThat(robot.position().orientation()).isEqualTo(RobotOrientation.NORTH);
    }

    @Test
    void turn_left_when_west_orientation_works() {
        robot.executeCommand(RobotCommand.L);
        robot.executeCommand(RobotCommand.L);
        robot.executeCommand(RobotCommand.L);
        assertThat(robot.position().y()).isEqualTo(DEFAULT_ROBOT_POSITION_Y);
        assertThat(robot.position().x()).isEqualTo(DEFAULT_ROBOT_POSITION_X);
        assertThat(robot.position().orientation()).isEqualTo(RobotOrientation.EAST);
    }

    @Test
    void turn_right_when_north_orientation_works() {
        robot.executeCommand(RobotCommand.R);
        assertThat(robot.position().y()).isEqualTo(DEFAULT_ROBOT_POSITION_Y);
        assertThat(robot.position().x()).isEqualTo(DEFAULT_ROBOT_POSITION_X);
        assertThat(robot.position().orientation()).isEqualTo(RobotOrientation.EAST);
    }

    @Test
    void turn_right_when_south_orientation_works() {
        robot.executeCommand(RobotCommand.L);
        robot.executeCommand(RobotCommand.L);
        robot.executeCommand(RobotCommand.R);
        assertThat(robot.position().y()).isEqualTo(DEFAULT_ROBOT_POSITION_Y);
        assertThat(robot.position().x()).isEqualTo(DEFAULT_ROBOT_POSITION_X);
        assertThat(robot.position().orientation()).isEqualTo(RobotOrientation.WEST);
    }

    @Test
    void turn_right_when_east_orientation_works() {
        robot.executeCommand(RobotCommand.R);
        robot.executeCommand(RobotCommand.R);
        assertThat(robot.position().y()).isEqualTo(DEFAULT_ROBOT_POSITION_Y);
        assertThat(robot.position().x()).isEqualTo(DEFAULT_ROBOT_POSITION_X);
        assertThat(robot.position().orientation()).isEqualTo(RobotOrientation.SOUTH);
    }

    @Test
    void turn_right_when_west_orientation_works() {
        robot.executeCommand(RobotCommand.L);
        robot.executeCommand(RobotCommand.R);
        assertThat(robot.position().y()).isEqualTo(DEFAULT_ROBOT_POSITION_Y);
        assertThat(robot.position().x()).isEqualTo(DEFAULT_ROBOT_POSITION_X);
        assertThat(robot.position().orientation()).isEqualTo(RobotOrientation.NORTH);
    }

}
