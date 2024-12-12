package com.trex.laxmiemi.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.max

class EMIUtility(
    firstDueDate: String,
    private val durationInMonths: Int,
    initialCurrentDueDate: String? = null,
    private val onDateUpdate: ((String) -> Unit)? = null,
) {
    companion object {
        private const val DATE_FORMAT = "dd-MM-yyyy"
        private val dateFormatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

        /**
         * Converts string date to Calendar
         */
        private fun String.toCalendar(): Calendar =
            Calendar.getInstance().apply {
                time = dateFormatter.parse(this@toCalendar) ?: Date()
                // Set time to start of day
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

        /**
         * Converts Calendar to string date
         */
        private fun Calendar.toDateString(): String = dateFormatter.format(this.time)
    }

    private var firstDueDateCal: Calendar = firstDueDate.toCalendar()
    private var currentDueDate: Calendar =
        initialCurrentDueDate?.toCalendar() ?: firstDueDateCal.clone() as Calendar
        set(value) {
            field = value
            onDateUpdate?.invoke(value.toDateString())
        }
    private var paidEMIs: Int = 0

    /**
     * Data class to hold EMI status information
     */
    data class EMIStatus(
        val isCompleted: Boolean,
        val isDelayed: Boolean,
        val delayedDays: Long,
        val remainingEMIs: Int,
        val nextDueDate: String,
    )

    /**
     * Updates the current due date manually
     * @param newDueDate The new due date to set in dd-MM-yyyy format
     */
    fun updateCurrentDueDate(newDueDate: String) {
        currentDueDate = newDueDate.toCalendar()
    }

    /**
     * Marks current EMI as paid and updates the due date
     * @return Boolean indicating if payment was successful
     */
    fun markCurrentEMIPaid(): Boolean {
        if (isEMICompleted()) {
            return false
        }

        currentDueDate.add(Calendar.MONTH, 1)
        paidEMIs++
        return true
    }

    fun getNextEmiDate(): String {
        currentDueDate.add(Calendar.MONTH, 1)
        return currentDueDate.toDateString()
    }

    /**
     * Checks if all EMIs are completed
     * @return Boolean indicating if all EMIs are paid
     */
    fun isEMICompleted(): Boolean = paidEMIs >= durationInMonths

    /**
     * Gets the number of remaining EMIs
     * @return Int number of remaining EMIs
     */
    fun getRemainingEMIs(): Int = max(0, durationInMonths - paidEMIs)

    /**
     * Calculates days delayed from current date to due date
     * @return Long number of days delayed (negative if not delayed)
     */
    fun calculateDelayedDays(): Long {
        val now =
            Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
        val diff = now.timeInMillis - currentDueDate.timeInMillis
        return diff / (24 * 60 * 60 * 1000) // Convert milliseconds to days
    }

    /**
     * Gets complete EMI status
     * @return EMIStatus object containing all relevant information
     */
    fun getEMIStatus(): EMIStatus {
        val delayedDays = calculateDelayedDays()

        return EMIStatus(
            isCompleted = isEMICompleted(),
            isDelayed = delayedDays > 0,
            delayedDays = max(0, delayedDays),
            remainingEMIs = getRemainingEMIs(),
            nextDueDate = currentDueDate.toDateString(),
        )
    }

    /**
     * Gets the current due date as string
     * @return String current due date in dd-MM-yyyy format
     */
    fun getCurrentDueDate(): String = currentDueDate.toDateString()
}
