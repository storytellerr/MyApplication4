package com.example.shashank.myapplication;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kotlin.jvm.JvmField;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SignInActivityTestCase extends Activity {
    @Rule @JvmField
    public ActivityTestRule mActivity = new ActivityTestRule<>(
            SignInActivityTestCase.class);

    @Test
    public void SignUpWithNoData(){
        Espresso.onView(ViewMatchers.withId(R.id.emailId)).perform(ViewActions.clearText()).perform(ViewActions.typeText(""));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.passwo)).perform(ViewActions.clearText()).perform(ViewActions.typeText(""));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.fabb)).perform(ViewActions.click());
    }
    @Test
    public void SignUpWithEmailOnly(){
        Espresso.onView(ViewMatchers.withId(R.id.emailId)).perform(ViewActions.clearText()).perform(ViewActions.typeText("sha@gmail.com"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.passwo)).perform(ViewActions.clearText()).perform(ViewActions.typeText(""));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.fabb)).perform(ViewActions.click());
    }
    @Test
    public void SignUpWithPasswordOnly(){
        Espresso.onView(ViewMatchers.withId(R.id.emailId)).perform(ViewActions.clearText()).perform(ViewActions.typeText(""));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.passwo)).perform(ViewActions.clearText()).perform(ViewActions.typeText("password"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.fabb)).perform(ViewActions.click());
    }
    @Test
    public void SignUpWithIncorrectData(){
        Espresso.onView(ViewMatchers.withId(R.id.emailId)).perform(ViewActions.clearText()).perform(ViewActions.typeText("s@gmail.com"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.passwo)).perform(ViewActions.clearText()).perform(ViewActions.typeText("asdf"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.fabb)).perform(ViewActions.click());
    }

}
