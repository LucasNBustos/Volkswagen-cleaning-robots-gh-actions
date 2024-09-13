package com.volkswagen;

import com.volkswagen.adapter.console.out.ConsolePrintAdapter;
import com.volkswagen.adapter.file.in.ReadRobotsDataAdapter;
import com.volkswagen.adapter.jdbc.out.JdbcRobotsResultAdapter;
import com.volkswagen.application.port.in.CleaningRobotService;
import com.volkswagen.application.port.out.RobotsResultPort;
import com.volkswagen.application.service.CleaningRobotServiceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    private static final String FILE_PATH = "robots.txt";

    public static void main(String[] args) {

        RobotsResultPort robotsResultPort = new ConsolePrintAdapter();

        //RobotsResultPort robotsResultPort = new JdbcRobotsResultAdapter();

        CleaningRobotService cleaningRobotService = new CleaningRobotServiceImpl(robotsResultPort);

        ReadRobotsDataAdapter readRobotsDataAdapter = new ReadRobotsDataAdapter();

        readRobotsDataAdapter.readDataFrom(FILE_PATH).ifPresent(
                robotsDataCommand -> cleaningRobotService.controlRobots(robotsDataCommand));



    }
}
