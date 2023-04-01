package com.squer.promobee.security.util


import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class DateUtils {
    companion object{
        fun convertToLocalDate(date: Date?): LocalDate? {
            if (date == null)
                return null
            return date.toInstant()
                    .atZone(ZoneId.of("Asia/Kolkata"))
                    .toLocalDate();
        }

        fun getYearMonth(date: Date?): Long? {
            if (date == null)
                return null
            return SimpleDateFormat("yyyyMM").format(date).toLong()
        }

        fun getYearMonthDate(date: Date): Long? {
            if (date == null)
                return null
            return SimpleDateFormat("yyyyMMdd").format(date).toLong()
        }

        fun addDays(date: Date, days: Int) : Date? {
            var date1 = convertToLocalDate(date)
            var date2 = date1?.plusDays(days.toLong())
            return Date.from(date2?.atStartOfDay(ZoneId.systemDefault())?.toInstant());
        }
    }
}


