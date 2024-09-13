package com.volkswagen.application.service;

import com.volkswagen.application.port.in.CleaningRobotService;
import com.volkswagen.application.port.in.RobotsDataCommand;
import com.volkswagen.application.port.out.RobotsResultPort;
import com.volkswagen.domain.Robot;
import com.volkswagen.domain.Workplace;

import java.util.ArrayList;
import java.util.List;

import static com.volkswagen.domain.Robot.from;

public class CleaningRobotServiceImpl implements CleaningRobotService {

    private final RobotsResultPort robotsResultPort;

    public CleaningRobotServiceImpl(RobotsResultPort robotsResultPort) {
        this.robotsResultPort = robotsResultPort;
    }

    @Override
    public List<Robot> controlRobots(RobotsDataCommand robotsDataCommand) {

        // TODO: Controla los robots, ejecutando todas las acciones  sobre cada robot.  Importante:  los robots no se pueden mover en paralelo.
        //Primero se procesa un robot.  Cuando este robot ha finalizado  porque ya se han procesado todas sus acciones,  entonces pasamos al siguiente robot.
        //Y así sucesivamente hasta que ya hemos trabajado con todos los robots.

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
