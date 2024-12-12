package com.trex.laxmiemi.utils

import java.text.SimpleDateFormat
import java.util.*

data class DeviceEMIStatus(
    val isDueDateCrossed: Boolean,
    val daysDelayed: Int,
)

data class EMIPeriodStatus(
    val isEMIPeriodCompleted: Boolean,
    val monthsPassed: Int,
)

object EMIUtil {
    private const val DATE_FORMAT = "dd-MM-yyyy"

    fun checkEMIDueDate(
        dueDateString: String,
        daysDelay: Int,
    ): DeviceEMIStatus {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        val dueDate = dateFormat.parse(dueDateString)
        val currentDate = Calendar.getInstance().time

        if (dueDate == null) {
            throw IllegalArgumentException("Invalid due date format. Please use $DATE_FORMAT")
        }

        val differenceInMillis = currentDate.time - dueDate.time
        val differenceInDays = (differenceInMillis / (1000 * 60 * 60 * 24)).toInt()

        return if (differenceInDays > daysDelay) {
            DeviceEMIStatus(isDueDateCrossed = true, daysDelayed = differenceInDays)
        } else {
            DeviceEMIStatus(isDueDateCrossed = false, daysDelayed = 0)
        }
    }

    fun checkEMIPeriodCompletion(
        dueDateString: String,
        emiPeriodMonths: Int,
    ): EMIPeriodStatus {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        val dueDate = dateFormat.parse(dueDateString)
        val currentDate = Calendar.getInstance()

        if (dueDate == null) {
            throw IllegalArgumentException("Invalid due date format. Please use $DATE_FORMAT")
        }

        val dueDateCalendar = Calendar.getInstance()
        dueDateCalendar.time = dueDate
        dueDateCalendar.add(Calendar.MONTH, emiPeriodMonths)

        val monthsPassed =
            currentDate.get(Calendar.MONTH) - dueDateCalendar.get(Calendar.MONTH) +
                (currentDate.get(Calendar.YEAR) - dueDateCalendar.get(Calendar.YEAR)) * 12

        return if (currentDate.after(dueDateCalendar)) {
            EMIPeriodStatus(isEMIPeriodCompleted = true, monthsPassed = monthsPassed)
        } else {
            EMIPeriodStatus(isEMIPeriodCompleted = false, monthsPassed = monthsPassed)
        }
    }
}
