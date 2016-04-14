package com.openweather.weatherapp;

import android.net.Uri;
import android.util.Log;

import com.openweather.weatherapp.model.Weather;
import com.openweather.weatherapp.model.WeatherDetails;
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
import java.util.ArrayList;
import java.util.List;


public class FetchWeatherHttpClient {


    private static final String TAG = "FetchWeatherHttpClient";

    public Weather getFetchedWeather() {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // json response as a String
        String forecastJsonStr = null;
        StringBuilder result = null;
        Weather weather = null;
        try {

            final String QUERY_PARAM = "q";
            final String APPID_PARAM = "APPID";
            String defaultLocation = "London";

            Uri builtUri = Uri.parse(Constants.FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, defaultLocation)
                    .appendQueryParameter(APPID_PARAM, Constants.MAINTAINENCE_KEY)
                    .build();

            URL url = new URL(builtUri.toString());
            Log.d(TAG, "URL ->" + builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

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
            weather = getWeatherDataFromJson(forecastJsonStr);
        } catch (IOException e) {
            Log.e(TAG, "Error ", e);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
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

    private Weather getWeatherDataFromJson(String forecastJsonString)
            throws JSONException {


        String NODE_CITY = "city";
        String NODE_CITY_NAME = "name";
        String NODE_COUNTRY = "country";

        //list of forecast
        final String NODE_LIST = "list";

        final String NODE_PRESSURE = "pressure";
        final String NODE_HUMIDITY = "humidity";
        final String NODE_SEA_LEVEL = "sea_level";

        final String NODE_MAX = "temp_max";
        final String NODE_MIN = "temp_min";

        final String NODE_WEATHER = "weather";
        final String NODE_DESCRIPTION = "main";

        final String NODE_TIME = "dt_txt";


        Weather weather = null;
        try {

            weather = new Weather();

            JSONObject forecastJson = new JSONObject(forecastJsonString);

            JSONObject cityJson = forecastJson.getJSONObject(NODE_CITY);
            String cityName = cityJson.getString(NODE_CITY_NAME);
            String countryName = cityJson.getString(NODE_COUNTRY);

            weather.setCity(cityName);
            weather.setCountry(countryName);

            JSONArray weatherArray = forecastJson.getJSONArray(NODE_LIST);

            List<WeatherDetails> detailsList = new ArrayList<>();

            for (int i = 0; i < weatherArray.length(); i++) {

                String pressure;
                String humidity;
                String seaLevel;
                String high;
                String low;
                String description = "Clear";
                String time = "";


                time = weatherArray.getJSONObject(i).getString(NODE_TIME);
                JSONObject dayForecast = weatherArray.getJSONObject(i).getJSONObject(NODE_DESCRIPTION);
                pressure = dayForecast.getString(NODE_PRESSURE);
                humidity = dayForecast.getString(NODE_HUMIDITY);
                seaLevel = dayForecast.getString(NODE_SEA_LEVEL);
                high = dayForecast.getString(NODE_MAX);
                low = dayForecast.getString(NODE_MIN);

                JSONArray forecastDescArray = weatherArray.getJSONObject(i).getJSONArray(NODE_WEATHER);

                for (int j = 0; j < forecastDescArray.length(); j++) {
                    description = forecastDescArray.getJSONObject(j).getString(NODE_DESCRIPTION);
                }

                WeatherDetails weatherDetails = new WeatherDetails();
                weatherDetails.setMinTemp(low);
                weatherDetails.setForeCast(description);
                weatherDetails.setHumidity(humidity);
                weatherDetails.setMaxTemp(high);
                weatherDetails.setPressure(pressure);
                weatherDetails.setSeaLevel(seaLevel);
                weatherDetails.setTime(time);
                detailsList.add(weatherDetails);
            }
            weather.setWeatherDetailsList(detailsList);
            Log.d(TAG, "Weather fetched" + weather);
            return weather;

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return weather;
    }
}
