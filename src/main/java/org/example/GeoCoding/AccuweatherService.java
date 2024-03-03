package org.example.GeoCoding;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AccuweatherService {

    private static final String ACCUWEATHER_API_KEY = "hZMIz9Res4GkhJ1goIXubSCXA3f3VcVG";

    public static class AccuWeatherData {
        private final double accuTemperature;
        private final double accuPressure;
        private final double accuHumidity;
        private final double accuWindSpeed;
        private final double accuWindDirection;

        public double getAccuTemperature() {
            return accuTemperature;
        }

        public double getAccuPressure() {
            return accuPressure;
        }

        public double getAccuHumidity() {
            return accuHumidity;
        }

        public double getAccuWindSpeed() {
            return accuWindSpeed;
        }

        public double getAccuWindDirection() {
            return accuWindDirection;
        }

        public AccuWeatherData(double accuTemperature, double accuPressure, double accuHumidity, double accuWindSpeed, double accuWindDirection) {
            this.accuTemperature = accuTemperature;
            this.accuPressure = accuPressure;
            this.accuHumidity = accuHumidity;
            this.accuWindSpeed = accuWindSpeed;
            this.accuWindDirection = accuWindDirection;
        }
    }

    public static AccuWeatherData getAccuWeatherData(double latitude, double longitude) throws IOException {
        String cityKey = getCityKey(latitude, longitude);
        if (cityKey == null) {
            System.out.println("Nie udało się uzyskać klucza miasta z AccuWeather.");
            return null;
        }

        String urlString = "http://dataservice.accuweather.com/currentconditions/v1/" + cityKey + "?apikey=" + ACCUWEATHER_API_KEY + "&details=true";

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(response.toString()).getAsJsonArray();
            JsonObject firstResult = jsonArray.get(0).getAsJsonObject();

            double accuTemperature = firstResult.get("Temperature").getAsJsonObject().get("Metric").getAsJsonObject().get("Value").getAsDouble();
            double accuPressure = firstResult.get("Pressure").getAsJsonObject().get("Metric").getAsJsonObject().get("Value").getAsDouble();
            double accuHumidity = firstResult.get("RelativeHumidity").getAsDouble();
            double accuWindSpeed = firstResult.get("Wind").getAsJsonObject().get("Speed").getAsJsonObject().get("Metric").getAsJsonObject().get("Value").getAsDouble();
            double accuWindDirection = firstResult.get("Wind").getAsJsonObject().get("Direction").getAsJsonObject().get("Degrees").getAsDouble();

            return new AccuWeatherData(accuTemperature, accuPressure, accuHumidity, accuWindSpeed, accuWindDirection);
        } else {
            throw new IOException("Failed to fetch AccuWeather data, response code: " + responseCode);
        }
    }

    private static String getCityKey(double latitude, double longitude) throws IOException {
        String urlString = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?q=" + latitude + "," + longitude + "&apikey=" + ACCUWEATHER_API_KEY ;

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonParser parser = new JsonParser();
            JsonElement jsonResponse = parser.parse(response.toString());

            if (jsonResponse.isJsonObject()) {
                JsonObject cityJson = jsonResponse.getAsJsonObject();
                return cityJson.get("Key").getAsString();
            }
        }
        return null;
    }
}
