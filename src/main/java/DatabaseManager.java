
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:test.db";

    public static void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS players ("
                   + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                   + " username TEXT NOT NULL UNIQUE,"
                   + " password TEXT NOT NULL"
                   + ");";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'players' is ready.");
        } catch (SQLException e) {
            System.out.println("Table error: " + e.getMessage());
        }
    }

    public static void registerPlayer(String username, String password) {
        String sql = "INSERT INTO players(username, password) VALUES(?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            System.out.println("Player " + username + " registered!");
        } catch (SQLException e) {
            System.out.println("Register error: " + e.getMessage());
        }
    }

    public static boolean userExists(String username) {
        String sql = "SELECT 1 FROM players WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean checkPassword(String username, String password) {
        String sql = "SELECT password FROM players WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password").equals(password);
            }
        } catch (SQLException e) {
            System.out.println("Password check error: " + e.getMessage());
        }
        return false;
    }
}