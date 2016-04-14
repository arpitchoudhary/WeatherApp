package com.openweather.weatherapp.view;

import android.app.Activity;
import android.os.SystemClock;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openweather.weatherapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Matcher;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressMenuKey;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private Activity activity;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
        }
    };

    @Before
    public void setUp() {
        SystemClock.sleep(2000);
        activity = mActivityRule.getActivity();
        Intents.init();
    }

    @After
    public void tearDown(){
        Intents.release();
    }


    @Test
    public void clickOnMainContentLayout_checkForDetailWeatherActivityLaunch() {
        onView(withId(R.id.headerContentLL)).perform(click());
        intended(hasComponent(DetailWeatherActivity.class.getName()));
    }
    @Test
    public void scroll_checkForDetailWeatherActivityLaunch() {
        onView(withId(R.id.listTempLV)).perform(swipeUp());
    }
    @Test
    public void press_ActionBarMenuKey() {
        onView(isRoot()).perform(pressMenuKey());
        onView(withText("By ZipCode")).perform(click());
        intended(hasComponent(SearchByZipcodeActivity.class.getName()));
    }

    @Test
    public void validate_ActionBarMenuItems() {
        onView(isRoot()).perform(pressMenuKey());
        onView(withText("Settings")).check(matches(isDisplayed()));
        onView(withText("By ZipCode")).check(matches(isDisplayed()));

    }

    @Test
    public void validate_ShareButtonMenuItems() {
        onView(isRoot()).perform(pressMenuKey());
        onView(withText("Messaging")).check(matches(isDisplayed()));
        onView(withText("Bluetooth")).check(matches(isDisplayed()));
        onView(withText("Hangouts")).check(matches(isDisplayed()));
        onView(withText("see all")).check(matches(isDisplayed()));
    }
}
