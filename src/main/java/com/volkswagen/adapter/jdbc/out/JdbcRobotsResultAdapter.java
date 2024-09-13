package com.volkswagen.adapter.jdbc.out;

import com.volkswagen.application.port.out.RobotsResultPort;
import com.volkswagen.domain.Robot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JdbcRobotsResultAdapter implements RobotsResultPort {

  private Connection connection;

  @Override
  public void processRobotsResult(List<Robot> robots) {
    try {
      initConnection();
      String query = "INSERT INTO robots_process (x, y, orientation) VALUES (?, ?, ?)";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      for(var robot : robots) {
        preparedStatement.setInt(1, robot.position().x());
        preparedStatement.setInt(2, robot.position().y());
        preparedStatement.setString(3, robot.position().orientation().getRawValue());
        if (preparedStatement.executeUpdate() != 1) {
          System.out.println ("Failed to add a new robot record");
        }
      }
    }catch (SQLException e) {
      System.out.println(e.getMessage());
    }finally {
      try {
        connection.close();
      }catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private void initConnection() throws SQLException {
    String url = "jdbc:mysql://localhost:3306/robots?serverTimezone=UTC";
    String username = "root";
    String password = "Dddedo";
    connection = DriverManager.getConnection(url, username, password);
  }
}
