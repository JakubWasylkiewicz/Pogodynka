package org.example.GeoCoding;

import org.example.WeatherData.WeatherData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Podaj nazwę miasta: ");
            String cityName = reader.readLine();
            System.out.print("Podaj kod pocztowy: ");
            String postalCode = reader.readLine();
            System.out.print("Podaj ulicę: ");
            String street = reader.readLine();

            GeoLocationService geoLocationService = new GeoLocationService();
            GeoLocationService.LocationData locationData = geoLocationService.getLocationData(cityName, postalCode, street);

            if (locationData != null) {
                System.out.println("Współrzędne dla lokalizacji:");
                System.out.println("Szerokość geograficzna (latitude): " + locationData.getLatitude());
                System.out.println("Długość geograficzna (longitude): " + locationData.getLongitude());

                // Pobierz dane pogodowe na podstawie lokalizacji
                OpenWeatherService weatherService = new OpenWeatherService();
                WeatherData weatherData = weatherService.getWeatherData(locationData.getLatitude(), locationData.getLongitude());

                if (weatherData != null) {
                    // Konwersja temperatury z kelwinów na stopnie Celsjusza
                    double temperatureCelsius = weatherData.getTemperature() - 273.15;

                    System.out.println("Dane pogodowe:");
                    System.out.println("Temperatura: " + temperatureCelsius + " stopni Celsjusza");
                    System.out.println("Ciśnienie: " + weatherData.getPressure() + " hPa");
                    System.out.println("Wilgotność: " + weatherData.getHumidity() + "%");
                    System.out.println("Prędkość wiatru: " + weatherData.getWindSpeed() + " m/s");
                    System.out.println("Kierunek wiatru: " + weatherData.getWindDirection() + " stopni");
                } else {
                    System.out.println("Nie udało się pobrać danych pogodowych.");
                }
            } else {
                System.out.println("Nie udało się uzyskać współrzędnych dla podanej lokalizacji.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}