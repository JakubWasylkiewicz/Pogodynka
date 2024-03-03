package org.example.GeoCoding;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AggregateMain {

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

                AggregateService aggregateService = new AggregateService();
                WeatherService.WeatherData aggregatedWeatherData = aggregateService.aggregateWeatherData(locationData.getLatitude(), locationData.getLongitude());

                if (aggregatedWeatherData != null) {
                    System.out.println("Agregowane dane pogodowe:");
                    System.out.println("Temperatura: " + aggregatedWeatherData.getTemperature() + " stopni Celsjusza");
                    System.out.println("Ciśnienie: " + aggregatedWeatherData.getPressure() + " hPa");
                    System.out.println("Wilgotność: " + aggregatedWeatherData.getHumidity() + "%");
                    System.out.println("Prędkość wiatru: " + aggregatedWeatherData.getWindSpeed() + " m/s");
                    System.out.println("Kierunek wiatru: " + aggregatedWeatherData.getWindDirection() + " stopni");
                } else {
                    System.out.println("Nie udało się uzyskać danych pogodowych.");
                }
            } else {
                System.out.println("Nie udało się uzyskać współrzędnych dla podanej lokalizacji.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

