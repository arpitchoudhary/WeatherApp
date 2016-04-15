package com.openweather.weatherapp.view;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openweather.weatherapp.FetchWeatherHttpClient;
import com.openweather.weatherapp.R;
import com.openweather.weatherapp.model.Weather;

/**
 * Fragment created to fetch weather details,
 * And retain state during Activity (MainActivity) recreation
 * and give the callback accordingly.
 */
public class FetchWeatherTask extends Fragment {

    private Weather weatherData;

    public void setWeatherData(Weather weatherData) {
        this.weatherData = weatherData;
    }

    public Weather getWeatherData() {
        return weatherData;
    }

    /**
     * Callbacks define, which update the UI
     * based on the state
     */
    interface TaskCallbacks {
        // before making any operation in background
        void onPreExecute();

        // if call to fetch the data is canceled.
        void onCancelled();

        //after fetching the data.
        void onPostExecute(Weather weather);
    }

    private TaskCallbacks mCallbacks;
    private FetchWeatherDetails fetchWeatherDetails;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (TaskCallbacks) activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);

        // Create and execute the background task.
        fetchWeatherDetails = new FetchWeatherDetails();
        fetchWeatherDetails.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    /**
     * Set the callback to null so we don't accidentally leak the
     * Activity instance.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    /**
     * FetchWeatherDetails AsyncTask created to fetch the Weather Details.
     */
    class FetchWeatherDetails extends AsyncTask<Void, Void, Weather> {

        @Override
        protected void onPreExecute() {
            if (mCallbacks != null) {
                mCallbacks.onPreExecute();
            }
        }

        @Override
        protected Weather doInBackground(Void... params) {
            Weather weather = new FetchWeatherHttpClient().getFetchedWeather();
            return weather;
        }


        @Override
        protected void onCancelled() {
            if (mCallbacks != null) {
                mCallbacks.onCancelled();
            }
        }


        @Override
        protected void onPostExecute(Weather weather) {
            if (mCallbacks != null) {
                mCallbacks.onPostExecute(weather);
            }
        }
    }

}
