package ro.pub.cs.systems.eim.practicaltest02;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("data/2.5/weather")
    Call<WeatherResponse> getWeather(@Query("q") String city, @Query("appid") String apiKey);
}
