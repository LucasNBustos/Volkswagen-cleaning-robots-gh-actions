package com.volkswagen.adapter.console.out;

import com.volkswagen.application.port.out.RobotsResultPort;
import com.volkswagen.domain.Robot;

import java.util.List;

public class ConsolePrintAdapter implements RobotsResultPort {
    @Override
    public void processRobotsResult(List<Robot> robots) {
        robots.forEach(System.out::println);
    }
}
