package com.openweather.weatherapp.view;


import android.os.Bundle;

import com.openweather.weatherapp.FetchWeatherByIDClient;
import com.openweather.weatherapp.model.ZipCodeDetails;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.never;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Bundle.class})
public class FetchWeatherByIDTaskTest {

    ZipCodeDetails mockedWeather;
    FetchWeatherByIDTask task;
    FetchWeatherByIDTask.TaskCallbacks mockedCallbacks;
    FetchWeatherByIDTask.FetchWeatherDetails mockedDetails;

    @Before
    public void setUp() {
        mockedWeather = Mockito.mock(ZipCodeDetails.class);
        mockedCallbacks = Mockito.mock(FetchWeatherByIDTask.TaskCallbacks.class);
        mockedDetails = Mockito.mock(FetchWeatherByIDTask.FetchWeatherDetails.class);
        task = new FetchWeatherByIDTask();
    }

    @After
    public void tearDown() {
        task = null;
    }

    @Test
    public void setZipCode_wasCalled() {
        task.setZipCodeData(mockedWeather);
    }

    @Test
    public void getZipCode_wasCalled() {
        task.setZipCodeData(mockedWeather);
        Assert.assertNotNull(task.getZipCodeData());
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
        Mockito.verify(mockedCallbacks, never()).onPreExecute();
    }

    @Test
    public void doInBackground_wasCalled() {
        FetchWeatherByIDClient mocked = Mockito.mock(FetchWeatherByIDClient.class);
        Mockito.when(mocked.getFetchedWeather()).thenReturn(mockedWeather);
        mockedDetails.doInBackground(null);
    }

    @Test
    public void onPostExecute_wasNotCalledWithNullCallback() {
        mockedDetails.onPreExecute();
        Mockito.verify(mockedCallbacks, never()).onPostExecute(mockedWeather);
    }

}
