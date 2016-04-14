package com.openweather.weatherapp;


import android.net.Uri;

import com.openweather.weatherapp.model.Weather;
import com.openweather.weatherapp.utility.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.Builder.class})
public class FetchWeatherByIDClientTest {

    HttpURLConnection mockedConnection;
    BufferedReader mockedReder;
    Weather mockedWeather;
    URL mockedURL;
    Uri mockedUri;

    @Before
    public void setUp(){
        mockedConnection = Mockito.mock(HttpURLConnection.class);
        mockedReder = Mockito.mock(BufferedReader.class);
        mockedWeather = Mockito.mock(Weather.class);
        mockedURL = PowerMockito.mock(URL.class);
        mockedUri = PowerMockito.mock(Uri.class);
    }

    @Test
    public void getWeather_returnWeather() throws Exception {
        Uri.Builder mockBuilder= Mockito.mock(Uri.Builder.class);
        PowerMockito.doReturn(mockBuilder).when(mockedUri).parse(Constants.FORECAST_BASE_URL);
        Mockito.doReturn(mockBuilder).when((mockedUri).parse(Constants.FORECAST_BASE_URL)).buildUpon();
        Mockito.doReturn(mockBuilder).when(mockBuilder).build();
        PowerMockito.whenNew(Uri.Builder.class).withAnyArguments().thenReturn(mockBuilder);
    }
}
