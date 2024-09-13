package com.volkswagen.application.port.in;

import com.volkswagen.domain.RobotPosition;

import java.util.List;

public record RobotConfiguration(RobotPosition robotPosition,
                                 List<RobotCommand> commands) {
}
