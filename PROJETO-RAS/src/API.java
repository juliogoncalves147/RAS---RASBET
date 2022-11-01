import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.Charset;



public class API {

    private static HttpURLConnection connection;

    public static void main(String[] args) {
            /* // Método 1
            BufferedReader reader;
            String line;
            StringBuffer responseContent = new StringBuffer();

            try {
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
                System.out.println(responseContent.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            */


        /* */
        // Método 2
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://ucras.di.uminho.pt/v1/games/")).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
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
