package com.wikitaco.demo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.wikitaco.demo.tacodetail.TacoDetailActivity;
import com.wikitaco.demo.tacolist.TacosListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TacoDetailInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.wikitaco.demo", appContext.getPackageName());
    }
    @Rule
    public ActivityTestRule<TacoDetailActivity> mActivityTestRule = new ActivityTestRule<TacoDetailActivity>(
            TacoDetailActivity.class, true, true
    ){};

    @Test
    public void validateFabClick(){
        onView(withId(R.id.fab))
                .perform(click())
                .check(matches(isDisplayed()));
    }
}
