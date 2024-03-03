package org.example.WeatherData;

public class WeatherData {
    private final double temperature;
    private final double pressure;
    private final double humidity;
    private final double windSpeed;
    private final double windDirection;

    public double getTemperature() {
        return temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public WeatherData(double temperature, double pressure, double humidity, double windSpeed, double windDirection) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }
}