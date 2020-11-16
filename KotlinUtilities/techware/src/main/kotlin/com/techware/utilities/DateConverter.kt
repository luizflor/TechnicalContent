package com.techware.utilities

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class DateConverter {
    companion object {
        val formatter = DateTimeFormatter.ofPattern("M/d/yyyy")


        fun toInstant(stringInDatePattern: String): Instant {
            var localDate = LocalDate.parse(stringInDatePattern, formatter)
            return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        }

        fun toStringDate(date: Instant): String {
            //val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(Locale.US)
                .withZone(ZoneId.systemDefault())
            return formatter.format(date);
        }

        fun toStringDateTime(date: Instant): String {
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(Locale.US)
                .withZone(ZoneId.systemDefault())
            return formatter.format(date);
        }
    }
}