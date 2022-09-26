package com.sounge.soungeapp.utils

object FormatUtils {
    fun formatHoursPast(hoursPast: Long): String {
        if (hoursPast > 8760) {
            val years = Math.floorDiv(hoursPast, 8760)

            return "%d %s".format(years, "a")
        }

        if (hoursPast > 730) {
            val months = Math.floorDiv(hoursPast, 730)

            return "%d %s".format(months, "me")
        }

        if (hoursPast > 168) {
            val weeks = Math.floorDiv(hoursPast, 168)

            return "%d %s".format(weeks, "sem")
        }

        if (hoursPast > 24) {
            val days = Math.floorDiv(hoursPast, 24)

            return "%d %s".format(days, "d")
        }

        return "%d %s".format(hoursPast, "h")
    }

    fun formatLikeAndCommentCount(count: Int): String {
        if (count > 999_999) {
            return "%.1fm".format(count / 1_000_000.0)
        }

        if (count > 999) {
            return "%.1fk".format(count / 1_000.0)
        }

        return count.toString()
    }
}