package org.example.GeoCoding;

import com.google.gson.*;
import org.example.WeatherData.WeatherData;
import org.example.WeatherInterface.WeatherInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AccuweatherService implements WeatherInterface {
    private static final String ACCUWEATHER_API_KEY = "SbOjnr34wwAfOV5b2AUPovGKGF3JP9Os";

    public static String getCityKey(double latitude, double longitude) throws IOException {
        String urlString = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?q=" + latitude + "," + longitude + "&apikey=" + ACCUWEATHER_API_KEY;

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

    @Override
    public WeatherData getWeatherData(double latitude, double longitude) throws IOException {
        String cityKey = getCityKey(latitude, longitude);
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
            JsonElement jsonResponse = parser.parse(response.toString());

            if (jsonResponse.isJsonArray()) {
                JsonArray weatherArray = jsonResponse.getAsJsonArray();
                if (weatherArray.size() > 0) {
                    JsonObject weatherObject = weatherArray.get(0).getAsJsonObject();
                    System.out.println(weatherObject.toString());
                    double temperature = weatherObject.getAsJsonObject("Temperature").getAsJsonObject("Metric").get("Value").getAsDouble();
//                    double pressure = weatherObject.get("Pressure").getAsJsonObject("Metric").get("Value").getAsDouble();
                    double pressure = 0;
                    double pressure2 = weatherObject.getAsJsonObject("Pressure").getAsJsonObject("Metric").get("Value").getAsDouble();
                    double humidity = weatherObject.get("RelativeHumidity").getAsDouble();
                    double windSpeed = weatherObject.getAsJsonObject("Wind").getAsJsonObject("Speed").getAsJsonObject("Metric").get("Value").getAsDouble();
                    double windDirection = weatherObject.getAsJsonObject("Wind").getAsJsonObject("Direction").get("Degrees").getAsDouble();

                    return new WeatherData(temperature, pressure2, humidity, windSpeed, windDirection);
                }
            }
        }
        return null;
    }


}
