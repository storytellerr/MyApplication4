package com.example.shashank.myapplication;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

//    @Test
//    public void LoginActivityButtonFlow(){
//        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.clearText()).perform(ViewActions.typeText("myname@gmail.com"));
//        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.clearText()).perform(ViewActions.typeText("invalidpassword"));
//        Espresso.onView(ViewMatchers.withId(R.id.login)).perform(ViewActions.click());
//    }
}