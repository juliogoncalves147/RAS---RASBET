package Model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.*;
import java.util.Date;

public class API {

    private static HttpURLConnection connection;



    private static void updateSQL(List<Jogo> jogos) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/RASBET", "root", "root");


        for (Jogo jogo : jogos) {
            String id = jogo.getId();
            String desporto = "Futebol";
            String data = jogo.getCommenceTime();
            String formatada = data.substring(0, 10) + " " + data.substring(11, 19);
            int estado = jogo.getCompleted() ? 1 : 0;
            String query = "insert ignore into Jogo values ( ? , 'Futebol', ? ,  ? );";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, id);
            preparedStmt.setString (2, formatada);
            preparedStmt.setInt   (3, estado);
            preparedStmt.executeUpdate();

            Bookmaker bookmaker = jogo.getBookmakers().get(0);
            String homeTeam =  bookmaker.getMarkets().get(0).getOutcomes().get(1).getName();
            String awayTeam =  bookmaker.getMarkets().get(0).getOutcomes().get(0).getName();
            String draw = bookmaker.getMarkets().get(0).getOutcomes().get(2).getName();
            int homeOdd = bookmaker.getMarkets().get(0).getOutcomes().get(1).getPrice();
            int awayOdd = bookmaker.getMarkets().get(0).getOutcomes().get(0).getPrice();
            int drawOdd = bookmaker.getMarkets().get(0).getOutcomes().get(2).getPrice();
            // Alterar esta query para inserir os odds direitas
            String query2 = "insert ignore into Odds values ( ? , 'Futebol', ? ,  ? );";
            preparedStmt = conn.prepareStatement(query2);
            preparedStmt.setString (1, id);
            preparedStmt.setString (2, formatada);
            preparedStmt.setInt   (3, estado);
            preparedStmt.executeUpdate();
        }

        conn.close();
    }



    private static void StringtoJson(String json) throws JsonProcessingException, SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Jogo> listJogo = objectMapper.readValue(json, new TypeReference<List<Jogo>>(){});

        for(Jogo j : listJogo){
            System.out.println(j.toString());
        }

        updateSQL(listJogo);
    }



    public static void main(String[] args) throws JsonProcessingException, SQLException {
        // Método 1


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    BufferedReader reader;
                    String line;
                    StringBuffer responseContent = new StringBuffer();

                    URL url = new URL("http://ucras.di.uminho.pt/v1/games/");
                    connection = (HttpURLConnection) url.openConnection();
                    // Request setup
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);

                    int status = connection.getResponseCode();
                    //System.out.println(status);

                    if (status > 299) {
                        reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        while((line = reader.readLine()) != null) {
                            responseContent.append(line);
                        }
                        reader.close();
                    } else {
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        while((line = reader.readLine()) != null) {
                            responseContent.append(line);
                        }
                        reader.close();
                    }

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


        /*
        // Método 2
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://ucras.di.uminho.pt/v1/games/")).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(Model.API::StringtoJson)
                .join();
         */
    }

        /*
        public static String parse(String responseBody){
            JSONArray albums = new JSONArray(responseBody);
            for(int i = 0; i < albums.length(); i++) {
                JSONObject album = albums.getJSONObject(i);
                String id = album.getString("id");
                System.out.println(id + " \n");
            }
            return null;
        }*/

}
