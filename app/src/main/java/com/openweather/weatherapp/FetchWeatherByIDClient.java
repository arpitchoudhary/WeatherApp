package com.openweather.weatherapp;


import android.net.Uri;
import android.util.Log;

import com.openweather.weatherapp.model.Weather;
import com.openweather.weatherapp.model.WeatherDetails;
import com.openweather.weatherapp.model.ZipCodeDetails;
import com.openweather.weatherapp.utility.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HttpClient to make the handle the network calls
 */
public class FetchWeatherByIDClient {

    private static final String TAG = "FetchWeatherByIDClient";

    public ZipCodeDetails getFetchedWeather() {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // json response as a String
        String forecastJsonStr = null;
        StringBuilder result = null;
        ZipCodeDetails weather = null;
        try {

            final String QUERY_PARAM = "zip";
            final String APPID_PARAM = "APPID";

            Uri builtUri = Uri.parse(Constants.ZIPCODE_FORECAST_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, "94040")
                    .appendQueryParameter(APPID_PARAM, Constants.MAINTAINENCE_KEY)
                    .build();

            URL url = new URL(builtUri.toString());
            Log.d(TAG, "URL ->" + builtUri.toString());
            //create the request and open the connection.
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //read the input stream
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            forecastJsonStr = buffer.toString();
            Log.d(TAG,forecastJsonStr);
            weather = getWeatherDataFromJson(forecastJsonStr);
        } catch (IOException e) {
            Log.e(TAG, "Error ", e);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
        return weather;
    }

    /**
     * private Api to parse the Json String and populate the appropriate modal.
     * @param forecastJsonStr
     * @return ZipCodeDetails
     * @throws JSONException
     */
    private ZipCodeDetails getWeatherDataFromJson(String forecastJsonStr) throws JSONException {
        final String NODE_CITY_NAME = "name";
        final String NODE_MAX = "temp_max";
        final String NODE_MIN = "temp_min";
        final String NODE_HUMIDITY = "humidity";

        final String NODE_WEATHER = "weather";
        final String NODE_DESCRIPTION = "main";


        ZipCodeDetails weather = null;
        try {

            weather = new ZipCodeDetails();

            JSONObject forecastJson = new JSONObject(forecastJsonStr);

            String cityName = forecastJson.getString(NODE_CITY_NAME);
            weather.setCity(cityName);

            String humidity;
            String high;
            String low;
            String description = "Clear";

            JSONObject main = forecastJson.getJSONObject(NODE_DESCRIPTION);
            high = main.getString(NODE_MAX);
            low = main.getString(NODE_MIN);
            humidity = main.getString(NODE_HUMIDITY);

            JSONArray forecastDescArray = forecastJson.getJSONArray(NODE_WEATHER);

            for (int j = 0; j < forecastDescArray.length(); j++) {
                description = forecastDescArray.getJSONObject(j).getString(NODE_DESCRIPTION);
            }
            weather.setHumid(humidity);
            weather.setForecast(description);
            weather.setMaxTemp(high);
            weather.setMinTemp(low);
            Log.d(TAG, "Weather fetched" + weather);
            return weather;
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return weather;
    }
}
