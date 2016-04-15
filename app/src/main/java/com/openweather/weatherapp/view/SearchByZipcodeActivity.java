package com.openweather.weatherapp.view;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.openweather.weatherapp.R;
import com.openweather.weatherapp.model.Weather;
import com.openweather.weatherapp.model.ZipCodeDetails;

public class SearchByZipcodeActivity extends AppCompatActivity implements FetchWeatherByIDTask.TaskCallbacks {

    private TextView city;
    private TextView minTemp;
    private TextView maxTemp;
    private TextView forecast;
    private TextView humid;
    private Button enterZip;
    private EditText etZip;
    private ProgressDialog genericProgressDialog;
    private FetchWeatherByIDTask fetchWeatherByIDTask;
    private static final String TAG_TASK_FRAGMENT = "FetchWeatherByIDTask";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_city);
        getViewById();
        createGenericProgressDialog();

        FragmentManager fm = getFragmentManager();
        fetchWeatherByIDTask = (FetchWeatherByIDTask) fm.findFragmentByTag(TAG_TASK_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (fetchWeatherByIDTask == null) {
            fetchWeatherByIDTask = new FetchWeatherByIDTask();
            fm.beginTransaction().add(fetchWeatherByIDTask, TAG_TASK_FRAGMENT).commit();
        } else {
            if (fetchWeatherByIDTask != null) {
                updateScreen(fetchWeatherByIDTask.getZipCodeData());
            }
        }
    }

    private void getViewById() {
        city = (TextView) findViewById(R.id.zipCode_CityTV);
        minTemp = (TextView) findViewById(R.id.zipCode_CityTV);
        maxTemp = (TextView) findViewById(R.id.zipCode_CityTV);
        forecast = (TextView) findViewById(R.id.zipCode_CityTV);
        humid = (TextView) findViewById(R.id.zipHumidityTV);
        enterZip = (Button) findViewById(R.id.zipCode_EnterButton);
        etZip = (EditText) findViewById(R.id.zipcode_enterZipEt);
    }

    /**
     * Api to create progress dialog
     */
    private void createGenericProgressDialog() {
        genericProgressDialog = new ProgressDialog(this);
        genericProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        genericProgressDialog.setCancelable(false);
    }

    /**
     * Api to show progress dialog, while fetching weather details.
     */
    public void showProgressDialog() {
        if (genericProgressDialog != null) {
            if (!genericProgressDialog.isShowing()) {
                genericProgressDialog.setMessage("Retrieving Data ....");
                genericProgressDialog.show();
            }
        }

    }

    /**
     * Api to dismiss progress dialog, if it is showing.
     */
    public void dismissProgressDialog() {
        if (genericProgressDialog != null) {
            if (genericProgressDialog.isShowing()) {
                genericProgressDialog.dismiss();
            }
        }
    }

    /**
     * Callbacks method called by FetchWeatherTask
     * to update the UI according.
     */
    @Override
    public void onPreExecute() {
        showProgressDialog();
    }

    @Override
    public void onPostExecute(ZipCodeDetails weather) {
        updateScreen(weather);
        fetchWeatherByIDTask.setZipCodeData(weather);
        dismissProgressDialog();
    }

    /**
     * Api to populate the screen
     *
     * @param weather
     */
    private void updateScreen(ZipCodeDetails weather) {
        city.setText(weather.getCity());
        minTemp.setText("Min Temp " + weather.getMinTemp() + "°C");
        maxTemp.setText("Max Temp " + weather.getMaxTemp() + "°C");
        forecast.setText(weather.getForecast());
        humid.setText("Humidity " + weather.getHumid());
    }
}
