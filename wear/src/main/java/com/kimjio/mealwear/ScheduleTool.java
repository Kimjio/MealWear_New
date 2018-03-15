package com.kimjio.mealwear;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ScheduleTool {
    public static final String SCHEDULE_PREFERENCE_NAME = "schedule";

    public static String getScheduleStringFormat(int year, int month, int day) {
        /*
         * Format : year-month-day
         */
        // Calendar의 month는 1이 부족하므로 1을 더해줌
        month += 1;
        return year + "-" + month + "-" + day;
    }

    /**
     * Pref Name Format : 2015-02-17-TYPE_index
     * ex) 2015-02-17-1_3
     */
    public static void saveScheduleData(Context mContext, String[] Calender, String[] Schedule) {
        Preference mPref = new Preference(mContext, SCHEDULE_PREFERENCE_NAME);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd(E)",
                Locale.KOREA);

        for (int index = 0; index < Calender.length; index++) {
            try {
                Calendar mDate = Calendar.getInstance();
                mDate.setTime(mFormat.parse(Calender[index]));

                int year = mDate.get(Calendar.YEAR);
                int month = mDate.get(Calendar.MONTH);
                int day = mDate.get(Calendar.DAY_OF_MONTH);

                if (!isBlank(Schedule[index])) Schedule[index] = "";

                mPref.putString(getScheduleStringFormat(year, month, day), Schedule[index]);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isBlank(String schedule) {
        return !("".equals(schedule) || " ".equals(schedule) || schedule == null);
    }

    /**
     * Format : 2018-3-15
     */
    public static restoreScheduleClass restoreBapData(Context context, int year, int month, int day) {
        Preference preference = new Preference(context, SCHEDULE_PREFERENCE_NAME);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        restoreScheduleClass restoreScheduleClass = new restoreScheduleClass();

        restoreScheduleClass.Schedule = preference.getString(getScheduleStringFormat(year, month, day), null);

        if (restoreScheduleClass.Schedule == null) {
            restoreScheduleClass.isNormalDay = true;
        }

        return restoreScheduleClass;
    }

    public static String replaceAndCheckString(String mString) {
        String[] mTrash = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "."};
        for (String e : mTrash) {
            mString = mString.replace(e, "");
        }
        return mString;
    }

    public static class restoreScheduleClass {
        public String Schedule;
        public boolean isNormalDay = false;
    }
}
