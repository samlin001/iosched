/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.iosched.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.google.samples.apps.iosched.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ScheduleActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(LauncherActivity::class.java)

    @Test
    fun scheduleActivityTest() {
        val tabView = onView(
                allOf(withContentDescription("May 9"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tabs),
                                        0),
                                1),
                        isDisplayed()))
        tabView.perform(click())

        val tabView2 = onView(
                allOf(withContentDescription("May 10"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tabs),
                                        0),
                                2),
                        isDisplayed()))
        tabView2.perform(click())

        val tabView3 = onView(
                allOf(withContentDescription("Agenda"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tabs),
                                        0),
                                3),
                        isDisplayed()))
        tabView3.perform(click())

        val viewGroup = onView(
                allOf(childAtPosition(
                        withParent(withId(R.id.viewpager)),
                        2),
                        isDisplayed()))
        viewGroup.check(matches(isDisplayed()))

        val tabView4 = onView(
                allOf(withContentDescription("May 10"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tabs),
                                        0),
                                2),
                        isDisplayed()))
        tabView4.perform(click())

        val floatingActionButton = onView(
                allOf(withId(R.id.filter_fab), withContentDescription("Filters"),
                        childAtPosition(
                                allOf(withId(R.id.coordinator_layout),
                                        childAtPosition(
                                                withId(R.id.fragment_container),
                                                0)),
                                4),
                        isDisplayed()))
        floatingActionButton.perform(click())
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
