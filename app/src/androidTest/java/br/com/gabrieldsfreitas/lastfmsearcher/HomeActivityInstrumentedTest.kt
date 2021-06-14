package br.com.gabrieldsfreitas.lastfmsearcher

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.gabrieldsfreitas.lastfmsearcher.databinding.ActivityHomeBinding
import br.com.gabrieldsfreitas.lastfmsearcher.ui.HomeActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityInstrumentedTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<HomeActivity>()

    @Test
    fun testEvent() {
        val scenario = activityScenarioRule.scenario
        scenario.onActivity {
            onView(withId(R.id.total_text_view))
                .check(matches(withText("Top 20 tracks")))

//            onView(withId(R.id.recycler_view))
//                .check(matches(isDisplayed()))
        }
    }
}