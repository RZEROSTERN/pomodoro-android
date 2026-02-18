package mx.dev1.pomodoro.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import mx.dev1.pomodoro.ui.theme.PomodoroTheme
import org.junit.Rule
import org.junit.Test

class CalendarScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calendarScreen_displaysMainSections() {
        composeTestRule.setContent {
            PomodoroTheme {
                CalendarScreen()
            }
        }

        composeTestRule.onNodeWithText("August").assertIsDisplayed()
        composeTestRule.onNodeWithText("Yoga lesson   x").assertIsDisplayed()
        composeTestRule.onNodeWithText("5 course").assertIsDisplayed()
    }

    @Test
    fun calendarScreen_selectsDifferentDay() {
        composeTestRule.setContent {
            PomodoroTheme {
                CalendarScreen()
            }
        }

        composeTestRule.onNodeWithContentDescription("August 17").performClick()
        composeTestRule.onNodeWithContentDescription("August 17 selected").assertIsDisplayed()
    }
}
