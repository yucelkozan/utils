package com.kozan.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeUtils {
    public static long[] getDayHourMinute(long difference){
        //long diffSeconds = difference / 1000 % 60;
        long diffMinutes = difference / (60 * 1000) % 60;
        long diffHours = difference / (60 * 60 * 1000) % 24;
        long diffDays = difference / (24 * 60 * 60 * 1000);

        return new long[]{diffDays, diffHours, diffMinutes};
    }

    public static long[] getDayHourMinuteSecond(long difference){
        long diffSeconds = difference / 1000 % 60;
        long diffMinutes = difference / (60 * 1000) % 60;
        long diffHours = difference / (60 * 60 * 1000) % 24;
        long diffDays = difference / (24 * 60 * 60 * 1000);

        return new long[]{diffDays, diffHours, diffMinutes, diffSeconds};
    }

    public static long[] getTimeDuration(long difference){
        long seconds = difference / 1000;
        long hours = seconds / (60 * 60);
        long remindSeconds = seconds - (hours * 60 * 60);
        long minutes = remindSeconds / 60;
        if (minutes >= 60) {
            hours = hours + 1;
            minutes = minutes % 60;
        }
        seconds = remindSeconds - (minutes * 60);
        if (seconds >= 60) {
            minutes = minutes + 1;
            seconds = seconds % 60;
        }

        return new long[]{hours, minutes, seconds};
    }

    public static String getTimeDurationString(long difference){
        long[] timeDuration = getTimeDuration(difference);
        long hours = timeDuration[0];
        long minutes = timeDuration[1];
        long seconds = timeDuration[2];

        if (hours > 0) {
            return String.format(Locale.ENGLISH, "%02d", hours) + ":" + String.format(Locale.ENGLISH, "%02d", minutes) + ":" + String.format(Locale.ENGLISH, "%02d", seconds);
        } else {
            return String.format(Locale.ENGLISH, "%02d", minutes) + ":" + String.format(Locale.ENGLISH, "%02d", seconds);
        }
    }

    public static String getDateString(long milliseconds, SimpleDateFormat sdf) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return sdf.format(calendar.getTime());
    }

    public static long getDaysFromDateToToday(long dateInMs) {
        Calendar calendarToday = Calendar.getInstance();
        return getDays(dateInMs, calendarToday.getTimeInMillis());
    }

    public static long getDaysFromTodayToDate(long dateInMs) {
        Calendar calendarToday = Calendar.getInstance();
        return getDays(calendarToday.getTimeInMillis(), dateInMs);
    }

    public static long getDays(long dateInMs_1, long dateInMs_2) {
        return TimeUnit.DAYS.convert(dateInMs_2 - dateInMs_1, TimeUnit.MILLISECONDS);
    }

    public static long getTodayTimeInMs() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getYesterdayTimeInMs() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTimeInMillis();
    }

    public static long getTomorrowTimeInMs() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTimeInMillis();
    }

    public static long getTimeInMs(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static Calendar getCalendar(long calendarTimeInMs) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendarTimeInMs);
        return calendar;
    }
}
