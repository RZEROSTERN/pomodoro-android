package mx.dev1.pomodoro.ui.components

import org.junit.Assert.assertEquals
import org.junit.Test

class MyTrackerCategoryRowTest {

    @Test
    fun defaultTrackerCategories_returnsExpectedValues() {
        val categories = defaultTrackerCategories()

        assertEquals(3, categories.size)
        assertEquals("Productive", categories[0].title)
        assertEquals("Meditation", categories[1].title)
        assertEquals("Education", categories[2].title)
    }
}
