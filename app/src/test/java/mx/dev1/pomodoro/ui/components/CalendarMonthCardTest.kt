package mx.dev1.pomodoro.ui.components

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CalendarMonthCardTest {

    @Test
    fun buildMonthGrid_returnsExpectedOffsetAndSize() {
        val cells = buildMonthGrid(daysInMonth = 31, startDayOffset = 2)

        assertEquals(35, cells.size)
        assertNull(cells[0])
        assertNull(cells[1])
        assertEquals(1, cells[2])
        assertEquals(31, cells[32])
        assertNull(cells[33])
        assertNull(cells[34])
    }

    @Test(expected = IllegalArgumentException::class)
    fun buildMonthGrid_rejectsInvalidDayCount() {
        buildMonthGrid(daysInMonth = 27, startDayOffset = 0)
    }
}
