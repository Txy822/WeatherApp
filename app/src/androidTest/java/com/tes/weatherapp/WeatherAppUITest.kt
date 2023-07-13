package com.tes.weatherapp

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.test.platform.app.InstrumentationRegistry
import com.tes.weatherapp.presentation.view.DetailScreen
import com.tes.weatherapp.presentation.view.HomeScreen
import com.tes.weatherapp.presentation.view.MainActivity
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class WeatherAppUITest {
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.tes.weatherapp", appContext.packageName)
    }
    @Test
    fun homeScreenComponentsDisplayedCorrectly() {
        composeTestRule.activity.setContent {
            HomeScreen()
        }
        val rootNode = composeTestRule.onRoot()
        rootNode.printToLog("Node Tree")

        // Assert that the Points and Level  labels are  displayed
        composeTestRule.onNode(hasText("[London Weather]")).assertIsDisplayed()
        composeTestRule.onNodeWithText("London Weather", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Current Cond: ", useUnmergedTree = true).assertIsDisplayed()

        // Check if the current temperature text is displayed
        val currentTemperatureText = "°C"
        composeTestRule.onNodeWithText(currentTemperatureText).assertIsDisplayed()

        // Check if the "Last update:" text is displayed
        composeTestRule.onNodeWithText("Last update:").assertIsDisplayed()

        // Check if the forecast items are displayed
        val forecastItemCount = 7
        repeat(forecastItemCount) { index ->
            composeTestRule.onNodeWithText("Card $index").assertIsDisplayed()
        }
    }

    @Test
    fun detailScreenComponentsDisplaysCorrectly() {
        composeTestRule.activity.setContent {
            DetailScreen()
        }
        val rootNode = composeTestRule.onRoot()
        rootNode.printToLog("Node Tree")

        // Assert that the Points and Level  labels are  displayed
        composeTestRule.onNodeWithText("London Weather on", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Condition", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Temp in C: ", useUnmergedTree = true).assertIsDisplayed()
    }


    @Test
    fun clickingOnCardToNavigate() {
        composeTestRule.activity.setContent {
            HomeScreen()
        }
        // Simulate a click on the ImageCard
        // TODO: Add your click simulate here
        // composeTestRule.onNodeWithTag("ImageCard").performClick()

        // Assert that the points have been incremented
        // TODO: Add your assertions here
    }
    @Test
    fun listAllNodesTest() {
        composeTestRule.activity.setContent {
            HomeScreen()
        }
        // Get the root node of the semantic tree
        val rootNode = composeTestRule.onRoot()
        // Recursively list all nodes in the semantic tree
        rootNode.printToLog("Node Tree")
    }
}