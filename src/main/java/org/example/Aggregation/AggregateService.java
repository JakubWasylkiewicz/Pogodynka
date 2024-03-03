package org.example.Aggregation;

import org.example.WeatherServices.AccuweatherService;
import org.example.WeatherServices.OpenWeatherService;
import org.example.WeatherData.WeatherData;

import java.io.IOException;

public class AggregateService {
    private final AccuweatherService accuWeatherService;
    private final OpenWeatherService openWeatherService;

    public AggregateService(AccuweatherService accuWeatherService, OpenWeatherService openWeatherService) {
        this.accuWeatherService = accuWeatherService;
        this.openWeatherService = openWeatherService;
    }

    public WeatherData getAggregatedWeather(double latitude, double longitude) throws IOException {
        WeatherData getWeatherDataFromAccuweather = accuWeatherService.getWeatherData(latitude, longitude);
        WeatherData getWeatherDataFromOpenWeather = openWeatherService.getWeatherData(latitude, longitude);

        double temperature = calculateAverageValue(getWeatherDataFromAccuweather.getTemperature(), getWeatherDataFromOpenWeather.getTemperature() - 273.15);
        double pressure = calculateAverageValue(getWeatherDataFromAccuweather.getPressure(), getWeatherDataFromOpenWeather.getPressure());
        double humidity = calculateAverageValue(getWeatherDataFromAccuweather.getHumidity(), getWeatherDataFromOpenWeather.getHumidity());
        double windSpeed = calculateAverageValue(getWeatherDataFromAccuweather.getWindSpeed(), getWeatherDataFromOpenWeather.getWindSpeed());
        double windDirection = calculateAverageValue(getWeatherDataFromAccuweather.getWindDirection(), getWeatherDataFromOpenWeather.getWindDirection());

        WeatherData result = new WeatherData(temperature, pressure, humidity, windSpeed, windDirection);
        return result;
    }

    private static Double calculateAverageValue(Double value1, Double value2) {
        if (value1 == null && value2 == null) {
            return null;
        } else if (value1 == null) {
            return value2;
        } else if (value2 == null) {
            return value1;
        } else {
            return (value1 + value2) / 2;
        }
    }
}