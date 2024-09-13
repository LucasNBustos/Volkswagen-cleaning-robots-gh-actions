package com.volkswagen.application.port.in;

import com.volkswagen.domain.Workplace;

import java.util.List;

public record RobotsDataCommand(Workplace workplace, List<RobotConfiguration> configurations) {

}
