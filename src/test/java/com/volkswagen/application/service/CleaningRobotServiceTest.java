package com.volkswagen.application.service;

import com.volkswagen.adapter.console.out.ConsolePrintAdapter;
import com.volkswagen.application.exception.ObstacleException;
import com.volkswagen.application.port.in.CleaningRobotService;
import com.volkswagen.application.port.in.RobotCommand;
import com.volkswagen.application.port.in.RobotConfiguration;
import com.volkswagen.application.port.in.RobotsDataCommand;
import com.volkswagen.application.port.out.RobotsResultPort;
import com.volkswagen.domain.Robot;
import com.volkswagen.domain.RobotPosition;
import com.volkswagen.domain.WorkplaceTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CleaningRobotServiceTest {

    private final RobotsResultPort robotsResultPort = new ConsolePrintAdapter();

    private final CleaningRobotService cleaningRobotService = new CleaningRobotServiceImpl(robotsResultPort);

    private final List<RobotConfiguration> robotConfigurations = of(
        new RobotConfiguration(
            RobotPosition.from("1 2 N"),
            of(
                RobotCommand.fromChar('L'),
                RobotCommand.fromChar('M'),
                RobotCommand.fromChar('L'),
                RobotCommand.fromChar('M'),
                RobotCommand.fromChar('L'),
                RobotCommand.fromChar('M'),
                RobotCommand.fromChar('L'),
                RobotCommand.fromChar('M'),
                RobotCommand.fromChar('M')
            )
        ),
        new RobotConfiguration(
            RobotPosition.from("33 E"),
            of(
                RobotCommand.fromChar('M'),
                RobotCommand.fromChar('M'),
                RobotCommand.fromChar('R'),
                RobotCommand.fromChar('M'),
                RobotCommand.fromChar('M'),
                RobotCommand.fromChar('R'),
                RobotCommand.fromChar('M'),
                RobotCommand.fromChar('R'),
                RobotCommand.fromChar('R'),
                RobotCommand.fromChar('M')
            )
        )
    );

    private final List<RobotConfiguration> robotConfigurationsForCollision = of(
            new RobotConfiguration(
                    RobotPosition.from("1 2 N"), // 0, 1
                    of(
                            RobotCommand.fromChar('L'),
                            RobotCommand.fromChar('M'),
                            RobotCommand.fromChar('L'),
                            RobotCommand.fromChar('M')
                    )
            ),
            new RobotConfiguration(
                    RobotPosition.from("33 S"),
                    of(
                            RobotCommand.fromChar('M'), // 3, 2
                            RobotCommand.fromChar('M'), // 3, 1
                            RobotCommand.fromChar('R'),
                            RobotCommand.fromChar('M'), // 2, 1
                            RobotCommand.fromChar('M'), // 1, 1
                            RobotCommand.fromChar('M'), // 0, 1
                            RobotCommand.fromChar('R'),
                            RobotCommand.fromChar('M')
                    )
            )
    );

    private final RobotsDataCommand robotsDataCommand = new RobotsDataCommand(WorkplaceTest.createDefault(), robotConfigurations);
    private final RobotsDataCommand robotsDataCommandCollision = new RobotsDataCommand(WorkplaceTest.createDefault(), robotConfigurationsForCollision);

    @Test
    void returns_a_list_of_processed_robots() {
        List<Robot> robots = cleaningRobotService.controlRobots(robotsDataCommand);
        assertThat(robots).hasSize(2);
        robots.equals(robotsDataCommand.workplace().finishedRobots());
    }

    @Test
    void returns_a_list_of_robots_with_correct_positions() {
        List<Robot> robots = cleaningRobotService.controlRobots(robotsDataCommand);
        assertThat(robots.get(0).position()).isEqualTo(RobotPosition.from("1 3 N"));
        assertThat(robots.get(1).position()).isEqualTo(RobotPosition.from("5 1 E"));
    }

    @Test
    void process_robots_with_obstacle() {
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class,
                () -> cleaningRobotService.controlRobots(robotsDataCommandCollision));

        assertThat(illegalArgumentException.getMessage()).isEqualTo("Robot has found an obstacle");
    }

}
