package com.github.infrastructure;

import com.github.domain.IDatabase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLDatabase implements IDatabase {

    private String url;
    private String user;
    private String password;
    private Connection connection;

    public MySQLDatabase(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        connect();
        initializeSchema();
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    private void ensureConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            connect();
        }
    }

    private void initializeSchema() {
        // Only attempt to build schema if the connection actually exists
        if (connection == null) return; 

        String schemaQuery = "CREATE TABLE IF NOT EXISTS campaign_saves (" +
                             "userId INT PRIMARY KEY, " +
                             "save_data LONGTEXT NOT NULL" +
                             ")";
        boolean success = executeUpdate(schemaQuery);
        if (success) {
            System.out.println("Database schema initialized successfully.");
        } else {
            System.err.println("Database schema initialization failed.");
        }
    }

    @Override
    public boolean executeUpdate(String query, Object... params) {
        ensureConnection();
        // Crash prevention: check if connection is still null
        if (connection == null) return false; 

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("SQL execution error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Object fetchRecord(int id) {
        ensureConnection();
        // Crash prevention: check if connection is still null
        if (connection == null) return null; 

        String query = "SELECT save_data FROM campaign_saves WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("save_data");
            }
        } catch (SQLException e) {
            System.err.println("SQL fetch error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteRecord(int id) {
        ensureConnection();
        // Crash prevention: check if connection is still null
        if (connection == null) return false;

        String query = "DELETE FROM campaign_saves WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("SQL delete error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
