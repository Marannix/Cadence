package com.marannix.android.cadence.activity

import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.marannix.android.cadence.R
import com.marannix.android.cadence.RecyclerViewMatcher
import com.marannix.android.cadence.model.GitHubRepoModel

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    /**
     * HAPPY FLOW -> There is internet connection and the response is parsed as intent
     * The idea is to create a Mock response in the future to provide the local json file
     * This test will fail when there is no internet connection
     */

    private val aGithubRepoId = 298912
    private val aGithubRepoName = "simplerrd"
    private val aGithubRepoDescription = "SimpleRRD provides a simple Ruby interface for creating graphs with RRD"
    private val model =
        GitHubRepoModel(aGithubRepoId, aGithubRepoName, aGithubRepoDescription)

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        mActivityTestRule.activity.supportFragmentManager.beginTransaction()
    }

    /**
     * To run test, you'll need to right-click the class name either from the class file or from folder and click run
     */

    @Test
    fun testGithubRepoPositionOneHasCorrectData() {
        onView(withRecyclerView(R.id.githubRepoRecyclerView).atPosition(1))
            .check(matches(hasDescendant(withText("#${model.id}"))))
            .check(matches(hasDescendant(withText("Github Repo Name: "))))
            .check(matches(hasDescendant(withText(model.name))))
            .check(matches(hasDescendant(withText("Github Repo Description: "))))
            .check(matches(hasDescendant(withText(model.description))))
    }

    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }
}
