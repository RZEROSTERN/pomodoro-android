package mx.dev1.pomodoro.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import mx.dev1.pomodoro.ui.theme.PomodoroTheme
import org.junit.Rule
import org.junit.Test

class MyTrackerScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myTrackerScreen_displaysMainSections() {
        composeTestRule.setContent {
            PomodoroTheme {
                MyTrackerScreen()
            }
        }

        composeTestRule.onNodeWithText("You finished 72%").assertIsDisplayed()
        composeTestRule.onNodeWithText("78").assertIsDisplayed()
        composeTestRule.onNodeWithText("Productive").assertIsDisplayed()
        composeTestRule.onNodeWithText("Meditation").assertIsDisplayed()
        composeTestRule.onNodeWithText("Education").assertIsDisplayed()
        composeTestRule.onNodeWithText("VIEW DETAILS").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Progress card. You finished 72 percent of your tasks. 78 to do list.").assertIsDisplayed()
    }
}
