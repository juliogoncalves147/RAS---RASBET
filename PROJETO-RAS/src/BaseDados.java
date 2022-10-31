import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;


public class BaseDados {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    private static String url = "jdbc:mysql://localhost:3306/projeto_ras";
    private static String user = "root";
    private static String password = "root";

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM utilizador");
            while (rs.next()) {
                System.out.println(rs.getString("nome"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}