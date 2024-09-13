package com.volkswagen.application.exception;

import com.volkswagen.domain.Robot;
import com.volkswagen.domain.RobotPosition;
import com.volkswagen.domain.Workplace;

public class ObstacleException extends RuntimeException {

    private final Robot robot;

    private final Workplace workplace;

    private final int x;

    private final int y;

    public ObstacleException(Robot robot, Workplace workplace, int x, int y) {
        super("Robot has found an obstacle");
        this.robot = robot;
        this.workplace = workplace;
        this.x = x;
        this.y = y;
    }

}
