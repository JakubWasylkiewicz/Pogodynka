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

public class GeoLocationService {

    private static final String API_KEY = "api_key=65e2f07a7c763292633381fac5b8a44";

    public static class LocationData {
        private final double latitude;
        private final double longitude;

        public LocationData(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }

    public LocationData getLocationData(String cityName, String postalCode, String street) throws IOException {
        String urlString = "https://geocode.maps.co/search?q=" + cityName + "&postalCode=" + postalCode + "&street=" + street + "&apiKey=" + API_KEY;
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
                JsonArray jsonArray = jsonResponse.getAsJsonArray();
                JsonObject firstResult = jsonArray.get(0).getAsJsonObject();
                double latitude = firstResult.get("lat").getAsDouble();
                double longitude = firstResult.get("lon").getAsDouble();
                return new LocationData(latitude, longitude);
            }
        }
        return null;
    }
}