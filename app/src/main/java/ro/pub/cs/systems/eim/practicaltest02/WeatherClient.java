package ro.pub.cs.systems.eim.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WeatherClient {
    private String serverAddress;
    private int serverPort;

    public WeatherClient(String address, int port) {
        this.serverAddress = address;
        this.serverPort = port;
    }

    public String requestWeather(String city, String info) {
        StringBuilder response = new StringBuilder();
        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println(city);
            out.println(info);

            String line;
            while ((line = in.readLine()) != null) {
                response.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}
