package ru.geekbrains.A1L1_Intro;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherDataLoader {


    private static final String OPEN_WEATHER_API_KEY = "80e875f05e592979816e246496f61f8e";
    private static final String OPEN_WEATHER_API_URL =
            "https://api.openweathermap.org/data/2.5/forecast?id=%d&units=metric";
    private static final String KEY = "x-api-key";
    private static final String RESPONSE = "cod";
    private static final int ALL_GOOD = 200;

    public static JSONObject getJSONData(Integer id) {
        try {
            URL url = new URL(String.format(OPEN_WEATHER_API_URL, id));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty(KEY, OPEN_WEATHER_API_KEY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder rawData = new StringBuilder(1024);
            String tempVariable;

            while ((tempVariable = reader.readLine()) != null)
                rawData.append(tempVariable).append("\n");

            reader.close();

            JSONObject jsonObject = new JSONObject(rawData.toString());

            if (jsonObject.getInt(RESPONSE) != ALL_GOOD) {
                return null;
            }

            return jsonObject;
        } catch (IOException | JSONException e) {
            return null;
        }
    }
}
