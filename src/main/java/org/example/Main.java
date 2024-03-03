package org.example;

import org.example.Aggregation.AggregateService;
import org.example.GeoCoding.GeoLocationService;
import org.example.WeatherData.WeatherData;
import org.example.WeatherServices.AccuweatherService;
import org.example.WeatherServices.OpenWeatherService;

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
                OpenWeatherService openWeatherService = new OpenWeatherService();
                WeatherData openWeatherData = openWeatherService.getWeatherData(locationData.getLatitude(), locationData.getLongitude());

                AccuweatherService accuWeatherService = new AccuweatherService();
                WeatherData accuWeatherData = accuWeatherService.getWeatherData(locationData.getLatitude(), locationData.getLongitude());

                // Tutaj następuje wstrzyknięcie dwóch serwisów
                AggregateService aggregateService = new AggregateService(accuWeatherService, openWeatherService);

                WeatherData aggregatedWeather = aggregateService.getAggregatedWeather(locationData.getLatitude(), locationData.getLongitude());

                if (openWeatherData != null) {
                    // Konwersja temperatury z kelwinów na stopnie Celsjusza
                    double temperatureCelsius = openWeatherData.getTemperature() - 273.15;
                    System.out.println("Dane pogodowe Open Weather:");
                    System.out.println("Temperatura: " + temperatureCelsius + " stopni Celsjusza");
                    System.out.println("Ciśnienie: " + openWeatherData.getPressure() + " hPa");
                    System.out.println("Wilgotność: " + openWeatherData.getHumidity() + "%");
                    System.out.println("Prędkość wiatru: " + openWeatherData.getWindSpeed() + " m/s");
                    System.out.println("Kierunek wiatru: " + openWeatherData.getWindDirection() + " stopni");
                } else {
                    System.out.println("Nie udało się pobrać danych pogodowych.");
                }
                if (accuWeatherData != null) {
                    System.out.println("Dane pogodowe Accu Weather:");
                    System.out.println("Temperatura: " + accuWeatherData.getTemperature() + " stopni Celsjusza");
                    System.out.println("Ciśnienie: " + accuWeatherData.getPressure() + " hPa");
                    System.out.println("Wilgotność: " + accuWeatherData.getHumidity() + "%");
                    System.out.println("Prędkość wiatru: " + accuWeatherData.getWindSpeed() + " m/s");
                    System.out.println("Kierunek wiatru: " + accuWeatherData.getWindDirection() + " stopni");
                } else {
                    System.out.println("Nie udało się pobrać danych pogodowych.");
                }
                System.out.println("Średnie wartosci 2 serwisów");
                System.out.println("Temperatura: " + aggregatedWeather.getTemperature());
                System.out.println("Ciśnienie: " + aggregatedWeather.getPressure());
                System.out.println("Wilgotność: " + aggregatedWeather.getHumidity());
                System.out.println("Prędkość wiatru: " + aggregatedWeather.getWindSpeed());
                System.out.println("Kierunek wiatru: " + aggregatedWeather.getWindDirection());
                System.out.println("------------");
            } else {
                System.out.println("Nie udało się uzyskać współrzędnych dla podanej lokalizacji.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}