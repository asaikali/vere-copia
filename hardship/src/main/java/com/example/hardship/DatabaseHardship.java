package com.example.hardship;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHardship {

  private final DataSource dataSource;
  private final List<Connection> connections = new ArrayList<>();

  public DatabaseHardship(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  synchronized public void closeOpenConnections() {
    this.connections.forEach(c -> {
      try {
        c.close();
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    });

    this.connections.clear();
  }

  synchronized public void leakConnection(){
    try {
      var connection = this.dataSource.getConnection();
      if(connections.contains(connection)) {
        throw new IllegalStateException("Not leaking connections");
      }
      this.connections.add(connection);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
