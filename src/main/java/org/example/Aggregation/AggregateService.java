package org.example.Aggregation;

import org.example.GeoCoding.AccuweatherService;
import org.example.GeoCoding.OpenWeatherService;

import java.io.IOException;

public class AggregateService {

    private final AccuweatherService accuweatherService;
    private final OpenWeatherService weatherService;

    public AggregateService() {
        this.accuweatherService = new AccuweatherService();
        this.weatherService = new OpenWeatherService();
    }

//    public OpenWeatherService.WeatherData getAggregatedWeather(double latitude, double longitude) throws IOException {
//            String cityKey = accuweatherService.getCityKey(latitude, longitude);
//accuweatherService.getWeatherData(cityKey);
//
//            OpenWeatherService.WeatherData weatherDataFromWeatherService = weatherService.getWeatherData(latitude, longitude);
//    }





    private static double calculateAverageValue(double value1, double value2){
        return (value1+value2)/2;
    }
}