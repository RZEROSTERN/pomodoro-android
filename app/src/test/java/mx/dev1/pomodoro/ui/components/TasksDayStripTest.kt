package mx.dev1.pomodoro.ui.components

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class TasksDayStripTest {

    @Test
    fun taskWeekItemsForDate_returnsSundayToSaturdayForGivenWeek() {
        val items = taskWeekItemsForDate(LocalDate.of(2026, 2, 17))

        assertEquals(7, items.size)
        assertEquals("Sun", items.first().weekDay)
        assertEquals(15, items.first().dayNumber)
        assertEquals("Tue", items[2].weekDay)
        assertEquals(17, items[2].dayNumber)
        assertEquals("Sat", items.last().weekDay)
        assertEquals(21, items.last().dayNumber)
    }

    @Test
    fun dayIndexFromSunday_returnsExpectedIndexForTuesday() {
        val index = dayIndexFromSunday(LocalDate.of(2026, 2, 17))
        assertEquals(2, index)
    }
}
