package mx.dev1.pomodoro.ui.components

import org.junit.Assert.assertEquals
import org.junit.Test

class HistoryTaskCardTest {

    @Test
    fun defaultHistorySections_returnsExpectedStructure() {
        val sections = defaultHistorySections()

        assertEquals(2, sections.size)
        assertEquals("Today", sections[0].title)
        assertEquals(5, sections[0].items.size)
        assertEquals("Yesterday", sections[1].title)
        assertEquals(2, sections[1].items.size)
        assertEquals("Yoga lesson", sections[0].items[1].title)
        assertEquals("30m", sections[0].items[1].duration)
    }
}
