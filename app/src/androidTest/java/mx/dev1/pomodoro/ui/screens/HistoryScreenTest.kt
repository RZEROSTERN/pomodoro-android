package mx.dev1.pomodoro.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.performClick
import mx.dev1.pomodoro.ui.theme.PomodoroTheme
import org.junit.Rule
import org.junit.Test

class HistoryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun historyScreen_displaysSectionsAndItems() {
        composeTestRule.setContent {
            PomodoroTheme {
                HistoryScreen()
            }
        }

        composeTestRule.onNodeWithText("Today").assertIsDisplayed()
        composeTestRule.onNodeWithText("Yesterday").assertIsDisplayed()
        composeTestRule.onNodeWithText("Languages study").assertIsDisplayed()
        composeTestRule.onNodeWithText("Home page design").assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("Yoga lesson, Relaxing time, 30m, 01:15 - 01:45")
            .performTouchInput { swipeLeft() }

        composeTestRule.onNodeWithContentDescription("Delete Yoga lesson 01:15 - 01:45").assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Languages study, Education, 1h 15m, 12:00 - 01:15")
            .performTouchInput { swipeLeft() }

        composeTestRule.onNodeWithContentDescription("Delete Languages study 12:00 - 01:15").assertIsDisplayed()

        composeTestRule.onNodeWithText("Today").performClick()

        composeTestRule
            .onAllNodesWithContentDescription("Delete Languages study 12:00 - 01:15")
            .assertCountEquals(0)
    }
}
