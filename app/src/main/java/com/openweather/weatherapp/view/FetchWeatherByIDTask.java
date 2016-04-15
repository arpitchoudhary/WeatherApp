package com.openweather.weatherapp.view;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openweather.weatherapp.FetchWeatherByIDClient;
import com.openweather.weatherapp.R;
import com.openweather.weatherapp.model.ZipCodeDetails;

/**
 * Fragment created to fetch weather details,
 * And retain state during Activity (SearchByZipcodeActivity) recreation
 * and give the callback accordingly.
 */
public class FetchWeatherByIDTask extends Fragment {

    private ZipCodeDetails zipCodeData;

    public void setZipCodeData(ZipCodeDetails zipCodeData) {
        this.zipCodeData = zipCodeData;
    }

    public ZipCodeDetails getZipCodeData() {
        return zipCodeData;
    }

    /**
     * Callbacks define, which update the UI
     * based on the state
     */
    interface TaskCallbacks {
        // before making any operation in background
        void onPreExecute();

        //after fetching the data.
        void onPostExecute(ZipCodeDetails weather);
    }

    TaskCallbacks mCallbacks;
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
    class FetchWeatherDetails extends AsyncTask<Void, Void, ZipCodeDetails> {
        @Override
        protected void onPreExecute() {
            if (mCallbacks != null) {
                mCallbacks.onPreExecute();
            }
        }

        @Override
        protected ZipCodeDetails doInBackground(Void... params) {
            ZipCodeDetails weather = new FetchWeatherByIDClient().getFetchedWeather();
            return weather;
        }

        @Override
        protected void onPostExecute(ZipCodeDetails weather) {
            if (mCallbacks != null) {
                mCallbacks.onPostExecute(weather);
            }
        }
    }

}
