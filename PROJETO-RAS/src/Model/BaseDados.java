package Model;

import java.sql.*;


public class BaseDados {
    private static Connection conn = null;
    private static Statement stmt = null;

    public BaseDados() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projeto_ras", "rasUser", "Asdd00392384??");
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean update(String query) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ResultSet query(String s) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(s);
        }
        catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return rs;
    }
}