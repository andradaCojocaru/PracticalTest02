package ro.pub.cs.systems.eim.practicaltest02;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {
    @SerializedName("main")
    private Main main;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("weather")
    private List<Weather> weather;

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public class Main {
        @SerializedName("temp")
        private float temp;
        @SerializedName("pressure")
        private int pressure;
        @SerializedName("humidity")
        private int humidity;

        public float getTemp() {
            return temp;
        }

        public int getPressure() {
            return pressure;
        }

        public int getHumidity() {
            return humidity;
        }
    }

    public class Wind {
        @SerializedName("speed")
        private float speed;

        public float getSpeed() {
            return speed;
        }
    }

    public class Weather {
        @SerializedName("description")
        private String description;

        public String getDescription() {
            return description;
        }
    }
}
