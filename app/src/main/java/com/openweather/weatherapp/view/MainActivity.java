package com.openweather.weatherapp.view;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.openweather.weatherapp.ForeCastWeatherAdapter;
import com.openweather.weatherapp.R;
import com.openweather.weatherapp.model.Weather;
import com.openweather.weatherapp.model.WeatherDetails;


import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FetchWeatherTask.TaskCallbacks {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG_TASK_FRAGMENT = "task_fragment";


    private List<WeatherDetails> weatherList;
    private ListView foreCastListView;
    private LinearLayout mainContentLL;
    private Weather weatherDetails;
    private TextView maxTemp;
    private TextView minTemp;
    private TextView day;
    private TextView cityAndCountry;
    private TextView foreCast;
    private ProgressDialog genericProgressDialog;
    private FetchWeatherTask mTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViewById();
        createGenericProgressDialog();

        FragmentManager fm = getFragmentManager();
        mTaskFragment = (FetchWeatherTask) fm.findFragmentByTag(TAG_TASK_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (mTaskFragment == null) {
            mTaskFragment = new FetchWeatherTask();
            fm.beginTransaction().add(mTaskFragment, TAG_TASK_FRAGMENT).commit();
        } else {
            if (mTaskFragment.getWeatherData() != null)
                updateWeather(mTaskFragment.getWeatherData());
        }


        mainContentLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeatherActivity.class);
                intent.putExtra("WeatherDetails", weatherDetails);
                startActivity(intent);
            }
        });

    }

    private void getViewById() {
        getSupportActionBar().setElevation(0f);
        getSupportActionBar().setTitle("Weather");
        foreCastListView = (ListView) findViewById(R.id.listTempLV);
        mainContentLL = (LinearLayout) findViewById(R.id.mainContentLL);
        maxTemp = (TextView) findViewById(R.id.mainCityMaxTempTV);
        minTemp = (TextView) findViewById(R.id.mainCityMinTempTV);
        day = (TextView) findViewById(R.id.mainTodayDateTV);
        cityAndCountry = (TextView) findViewById(R.id.mainCityNameTV);
        foreCast = (TextView) findViewById(R.id.currentCityForecastTempTV);

    }

    private void updateWeather(Weather weather) {
        weatherDetails = weather;
        weatherList = weatherDetails.getWeatherDetailsList();
        ForeCastWeatherAdapter adapter = new ForeCastWeatherAdapter(this, weatherList);
        foreCastListView.setAdapter(adapter);

        maxTemp.setText((Math.round((Double.parseDouble(weatherList.get(0).getMaxTemp()) - 273.15))) + "°C");
        minTemp.setText((Math.round((Double.parseDouble(weatherList.get(0).getMinTemp()) - 273.15))) + "°C");
        cityAndCountry.setText(weatherDetails.getCity() + "," + weatherDetails.getCountry());
        foreCast.setText(weatherList.get(0).getForeCast());
        weatherDetails.setDay(DateFormat.getDateInstance().format(new Date()) + "");
        day.setText(DateFormat.getDateInstance().format(new Date()) + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_zipCode) {
            startActivity(new Intent(this, SearchByZipcodeActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createGenericProgressDialog() {
        genericProgressDialog = new ProgressDialog(this);
        genericProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        genericProgressDialog.setCancelable(false);
    }

    public void showProgressDialog() {
        if (genericProgressDialog != null) {
            if (!genericProgressDialog.isShowing()) {
                genericProgressDialog.setMessage("Retrieving Data ....");
                genericProgressDialog.show();
            }
        }

    }

    public void dismissProgressDialog() {
        if (genericProgressDialog != null) {
            if (genericProgressDialog.isShowing()) {
                genericProgressDialog.dismiss();
            }
        }
    }

    @Override
    public void onPreExecute() {
        showProgressDialog();
    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onPostExecute(Weather weather) {
        updateWeather(weather);
        mTaskFragment.setWeatherData(weather);
        dismissProgressDialog();
    }
}
