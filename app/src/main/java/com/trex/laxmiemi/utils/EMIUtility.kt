package com.trex.laxmiemi.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.max

class EMIUtility(
    private val firstDueDate: String,
    private val durationInMonths: Int,
) {
    companion object {
        private const val DATE_FORMAT = "dd-MM-yyyy"
        private val dateFormatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

        private fun String.toCalendar(): Calendar =
            Calendar.getInstance().apply {
                time = dateFormatter.parse(this@toCalendar) ?: Date()
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

        private fun Calendar.toDateString(): String = dateFormatter.format(this.time)
    }

    data class EMIStatus(
        val isCompleted: Boolean,
        val isDelayed: Boolean,
        val delayedDays: Long,
        val remainingEMIs: Int,
    )

    fun getNextMonthDate(date: String): String {
        val calendar = date.toCalendar()
        calendar.add(Calendar.MONTH, 1)
        return calendar.toDateString()
    }

    fun isDateInProperFormat(
        date: String,
        dateFormat: String = "dd-MM-yyyy",
    ): Boolean =
        try {
            val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(date)
            true
        } catch (e: Exception) {
            false
        }

    fun getEMIStatus(currentDueDate: String): EMIStatus {
        val now =
            Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

        val currentDueCal = currentDueDate.toCalendar()
        val firstDueCal = firstDueDate.toCalendar()

        // Calculate months passed since first due date
        val monthsPassed =
            ((currentDueCal.timeInMillis - firstDueCal.timeInMillis) / (30L * 24 * 60 * 60 * 1000)).toInt() + 1

        // Calculate days delayed from current date
        val delayedDays = (now.timeInMillis - currentDueCal.timeInMillis) / (24 * 60 * 60 * 1000)

        return EMIStatus(
            isCompleted = monthsPassed >= durationInMonths,
            isDelayed = delayedDays > 0,
            delayedDays = max(0, delayedDays),
            remainingEMIs = max(0, durationInMonths - monthsPassed),
        )
    }
}
