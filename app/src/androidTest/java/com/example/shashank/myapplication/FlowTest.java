package com.example.shashank.myapplication;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.shashank.myapplication.Activities.StartUp;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kotlin.jvm.JvmField;

import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FlowTest {
    public MockWebServer mockserver;

    @Rule @JvmField
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(
            StartUp.class);

    @Before
    public void setup()throws Exception{
        mockserver =new MockWebServer();
        mockserver.start();
        mockserver.url("/");

    }

    @Test
    public void AppFlowTest() {
        //go to SignupActivity by clicking on startup
        Espresso.onView(withId(R.id.signUp)).perform(click());

        //click on signup button without data
        Espresso.onView(withId(R.id.createaccount)).perform(click());

        //signup with only email or only passsword
        Espresso.onView(withId(R.id.emailId)).perform(ViewActions.clearText()).perform(ViewActions.typeText("sha@gmail.com"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.passwo)).perform(ViewActions.clearText()).perform(ViewActions.typeText(" "));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.createaccount)).perform(click());


        //signup with data
        Espresso.onView(withId(R.id.emailId)).perform(ViewActions.clearText()).perform(ViewActions.typeText("zxc@gmail.com"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.passwo)).perform(ViewActions.clearText()).perform(ViewActions.typeText("qwertyui"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.createaccount)).perform(click());

        //wait for the signup response(firebase auth)after response will automatically redirected to startup
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //go to SignInActivity via click on startup
        Espresso.onView(withId(R.id.signIn)).perform(click());

        //signin with no data
        Espresso.onView(withId(R.id.email)).perform(ViewActions.clearText()).perform(ViewActions.typeText(" "));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.password)).perform(ViewActions.clearText()).perform(ViewActions.typeText(" "));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.login)).perform(click());

        //signin with incorrect data(wrong credentials)
        Espresso.onView(withId(R.id.email)).perform(ViewActions.clearText()).perform(ViewActions.typeText("abc@gmail.com"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.password)).perform(ViewActions.clearText()).perform(ViewActions.typeText("qwerasdf"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.login)).perform(click());

        //signin with correct data
        Espresso.onView(withId(R.id.email)).perform(ViewActions.clearText()).perform(ViewActions.typeText("zxc@gmail.com"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.password)).perform(ViewActions.clearText()).perform(ViewActions.typeText("qwertyui"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.login)).perform(click());

        //wait for the login auth
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //mocking up json data from file
//        mockserver.enqueue(new MockResponse()
//                .setResponseCode(200)
//                .setBody("file.json"));

        //click on recycle view
        Espresso.onView(withId(R.id.rv_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        //go back
        Espresso.pressBack();

        //click for contacts
        Espresso.onView(withId(R.id.fabb)).perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //click on snackbar actions
//        Espresso.onView(allOf(withId(android.support.design.R.id.snackbar_action)))
//                .perform(click())
//        Espresso.pressBack();

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        Espresso.onView(withText("logout")).perform(click());


    }
    public void enterData(String data,int Id){
        Espresso.onView(withId(Id)).perform(ViewActions.clearText()).perform(ViewActions.typeText(data));
        Espresso.closeSoftKeyboard();
    }
}
