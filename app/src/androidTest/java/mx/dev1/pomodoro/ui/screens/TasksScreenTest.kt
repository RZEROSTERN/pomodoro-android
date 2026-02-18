package mx.dev1.pomodoro.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import mx.dev1.pomodoro.ui.theme.PomodoroTheme
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class TasksScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun tasksScreen_displaysMainState() {
        composeTestRule.setContent {
            PomodoroTheme {
                TasksScreen(referenceDate = LocalDate.of(2026, 2, 17))
            }
        }

        composeTestRule.onNodeWithContentDescription("Sun 15").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Tue 17").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Sat 21").assertIsDisplayed()
        composeTestRule.onNodeWithText("No tasks for today").assertIsDisplayed()
    }

    @Test
    fun tasksScreen_updatesSelectedDay() {
        composeTestRule.setContent {
            PomodoroTheme {
                TasksScreen(referenceDate = LocalDate.of(2026, 2, 17))
            }
        }

        composeTestRule.onNodeWithContentDescription("Fri 20").performClick()
        composeTestRule.onNodeWithContentDescription("Fri 20").assertIsDisplayed()
    }
}
