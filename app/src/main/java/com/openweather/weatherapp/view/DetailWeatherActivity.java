package com.openweather.weatherapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.openweather.weatherapp.R;
import com.openweather.weatherapp.model.Weather;
import com.openweather.weatherapp.model.WeatherDetails;
import com.openweather.weatherapp.utility.Constants;

public class DetailWeatherActivity extends AppCompatActivity {

    private TextView detailDayTV;
    private TextView detailDateTV;
    private TextView detailHighTempTV;
    private TextView detailLowTempTV;
    private TextView detailForecastTV;
    private TextView detailHumidityTV;
    private TextView detailPressureTV;
    private TextView detailWindTV;
    private ImageView imageForecast;
    private Weather weatherDetails;
    private ShareActionProvider mShareActionProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_weather);
        getViewById();
        weatherDetails = (Weather) getIntent().getSerializableExtra("WeatherDetails");
        setScreenDetail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        mShareActionProvider = (android.support.v7.widget.ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if (weatherDetails != null) {
            mShareActionProvider.setShareIntent(createShareIntent());
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Api to create and setUp a shareIntent
     * @return Intent
     */
    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, weatherDetails.getCity() + " MaxTemp - " + weatherDetails.getWeatherDetailsList().get(0).getMaxTemp() + " MinTemp- "
                + weatherDetails.getWeatherDetailsList().get(0).getMinTemp() + " " + Constants.SHARE_TAG);
        return shareIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * populating the Ui
     */
    private void setScreenDetail() {
        detailDayTV.setText(weatherDetails.getDay());
        detailDateTV.setText(weatherDetails.getCity());
        WeatherDetails details = weatherDetails.getWeatherDetailsList().get(0);
        detailHighTempTV.setText((Math.round((Double.parseDouble(details.getMaxTemp()) - 273.15))) + "°C");
        detailLowTempTV.setText((Math.round((Double.parseDouble(details.getMinTemp()) - 273.15))) + "°C");
        detailForecastTV.setText(details.getForeCast());
        detailHumidityTV.setText("Humidity " + details.getHumidity() + "%");
        detailPressureTV.setText("Pressure " + details.getPressure());
        detailWindTV.setText("Sea Level " + details.getSeaLevel());
        String foreCast = details.getForeCast();
        if (foreCast.contains("rain") || foreCast.contains("Rain")) {
            imageForecast.setImageResource(R.drawable.img_rain);
        } else if (foreCast.contains("clouds") || foreCast.contains("Clouds")) {
            imageForecast.setImageResource(R.drawable.img_cloud);
        } else if (foreCast.contains("snow") || foreCast.contains("Snow")) {
            imageForecast.setImageResource(R.drawable.img_snow);
        } else {
            imageForecast.setImageResource(R.drawable.img_sun);
        }

    }

    /**
     * getting all the Id's from the xml.
     */
    private void getViewById() {
        getSupportActionBar().setTitle("Weather");
        detailDayTV = (TextView) findViewById(R.id.detailDayTV);
        detailDateTV = (TextView) findViewById(R.id.detailDateTV);
        detailHighTempTV = (TextView) findViewById(R.id.detailHighTempTV);
        detailLowTempTV = (TextView) findViewById(R.id.detailLowTempTV);
        detailForecastTV = (TextView) findViewById(R.id.detailForecastTV);
        detailHumidityTV = (TextView) findViewById(R.id.detailHumidityTV);
        detailPressureTV = (TextView) findViewById(R.id.detailPressureTV);
        detailWindTV = (TextView) findViewById(R.id.detailSeaTV);
        imageForecast = (ImageView) findViewById(R.id.detail_iconIV);
    }
}
