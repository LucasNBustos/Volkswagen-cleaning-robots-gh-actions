package com.volkswagen.adapter.file.in;

import com.volkswagen.application.port.in.RobotCommand;
import com.volkswagen.application.port.in.RobotConfiguration;
import com.volkswagen.application.port.in.RobotsDataCommand;
import com.volkswagen.domain.RobotPosition;
import com.volkswagen.domain.Workplace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.volkswagen.application.port.in.RobotCommand.fromChar;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class ReadRobotsDataAdapter {
    private final ClassLoader classLoader;

    public ReadRobotsDataAdapter() {
        this.classLoader = ReadRobotsDataAdapter.class.getClassLoader();
    }

    public Optional<RobotsDataCommand> readDataFrom(String filePath) {
        if (filePath == null) throw new IllegalArgumentException("File path cannot be null");

        InputStream inputStream = classLoader.getResourceAsStream(filePath);
        try {
            if (inputStream != null && inputStream.available() > 0) {
                return parseFile(inputStream);
            }
        } catch (IOException e) {
            return empty();
        }
        return empty();
    }

    private Optional<RobotsDataCommand> parseFile(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            Workplace workplace = Workplace.from(reader.readLine());
            List<RobotConfiguration> robotConfigurations = new ArrayList<>();

            while (reader.ready()) {
                String rawRobotPosition = reader.readLine();
                RobotPosition robotPosition = RobotPosition.from(rawRobotPosition);

                List<RobotCommand> robotCommands = reader.readLine()
                    .chars()
                    .mapToObj(command -> fromChar((char) command))
                    .toList();
                robotConfigurations.add(new RobotConfiguration(robotPosition, robotCommands));

            }
            return of(new RobotsDataCommand(workplace, robotConfigurations));
        } catch (IOException e) {
            return empty();
        }
    }
}
