package ro.pub.cs.systems.eim.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import retrofit2.Call;
import retrofit2.Response;

public class WeatherServer extends Thread {
    private ServerSocket serverSocket;
    private int port;
    private WeatherService weatherService;

    public WeatherServer(int port, WeatherService weatherService) {
        this.port = port;
        this.weatherService = weatherService;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket, weatherService).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private WeatherService weatherService;

        public ClientHandler(Socket socket, WeatherService weatherService) {
            this.clientSocket = socket;
            this.weatherService = weatherService;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                String city = in.readLine();
                String info = in.readLine();

                WeatherResponse response = WeatherDataCache.getInstance().getWeather(city);
                if (response == null) {
                    Call<WeatherResponse> call = weatherService.getWeather(city, "e03c3b32cfb5a6f7069f2ef29237d87e");
                    Response<WeatherResponse> apiResponse = call.execute();
                    if (apiResponse.isSuccessful() && apiResponse.body() != null) {
                        response = apiResponse.body();
                        WeatherDataCache.getInstance().putWeather(city, response);
                    }
                }

                if (response != null) {
                    if ("all".equalsIgnoreCase(info)) {
                        out.println("Temp: " + response.getMain().getTemp());
                        out.println("Pressure: " + response.getMain().getPressure());
                        out.println("Humidity: " + response.getMain().getHumidity());
                        out.println("Wind Speed: " + response.getWind().getSpeed());
                        out.println("Description: " + response.getWeather().get(0).getDescription());
                    } else if ("temp".equalsIgnoreCase(info)) {
                        out.println("Temp: " + response.getMain().getTemp());
                    } else if ("pressure".equalsIgnoreCase(info)) {
                        out.println("Pressure: " + response.getMain().getPressure());
                    } else if ("humidity".equalsIgnoreCase(info)) {
                        out.println("Humidity: " + response.getMain().getHumidity());
                    } else if ("wind".equalsIgnoreCase(info)) {
                        out.println("Wind Speed: " + response.getWind().getSpeed());
                    } else if ("description".equalsIgnoreCase(info)) {
                        out.println("Description: " + response.getWeather().get(0).getDescription());
                    }
                } else {
                    out.println("Error retrieving weather data.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
