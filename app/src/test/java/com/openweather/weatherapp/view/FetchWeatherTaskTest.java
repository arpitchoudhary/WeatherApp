package com.openweather.weatherapp.view;

import android.os.Bundle;

import com.openweather.weatherapp.FetchWeatherHttpClient;
import com.openweather.weatherapp.model.Weather;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import static org.mockito.Mockito.never;

public class FetchWeatherTaskTest {

    Weather mockedWeather;
    FetchWeatherTask.TaskCallbacks callbacks;
    FetchWeatherTask.FetchWeatherDetails mockedDetails;
    FetchWeatherTask task;

    @Before
    public void setUp() {
        mockedWeather = Mockito.mock(Weather.class);
        callbacks = Mockito.mock(FetchWeatherTask.TaskCallbacks.class);
        mockedDetails = Mockito.mock(FetchWeatherTask.FetchWeatherDetails.class);
        task = new FetchWeatherTask();
    }

    @After
    public void tearDown() {
        task = null;
    }

    @Test
    public void setWeather_wasCalled() {
        task.setWeatherData(mockedWeather);
    }

    @Test
    public void getWeather_wasCalled() {
        task.setWeatherData(mockedWeather);
        Assert.assertNotNull(task.getWeatherData());
    }

    @Test
    public void onCreate_fetchWeatherAsynTaskCalled() {
        Bundle mockedBundle = PowerMockito.mock(Bundle.class);
        task.onCreate(mockedBundle);
    }

    @Test
    public void onPreExecute_wasNotCalledWithNullCallback() {
        mockedDetails.onPreExecute();
        FetchWeatherByIDTask.TaskCallbacks callbacks = Mockito.mock(FetchWeatherByIDTask.TaskCallbacks.class);
        Mockito.verify(callbacks, never()).onPreExecute();
    }

    @Test
    public void doInBackground_wasCalled() {
        FetchWeatherHttpClient mocked = Mockito.mock(FetchWeatherHttpClient.class);
        Mockito.when(mocked.getFetchedWeather()).thenReturn(mockedWeather);
        mockedDetails.doInBackground(null);
    }

    @Test
    public void onPostExecute_wasNotCalledWithNullCallback() {
        mockedDetails.onPreExecute();
        Mockito.verify(callbacks, never()).onPostExecute(mockedWeather);
    }


}
