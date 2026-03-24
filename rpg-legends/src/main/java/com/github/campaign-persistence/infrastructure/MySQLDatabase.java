package com.github.infrastructure;

import com.github.domain.IDatabase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The MySQLDatabase class provides a MySQL-specific implementation of the IDatabase interface.
 * It manages the connection to a MySQL database and provides methods for executing queries and managing records.
 */
public class MySQLDatabase implements IDatabase, AutoCloseable {

    private String url;
    private String user;
    private String password;
    private Connection connection;

    /**
     * Constructs a MySQLDatabase instance and initializes the connection and schema.
     *
     * @param url the JDBC URL for the MySQL database
     * @param user the database user
     * @param password the database password
     */
    public MySQLDatabase(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        connect();
        initializeSchema();
    }

    /**
     * Establishes a connection to the MySQL database.
     */
    private void connect() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    /**
     * Ensures that the database connection is open, reconnecting if necessary.
     */
    private void ensureConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            connect();
        }
    }

    /**
     * Initializes the database schema by creating the necessary tables if they do not exist.
     */
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

    /**
     * Executes a SQL update query (INSERT, UPDATE, DELETE).
     *
     * @param query the SQL query to execute
     * @param params the parameters to bind to the query
     * @return true if the execution was successful, false otherwise
     */
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

    /**
     * Executes a given SQL query.
     *
     * @param query the SQL query to be executed
     */
    @Override
    public void executeQuery(String query) {
        executeUpdate(query);
    }

    /**
     * Fetches a record from the campaign_saves table based on the user ID.
     *
     * @param id the user ID of the record to fetch
     * @return the saved data as a String, or null if not found
     */
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

    /**
     * Deletes a record from the campaign_saves table based on the user ID.
     *
     * @param id the user ID of the record to delete
     * @return true if the record was deleted successfully, false otherwise
     */
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

    /**
     * Closes the database connection.
     *
     * @throws Exception if a database access error occurs
     */
    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
