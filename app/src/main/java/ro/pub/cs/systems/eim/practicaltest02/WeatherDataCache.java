package ro.pub.cs.systems.eim.practicaltest02;

import java.util.HashMap;
import java.util.Map;

public class WeatherDataCache {
    private static WeatherDataCache instance;
    private Map<String, WeatherResponse> weatherData;

    private WeatherDataCache() {
        weatherData = new HashMap<>();
    }

    public static synchronized WeatherDataCache getInstance() {
        if (instance == null) {
            instance = new WeatherDataCache();
        }
        return instance;
    }

    public void putWeather(String city, WeatherResponse weatherResponse) {
        weatherData.put(city, weatherResponse);
    }

    public WeatherResponse getWeather(String city) {
        return weatherData.get(city);
    }
}
