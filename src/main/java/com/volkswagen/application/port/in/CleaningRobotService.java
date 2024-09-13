package com.volkswagen.application.port.in;

import com.volkswagen.domain.Robot;

import java.util.List;

public interface CleaningRobotService {
    List<Robot> controlRobots(RobotsDataCommand robotsDataCommand);
}
