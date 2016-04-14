package com.openweather.weatherapp.utility;


import android.test.AndroidTestCase;

import junit.framework.Assert;

import org.junit.Test;

public class UtilityTest extends AndroidTestCase {

    @Test
    public void getPreferredLocation_returnReturnLocation() {
        String expected = "bangalore";
        String result = Utility.getPreferredLocation(getContext());
        Assert.assertEquals(expected, result);
    }
}
