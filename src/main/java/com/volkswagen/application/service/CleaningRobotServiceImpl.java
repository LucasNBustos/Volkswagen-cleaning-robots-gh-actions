package com.volkswagen.application.service;

import com.volkswagen.application.port.in.CleaningRobotService;
import com.volkswagen.application.port.in.RobotsDataCommand;
import com.volkswagen.application.port.out.RobotsResultPort;
import com.volkswagen.domain.Robot;
import com.volkswagen.domain.Workplace;

import java.util.ArrayList;
import java.util.List;

public class CleaningRobotServiceImpl implements CleaningRobotService {

    private final RobotsResultPort robotsResultPort;

    public CleaningRobotServiceImpl(RobotsResultPort robotsResultPort) {
        this.robotsResultPort = robotsResultPort;
    }

    @Override
    public List<Robot> controlRobots(RobotsDataCommand robotsDataCommand) {

        Workplace workplace = robotsDataCommand.workplace();
        List<Robot> listaRobotsFinal = new ArrayList<>();

        for (var robotConfiguration : robotsDataCommand.configurations()){
            Robot robot = Robot.from(workplace, robotConfiguration.robotPosition());
            listaRobotsFinal.add(robot);
            for (var robotCommand : robotConfiguration.commands()){
                robot.executeCommand(robotCommand);
            }
            workplace.addFinishedRobot(robot);
        }

        robotsResultPort.processRobotsResult(listaRobotsFinal);
        return listaRobotsFinal;
    }
}
