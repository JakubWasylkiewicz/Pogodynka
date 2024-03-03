package org.example.WeatherInterface;

import org.example.WeatherData.WeatherData;

import java.io.IOException;

public interface WeatherInterface {

    WeatherData getWeatherData(double latitude, double longitude) throws IOException;
}
