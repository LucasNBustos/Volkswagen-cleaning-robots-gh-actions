package com.volkswagen.application.port.out;


import com.volkswagen.domain.Robot;

import java.sql.SQLException;
import java.util.List;

public interface RobotsResultPort {

    void processRobotsResult(List<Robot> robots);

}
