package techhunt.developers.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalenderUtils {

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public static String getCurrentDateString(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(Calendar.getInstance().getTime());
    }

    public static String changeDateFormateInString(String date, String formatFrom, String formateTo) throws ParseException {
        SimpleDateFormat from = new SimpleDateFormat(formatFrom);
        SimpleDateFormat to = new SimpleDateFormat(formateTo);
        Date dt = from.parse(date);
        return to.format(dt);
    }

    public static long getCurrentTimeInMilli() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static long getTimeInMilli(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    public static long getTimeInMilli(String date, String format) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat from = new SimpleDateFormat(format);
        Date date1 = from.parse(date);
        calendar.setTime(date1);
        return calendar.getTimeInMillis();
    }

    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDay(String date, String format) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat from = new SimpleDateFormat(format);
        Date date1 = from.parse(date);
        calendar.setTime(date1);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static int getMonth(String date, String format) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat from = new SimpleDateFormat(format);
        Date date1 = from.parse(date);
        calendar.setTime(date1);
        return calendar.get(Calendar.MONTH);
    }

    public static String getMonthName(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        return month_date.format(cal.getTime());
    }

    public static String getMonthName(String date, String format) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        SimpleDateFormat monthFormate = new SimpleDateFormat("format");
        Date date1 = monthFormate.parse(date);
        cal.setTime(date1);
        return month_date.format(cal.getTime());
    }

    public static String getMonthName(int month) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        cal.set(Calendar.MONTH, month);
        return month_date.format(cal.getTime());
    }

    public int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static int getYear(String date, String format) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat from = new SimpleDateFormat(format);
        Date date1 = from.parse(date);
        calendar.setTime(date1);
        return calendar.get(Calendar.YEAR);
    }

    public static int getDayOfWeek(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static int numberOfDaysInMonth(int month, int year) {
        Calendar monthStart = new GregorianCalendar(year, month - 1, 1);
        return monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Date incrementDateByOne(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    public Date decrementDateByOne(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }
}
