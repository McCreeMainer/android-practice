package com.example.navigation

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.navigation.task3.Activity1
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class Task3EspressoTest {

    private fun testActivity1Existence() {
        onView(withId(R.id.activity1_app_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.activity1_layout)).check(matches(isDisplayed()))

        onView(withId(R.id.activity2_app_bar)).check(doesNotExist())
        onView(withId(R.id.activity2_layout)).check(doesNotExist())

        onView(withId(R.id.activity3_app_bar)).check(doesNotExist())
        onView(withId(R.id.activity3_layout)).check(doesNotExist())

        onView(withId(R.id.activity_about_app_bar)).check(doesNotExist())
        onView(withId(R.id.activity_about_layout)).check(doesNotExist())
    }

    private fun testActivity2Existence() {
        onView(withId(R.id.activity1_app_bar)).check(doesNotExist())
        onView(withId(R.id.activity1_layout)).check(doesNotExist())

        onView(withId(R.id.activity2_app_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.activity2_layout)).check(matches(isDisplayed()))

        onView(withId(R.id.activity3_app_bar)).check(doesNotExist())
        onView(withId(R.id.activity3_layout)).check(doesNotExist())

        onView(withId(R.id.activity_about_app_bar)).check(doesNotExist())
        onView(withId(R.id.activity_about_layout)).check(doesNotExist())
    }

    private fun testActivity3Existence() {
        onView(withId(R.id.activity1_app_bar)).check(doesNotExist())
        onView(withId(R.id.activity1_layout)).check(doesNotExist())

        onView(withId(R.id.activity2_app_bar)).check(doesNotExist())
        onView(withId(R.id.activity2_layout)).check(doesNotExist())

        onView(withId(R.id.activity3_app_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.activity3_layout)).check(matches(isDisplayed()))

        onView(withId(R.id.activity_about_app_bar)).check(doesNotExist())
        onView(withId(R.id.activity_about_layout)).check(doesNotExist())
    }

    private fun testActivityAboutExistence() {
        onView(withId(R.id.activity1_app_bar)).check(doesNotExist())
        onView(withId(R.id.activity1_layout)).check(doesNotExist())

        onView(withId(R.id.activity2_app_bar)).check(doesNotExist())
        onView(withId(R.id.activity2_layout)).check(doesNotExist())

        onView(withId(R.id.activity3_app_bar)).check(doesNotExist())
        onView(withId(R.id.activity3_layout)).check(doesNotExist())

        onView(withId(R.id.activity_about_app_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_about_layout)).check(matches(isDisplayed()))
    }

    private fun testActivity1ToAbout() {
        testActivity1Existence()
        onView(withId(R.id.about_button)).perform(click())
        testActivityAboutExistence()
        pressBack()
        testActivity1Existence()
    }

    private fun testActivity2ToAbout() {
        testActivity2Existence()
        onView(withId(R.id.about_button)).perform(click())
        testActivityAboutExistence()
        pressBack()
        testActivity2Existence()
    }

    private fun testActivity3ToAbout() {
        testActivity3Existence()
        onView(withId(R.id.about_button)).perform(click())
        testActivityAboutExistence()
        pressBack()
        testActivity3Existence()
    }

    @get:Rule
    val scenarioRule = ActivityScenarioRule(Activity1::class.java)

    @Test
    fun testTransition123() {
        testActivity1Existence()
        onView(withId(R.id.button1)).perform(click())
        testActivity2Existence()
        onView(withId(R.id.button1)).perform(click())
        testActivity1Existence()
        onView(withId(R.id.button1)).perform(click())
        testActivity2Existence()
        onView(withId(R.id.button2)).perform(click())
        testActivity3Existence()
        onView(withId(R.id.button2)).perform(click())
        testActivity2Existence()
        onView(withId(R.id.button2)).perform(click())
        testActivity3Existence()
        onView(withId(R.id.button1)).perform(click())
        testActivity1Existence()
    }

    @Test
    fun testTransitionToAbout() {
        testActivity1ToAbout()
        onView(withId(R.id.button1)).perform(click())
        testActivity2ToAbout()
        onView(withId(R.id.button2)).perform(click())
        testActivity3ToAbout()
    }

    @Test
    fun testBackStack() {
        testActivity1Existence()
        onView(withId(R.id.button1)).perform(click())
        pressBack()
        testActivity1Existence()

        onView(withId(R.id.button1)).perform(click())
        onView(withId(R.id.button2)).perform(click())
        pressBack()
        testActivity2Existence()

        onView(withId(R.id.button2)).perform(click())
        pressBack()
        pressBack()
        testActivity1Existence()

        onView(withId(R.id.button1)).perform(click())
        onView(withId(R.id.button2)).perform(click())
        onView(withId(R.id.about_button)).perform(click())
        pressBack()
        testActivity3Existence()
        pressBack()
        testActivity2Existence()
    }
}