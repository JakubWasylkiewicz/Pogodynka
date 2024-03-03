package org.example.GeoCoding;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

public class AggregateService {

    private WeatherService weatherService;
    private AccuweatherService accuweatherService;

    public AggregateService() {
        this.weatherService = new WeatherService();
        this.accuweatherService = new AccuweatherService();
    }

    public static class AggregatedWeatherData {
        private final double averageTemperature;
        private final double averagePressure;
        private final double averageHumidity;
        private final double averageWindSpeed;
        private final double averageWindDirection;

        public double getAverageTemperature() {
            return averageTemperature;
        }

        public double getAveragePressure() {
            return averagePressure;
        }

        public double getAverageHumidity() {
            return averageHumidity;
        }

        public double getAverageWindSpeed() {
            return averageWindSpeed;
        }

        public double getAverageWindDirection() {
            return averageWindDirection;
        }

        public AggregatedWeatherData(double averageTemperature, double averagePressure, double averageHumidity, double averageWindSpeed, double averageWindDirection) {
            this.averageTemperature = averageTemperature;
            this.averagePressure = averagePressure;
            this.averageHumidity = averageHumidity;
            this.averageWindSpeed = averageWindSpeed;
            this.averageWindDirection = averageWindDirection;
        }
    }

    public AggregatedWeatherData getAggregatedWeatherData(double latitude, double longitude) {
        try {
            WeatherService.WeatherData openWeatherData = weatherService.getWeatherData(latitude, longitude);
            AccuweatherService.AccuWeatherData accuWeatherData = accuweatherService.getAccuWeatherData(latitude, longitude);

            if (openWeatherData != null && accuWeatherData != null) {
                double averageTemperature = (openWeatherData.getTemperature() + accuWeatherData.getAccuTemperature()) / 2;
                double averagePressure = (openWeatherData.getPressure() + accuWeatherData.getAccuPressure()) / 2;
                double averageHumidity = (openWeatherData.getHumidity() + accuWeatherData.getAccuHumidity()) / 2;
                double averageWindSpeed = (openWeatherData.getWindSpeed() + accuWeatherData.getAccuWindSpeed()) / 2;
                double averageWindDirection = (openWeatherData.getWindDirection() + accuWeatherData.getAccuWindDirection()) / 2;

                return new AggregatedWeatherData(averageTemperature, averagePressure, averageHumidity, averageWindSpeed, averageWindDirection);
            } else {
                System.out.println("Nie udało się uzyskać wszystkich danych do agregacji.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private double parseAccuweatherTemperature(String accuweatherData) {
        // Tutaj możemy sparsować dane z AccuWeather
        // W tym przykładzie zakładamy, że temperatura z AccuWeather jest pierwszym elementem w obiekcie JSON
        // Możesz dostosować to do rzeczywistej struktury danych z AccuWeather
        // W tym miejscu jest tylko przykładowy kod
        double temperature = 0.0;
        try {
            // Pobierz temperaturę z pierwszego elementu JSON
            temperature = Double.parseDouble(accuweatherData.substring(accuweatherData.indexOf("Temperature") + 14, accuweatherData.indexOf("RealFeelTemperature") - 3));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return temperature;
    }
}
