package Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class API {
    private static HttpURLConnection connection;

    private static void updateSQL(List<Jogo> jogos) throws SQLException {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projeto_ras", "ras", "ras");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        for (Jogo jogo : jogos) {
            String id = jogo.getId();
            String data = jogo.getCommenceTime();
            String formatada = data.substring(0, 10) + " " + data.substring(11, 19);
            int estado = jogo.getCompleted() ? 1 : 0;
            String query = "insert ignore into Jogo values ( ? , 'Futebol', ? ,  ? );";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, id);
            preparedStmt.setString (2, formatada);
            preparedStmt.setInt  (3, estado);
            preparedStmt.execute();

            Bookmaker bookmaker = jogo.getBookmakers().get(0);
            String homeTeam =  bookmaker.getMarkets().get(0).getOutcomes().get(1).getName();
            String awayTeam =  bookmaker.getMarkets().get(0).getOutcomes().get(0).getName();
            String draw = bookmaker.getMarkets().get(0).getOutcomes().get(2).getName();
            double homeOdd = bookmaker.getMarkets().get(0).getOutcomes().get(1).getPrice();
            double awayOdd = bookmaker.getMarkets().get(0).getOutcomes().get(0).getPrice();
            double drawOdd = bookmaker.getMarkets().get(0).getOutcomes().get(2).getPrice();

            putOdd(conn, id, homeTeam, homeOdd);
            putOdd(conn, id, awayTeam, awayOdd);

            putOdd(conn, id, draw, drawOdd);
        }
        conn.close();
    }

    private static void putOdd(Connection conn, String id, String team, double odd) throws SQLException {
        PreparedStatement preparedStmt;
        String query2 = "insert ignore into Odds (idJogo, prognostico, valor) values (?, ? ,  ? ) on duplicate key update valor = ?;";
        preparedStmt = conn.prepareStatement(query2);
        preparedStmt.setString (1, id);
        preparedStmt.setString (2, team);
        preparedStmt.setDouble (3, odd);
        preparedStmt.setDouble( 4, odd);
        preparedStmt.executeUpdate();
    }

    private static void StringtoJson(String json) throws JsonProcessingException, SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Jogo> listJogo = objectMapper.readValue(json, new TypeReference<>() {
        });

        for(Jogo j : listJogo){
            System.out.println(j.toString());
        }

        updateSQL(listJogo);
    }

    public static void main(String[] args) {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    BufferedReader reader;
                    String line;
                    StringBuilder responseContent = new StringBuilder();

                    URL url = new URL("http://ucras.di.uminho.pt/v1/games/");
                    connection = (HttpURLConnection) url.openConnection();
                    // Request setup
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(500000);
                    connection.setReadTimeout(500000);

                    int status = connection.getResponseCode();
                    //System.out.println(status);

                    if (status > 299) {
                        reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    } else {
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    }
                    while((line = reader.readLine()) != null) {
                        responseContent.append(line);
                    }
                    reader.close();

                    StringtoJson(responseContent.toString());
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                } finally {
                    connection.disconnect();
                }
            }
        };

        Timer timer = new Timer("Timer");

        long delay = 120000L;

        timer.scheduleAtFixedRate(task, 0,  delay);
    }
}
