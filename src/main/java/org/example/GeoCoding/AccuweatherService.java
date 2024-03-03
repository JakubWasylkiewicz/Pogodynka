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
    private static final String ACCUWEATHER_API_KEY = "EjIs6w02WNNFJeJ4P72Bv4TkEVepCFcD";
    private static String getCityKey(double latitude, double longitude) throws IOException {
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

    private static String getWeatherData(String cityKey) throws IOException {
        String urlString = "http://dataservice.accuweather.com/currentconditions/v1/" + cityKey + "?apikey=" + ACCUWEATHER_API_KEY;

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

            return firstResult.toString();
        }
        return null;
    }
}

