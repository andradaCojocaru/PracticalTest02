package ro.pub.cs.systems.eim.practicaltest02;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextServerPort;
    private EditText editTextClientPort;
    private EditText editTextCity;
    private EditText editTextInfo;
    private TextView textViewResponse;
    private WeatherService weatherService;
    private int serverPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextServerPort = findViewById(R.id.editTextServerPort);
        editTextClientPort = findViewById(R.id.editTextClientPort);
        editTextCity = findViewById(R.id.editTextCity);
        editTextInfo = findViewById(R.id.editTextInfo);
        textViewResponse = findViewById(R.id.textViewResponse);
        Button buttonStartServer = findViewById(R.id.buttonStartServer);
        Button buttonSend = findViewById(R.id.buttonSend);

        // Initialize the WeatherService
        weatherService = RetrofitClient.getWeatherService();

        buttonStartServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String portStr = editTextServerPort.getText().toString();
                serverPort = Integer.parseInt(portStr);

                // Start the server
                new WeatherServer(serverPort, weatherService).start();
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String portStr = editTextClientPort.getText().toString();
                int clientPort = Integer.parseInt(portStr);
                String city = editTextCity.getText().toString();
                String info = editTextInfo.getText().toString();

                // Send request to the server
                new WeatherRequestTask(clientPort).execute(city, info);
            }
        });
    }

    private class WeatherRequestTask extends AsyncTask<String, Void, String> {
        private int clientPort;

        public WeatherRequestTask(int clientPort) {
            this.clientPort = clientPort;
        }

        @Override
        protected String doInBackground(String... params) {
            String city = params[0];
            String info = params[1];
            WeatherClient client = new WeatherClient("localhost", clientPort);
            return client.requestWeather(city, info);
        }

        @Override
        protected void onPostExecute(String result) {
            textViewResponse.setText(result);
        }
    }
}
