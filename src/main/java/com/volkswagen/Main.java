package com.volkswagen;

import com.volkswagen.adapter.console.out.ConsolePrintAdapter;
import com.volkswagen.adapter.file.in.ReadRobotsDataAdapter;
import com.volkswagen.application.port.in.CleaningRobotService;
import com.volkswagen.application.port.out.RobotsResultPort;
import com.volkswagen.application.service.CleaningRobotServiceImpl;


public class Main {

    private static final String FILE_PATH = "robots.txt";

    public static void main(String[] args) {

        RobotsResultPort robotsResultPort = new ConsolePrintAdapter();

        CleaningRobotService cleaningRobotService = new CleaningRobotServiceImpl(robotsResultPort);

        ReadRobotsDataAdapter readRobotsDataAdapter = new ReadRobotsDataAdapter();

        readRobotsDataAdapter.readDataFrom(FILE_PATH).ifPresent(
                robotsDataCommand -> cleaningRobotService.controlRobots(robotsDataCommand));



    }
}
