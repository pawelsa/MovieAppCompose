package com.example.movieappcompose.extensions

import java.util.*

fun Long.isDateOlderThan(timePeriodType: Int, count: Int): Boolean {
    val startDate = GregorianCalendar
            .getInstance()
            .apply {
                timeInMillis = this@isDateOlderThan
            }
    val threshold = GregorianCalendar
            .getInstance()
            .apply {
                val currentDay = get(timePeriodType)
                set(timePeriodType, currentDay - count)
            }
    return startDate.before(threshold)
}