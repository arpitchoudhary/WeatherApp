//package com.openweather.weatherapp.view;
//
//import android.app.Activity;
//import android.os.SystemClock;
//import android.support.test.espresso.intent.Intents;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//
//import com.openweather.weatherapp.R;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.action.ViewActions.pressMenuKey;
//import static android.support.test.espresso.action.ViewActions.replaceText;
//import static android.support.test.espresso.action.ViewActions.typeText;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
//
///**
// * Created by achs9 on 4/14/16.
// */
//@RunWith(AndroidJUnit4.class)
//public class DetailWeatherActivityTest {
//
//    private Activity activity;
//
//    @Rule
//    public ActivityTestRule<DetailWeatherActivity> mActivityRule = new ActivityTestRule(DetailWeatherActivity.class) {
//        @Override
//        protected void beforeActivityLaunched() {
//            super.beforeActivityLaunched();
//        }
//    };
//
//    @Before
//    public void setUp() {
//        SystemClock.sleep(2000);
//        activity = mActivityRule.getActivity();
//        Intents.init();
//    }
//
//    @After
//    public void tearDown(){
//        Intents.release();
//    }
//
//    @Test
//    public void validate_ShareButtonMenuItems() {
//        onView(isRoot()).perform(pressMenuKey());
//        onView(withText("Messaging")).check(matches(isDisplayed()));
//        onView(withText("Bluetooth")).check(matches(isDisplayed()));
//        onView(withText("Hangouts")).check(matches(isDisplayed()));
//        onView(withText("see all")).check(matches(isDisplayed()));
//    }
//}
