package ro.pub.cs.systems.eim.practicaltest02;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://api.openweathermap.org/";

    private RetrofitClient() {
        // Private constructor to prevent instantiation
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static WeatherService getWeatherService() {
        return getClient().create(WeatherService.class);
    }
}
