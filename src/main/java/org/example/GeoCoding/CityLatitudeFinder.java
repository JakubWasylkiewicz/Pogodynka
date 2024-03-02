package org.example.GeoCoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class CityLatitudeFinder {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Podaj nazwę miasta: ");
            String cityName = reader.readLine();
            double latitude = getLatitude(cityName);
            if (latitude != Double.MIN_VALUE) {
                System.out.println("Szerokość geograficzna miasta " + cityName + ": " + latitude);
            } else {
                System.out.println("Nie udało się uzyskać szerokości geograficznej dla podanego miasta.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double getLatitude(String cityName) {
        String apiURL = "https://geocode.maps.co/search?q=" + cityName + "&api_key=65e2f07a7c763292633381fac5b8a44";
        try {
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            StringBuilder response = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                response.append(output);
            }

            conn.disconnect();

            System.out.println("Odpowiedź z serwera: " + response.toString()); // Wyświetl odpowiedź JSON

            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(response.toString());

            double latitude = Double.MIN_VALUE;
            if (jsonElement.isJsonObject()) {
                latitude = jsonElement.getAsJsonObject().get("latt").getAsDouble();
            }
            return latitude;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Double.MIN_VALUE;
    }
}