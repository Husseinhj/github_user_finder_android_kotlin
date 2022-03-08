package com.github.husseinhj.githubuser.extensions

import junit.framework.TestCase.assertFalse
import org.junit.Test
import java.text.SimpleDateFormat
import junit.framework.TestCase.assertTrue

class StringExtensionTest {

    @Test
    fun convertCorrectStringDateToDate_CorrectAnswer() {
        val stringDate = "2022-03-07T11:51:06Z"
        val date = stringDate.isoStringToDate()

        if (date == null) {
            assertTrue(false)
        }


        val day = SimpleDateFormat("dd").format(date!!)
        val month = SimpleDateFormat("MM").format(date)
        val year = SimpleDateFormat("yyyy").format(date)

        assertTrue(day == "07" && month == "03" && year == "2022")
    }

    @Test
    fun convertInCorrectStringDateFormatToDate_InCorrectAnswer() {
        val stringDate = "2022/03/07T11:51:06Z"
        val date = stringDate.isoStringToDate()

        assertFalse(date != null)
    }
}