package com.example.shashank.myapplication;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.shashank.myapplication.Activities.SignInActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kotlin.jvm.JvmField;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SignUpActivityTestCase1 {
    @Rule @JvmField
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(
            SignInActivity.class);



    @Test
    public void SignUpWithNoData(){
//        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.clearText()).perform(ViewActions.typeText(""));
//        Espresso.closeSoftKeyboard();
//        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.clearText()).perform(ViewActions.typeText(""));
//        Espresso.closeSoftKeyboard();
//        Espresso.onView(ViewMatchers.withId(R.id.login)).perform(ViewActions.click());

//        Espresso.onView(withId(R.id.email)).perform(ViewActions.clearText()).perform(ViewActions.typeText("sha@gmail.com"));
//        Espresso.closeSoftKeyboard();
//        Espresso.onView(withId(R.id.password)).perform(ViewActions.clearText()).perform(ViewActions.typeText(""));
//        Espresso.closeSoftKeyboard();
//        Espresso.onView(withId(R.id.login)).perform(click());
//
//        Espresso.onView(withId(R.id.email)).perform(ViewActions.clearText()).perform(ViewActions.typeText("sha@gmail.com"));
//        Espresso.closeSoftKeyboard();
//        Espresso.onView(withId(R.id.password)).perform(ViewActions.clearText()).perform(ViewActions.typeText(""));
//        Espresso.closeSoftKeyboard();
//        Espresso.onView(withId(R.id.login)).perform(click());
//
//        Espresso.onView(withId(R.id.email)).perform(ViewActions.clearText()).perform(ViewActions.typeText(""));
//        Espresso.closeSoftKeyboard();
//        Espresso.onView(withId(R.id.password)).perform(ViewActions.clearText()).perform(ViewActions.typeText("password"));
//        Espresso.closeSoftKeyboard();
//        Espresso.onView(withId(R.id.login)).perform(click());

        Espresso.onView(withId(R.id.email)).perform(ViewActions.clearText()).perform(ViewActions.typeText("s@gmail.com"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.password)).perform(ViewActions.clearText()).perform(ViewActions.typeText("asdf"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.login)).perform(click());
    }

}
