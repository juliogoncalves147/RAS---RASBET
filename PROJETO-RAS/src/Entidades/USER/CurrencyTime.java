package Entidades.USER;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class CurrencyTime {

    private double valueCurrency;
    private String timezone;
    private String currency;

    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    public CurrencyTime() {
        this.currency = "€";
        this.timezone = "Europe/Lisbon";
        this.valueCurrency = 1;

        try {
            JSONObject json = readJsonFromUrl("http://www.geoplugin.net/json.gp?base_currency=EUR");
            Double valueCurrency = json.getDouble("geoplugin_currencyConverter");
            this.valueCurrency = valueCurrency != 0 ? valueCurrency : 1;
            this.timezone = json.get("geoplugin_timezone").toString();
            this.currency = json.get("geoplugin_currencySymbol").toString();
        } catch (IOException e) {
        }
    }

    public double convertCurrency(double value) {
        return value * this.valueCurrency;
    }

    public LocalDateTime convertdatetime(LocalDateTime ldt) {
        ZoneId server = ZoneId.of("Europe/Lisbon");
        ZonedDateTime serverDateTime = ldt.atZone(server);

        ZoneId client = ZoneId.of(this.timezone);
        ZonedDateTime newDateTime = serverDateTime.withZoneSameInstant(client);
        return newDateTime.toLocalDateTime();
    }

    public String getCurrency() {
        return currency;
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
