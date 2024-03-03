package org.example.GeoCoding;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {

    private static final String API_KEY = "29a9b093e8a39ce93b7acc545b128a04"; //<-- Klucz API

    public static class WeatherData {
        private final double temperature;
        private final double pressure;
        private final double humidity;
        private final double windSpeed;
        private final double windDirection;

        public double getTemperature() {
            return temperature;
        }

        public double getPressure() {
            return pressure;
        }

        public double getHumidity() {
            return humidity;
        }

        public double getWindSpeed() {
            return windSpeed;
        }

        public double getWindDirection() {
            return windDirection;
        }

        public WeatherData(double temperature, double pressure, double humidity, double windSpeed, double windDirection) {
            this.temperature = temperature;
            this.pressure = pressure;
            this.humidity = humidity;
            this.windSpeed = windSpeed;
            this.windDirection = windDirection;
        }
    }

    public WeatherData getWeatherData(double latitude, double longitude) throws IOException {
        String urlString = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY;
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

            double temperature = jsonResponse.getAsJsonObject().getAsJsonObject("main").get("temp").getAsDouble();
            double pressure = jsonResponse.getAsJsonObject().getAsJsonObject("main").get("pressure").getAsDouble();
            double humidity = jsonResponse.getAsJsonObject().getAsJsonObject("main").get("humidity").getAsDouble();
            double windSpeed = jsonResponse.getAsJsonObject().getAsJsonObject("wind").get("speed").getAsDouble();
            double windDirection = jsonResponse.getAsJsonObject().getAsJsonObject("wind").get("deg").getAsDouble();

            return new WeatherData(temperature, pressure, humidity, windSpeed, windDirection);
        } else {
            throw new IOException("Failed to fetch weather data, response code: " + responseCode);
        }
    }
}