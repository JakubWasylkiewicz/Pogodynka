package org.example.GeoCoding;

import java.io.IOException;

public class AggregateService {

    private AccuweatherService accuweatherService;
    private WeatherService weatherService;

    public AggregateService() {
        this.accuweatherService = new AccuweatherService();
        this.weatherService = new WeatherService();
    }

    public WeatherService.WeatherData aggregateWeatherData(double latitude, double longitude) throws IOException {
        String accuweatherCityKey = accuweatherService.getCityKey(latitude, longitude);
        WeatherService.WeatherData openWeatherData = weatherService.getWeatherData(latitude, longitude);

        // Narazie jest tak że jak nie uda się pobrać danych z jedno lub drugiego to da null
        if (accuweatherCityKey == null || openWeatherData == null) {
            return null;
        }

        String accuweatherData = accuweatherService.getWeatherData(accuweatherCityKey);
        double accuweatherTemperature = parseAccuweatherTemperature(accuweatherData);


        double averageTemperature = calculateAverageTemperature(accuweatherTemperature, openWeatherData.getTemperature());


        return new WeatherService.WeatherData(
                averageTemperature,
                openWeatherData.getPressure(),
                openWeatherData.getHumidity(),
                openWeatherData.getWindSpeed(),
                openWeatherData.getWindDirection()
        );
    }

    private double parseAccuweatherTemperature(String accuweatherData) {

        double temperature = 0.0;
        try {

            temperature = Double.parseDouble(accuweatherData.substring(accuweatherData.indexOf("Temperature") + 14, accuweatherData.indexOf("RealFeelTemperature") - 3));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return temperature;
    }

    private double calculateAverageTemperature(double temperature1, double temperature2) {

        return (temperature1 + temperature2) / 2;
    }
}
